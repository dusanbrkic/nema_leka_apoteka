package com.team_08.ISAproj.controller;

import com.team_08.ISAproj.dto.ApotekaDTO;
import com.team_08.ISAproj.dto.CookieRoleDTO;
import com.team_08.ISAproj.dto.FarmaceutDTO;
import com.team_08.ISAproj.dto.KorisnikDTO;
import com.team_08.ISAproj.exceptions.KorisnikPostojiException;
import com.team_08.ISAproj.model.*;
import com.team_08.ISAproj.model.enums.KorisnickaRola;
import com.team_08.ISAproj.repository.PacijentRepository;
import com.team_08.ISAproj.service.ApotekaLekService;
import com.team_08.ISAproj.service.ApotekaService;
import com.team_08.ISAproj.service.EmailService;
import com.team_08.ISAproj.service.KorisnikService;
import com.team_08.ISAproj.service.OcenaService;
import com.team_08.ISAproj.service.PacijentService;

import java.util.Random;

import com.team_08.ISAproj.service.PregledService;
import com.team_08.ISAproj.service.RezervacijaService;
import com.team_08.ISAproj.service.ZdravstveniRadnikService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.OptimisticLockException;

@RestController
@RequestMapping("/korisnici")
public class KorisnikController {
    @Autowired
    private KorisnikService korisnikService;
  	@Autowired
  	private EmailService sendEmailService;
    @Autowired
    private PregledService pregledService;
    @Autowired
    private RezervacijaService rezervacijaService;
    @Autowired
    private ApotekaLekService apotekaLekService;
    @Autowired
    private PacijentService pacijentService;
    @Autowired
    private ZdravstveniRadnikService zdravstveniRadnikService;
    @Autowired
    private OcenaService ocenaService;

    //change password
    @PostMapping(value = "/updatePass", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CookieRoleDTO> changePassUser(
            @RequestParam String cookie,
            @RequestParam String currentPass,
            @RequestParam String newPass){
    	Korisnik k = korisnikService.findUserByToken(cookie);
        if(k == null) {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    	if(!k.getPassword().equals(currentPass)) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	String ck = CookieToken.createTokenValue(k.getUsername(), newPass);
        k.setFirstLogin(false);
        k.setPassword(newPass);
        k.setCookieTokenValue(ck);
        korisnikService.saveUser(k);
        return new ResponseEntity<>(new CookieRoleDTO(ck, null),HttpStatus.OK);
    }

    @GetMapping(value = "/loginUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CookieRoleDTO> loginUser(@RequestParam("username") String username,
                                                   @RequestParam("password") String password){
        Korisnik k = korisnikService.findUser(username);

        if(k == null) {
            return new ResponseEntity<CookieRoleDTO>(HttpStatus.NOT_FOUND);
        }
        if(k.getPassword().equals(password)){
            String ck = CookieToken.createTokenValue(username, password);
            if(k.getCookieToken() == null || k.getCookieToken().equals("token")) {
            	k.setCookieTokenValue(ck);
            }else {
                if(!k.getCookieToken().equals(ck)) {
                	return new ResponseEntity<CookieRoleDTO>(HttpStatus.BAD_REQUEST);
                	}
            }
            k.setCookieTokenValue(ck);
            korisnikService.saveUser(k);
            KorisnickaRola korisnickaRola = null;
            
            if(k instanceof Pacijent) 
            {
            	korisnickaRola = KorisnickaRola.PACIJENT;
        		List<Rezervacija> rezervacije = rezervacijaService.findRezervacijeByPacijent((Pacijent) k);
        		LocalDateTime currentDate = LocalDateTime.now();
        		
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        		
        		
        		for(Rezervacija r : rezervacije) {
        			r.getRokPonude().isBefore(currentDate);
        			if(r.isPreuzeto() == false && r.isIsteklo() == false && r.getRokPonude().isBefore(currentDate) && !r.getRokPonude().equals(currentDate)) {
        				r.setIsteklo(true);
        				((Pacijent) k).setBrPenala(((Pacijent) k).getBrPenala() + 1);
        				
        				List<RezervacijaLek> lekovi = rezervacijaService.findRezervacijaLekByRezervacijaID(r.getId());
        				for(RezervacijaLek rl : lekovi) {
        					ApotekaLek al = apotekaLekService.findInApotekaLek(rl.getLek().getId(), r.getApoteka().getId());
        					al.setKolicina(al.getKolicina() + rl.getKolicina());
            				apotekaLekService.saveAL(al);
        				}
        				
        				korisnikService.saveUser(k);
        				rezervacijaService.saveRezervacija(r);
        			}
        		}
            }
            else if(k instanceof Dermatolog) korisnickaRola = KorisnickaRola.DERMATOLOG;
            else if(k instanceof Farmaceut) korisnickaRola = KorisnickaRola.FARMACEUT;
            else if(k instanceof AdminApoteke) korisnickaRola = KorisnickaRola.ADMIN_APOTEKE;
            CookieRoleDTO cookieRoleDTO = new CookieRoleDTO(ck, korisnickaRola,k.getFirstLogin());
            return new ResponseEntity<CookieRoleDTO>(cookieRoleDTO, HttpStatus.OK);
        }

        return new ResponseEntity<CookieRoleDTO>(HttpStatus.UNAUTHORIZED);
    }
    //Verify korisnika
    @GetMapping(value = "/verifyCookie", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> verifyCookieUser(@RequestParam("cookie") String cookie){
        Korisnik k = korisnikService.findUserByToken(cookie);

        if(k == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<Void>(HttpStatus.OK);

    }
    //Licne informacije o korisniku
    @GetMapping(value = "/infoUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KorisnikDTO> infoUser(@RequestParam("cookie") String cookie){
        Korisnik k = korisnikService.findUserByToken(cookie);

        if(k == null) {
            return new ResponseEntity<KorisnikDTO>(HttpStatus.NOT_FOUND);
        }
        if(k instanceof Pacijent) {
        	KorisnikDTO pDTO = new KorisnikDTO((Pacijent) k);
        	return new ResponseEntity<KorisnikDTO>(pDTO, HttpStatus.OK);

        }

        KorisnikDTO korisnikDTO = new KorisnikDTO(k);
        return new ResponseEntity<KorisnikDTO>(korisnikDTO, HttpStatus.OK);

    }
    //update user Info
    @PutMapping(value = "/updateUser", consumes = "application/json",produces = "application/json")
    public ResponseEntity<String> updateUser(@RequestBody KorisnikDTO korisnikDTO) throws Exception {
    	Korisnik k = korisnikService.findUserByToken(korisnikDTO.getCookie());
        if(k == null) {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        k.updateUser(korisnikDTO);
        korisnikService.saveUser(k);
        return new ResponseEntity<>("User successfully updated",HttpStatus.OK);
    }
    //register user
    @PostMapping(value = "/registerUser")
    public ResponseEntity<KorisnikDTO> registerUser(@RequestBody KorisnikDTO korisnik) throws InterruptedException{
    	
        Pacijent pacijent = new Pacijent();
        pacijent.UpdatePacijent(korisnik);
    	Random rand = new Random();
        String verificationCode = "";
        for(int i = 0 ; i < 7 ; i++)
        {
            verificationCode += String.valueOf(rand.nextInt(10));
        }
        pacijent.setCookieTokenValue(verificationCode);
        
    	// konkuretno testiraj i sacuvaj korisnika
        try {
        	korisnikService.savePacijentKonkurentno(pacijent);
        } catch (KorisnikPostojiException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

        
        
		String body = "Poštovani, " + pacijent.getIme() + " vas verifikacioni kod je : " + verificationCode;

		String title = "Verifikacija Apoteka";

		try
		{
			Thread t = new Thread() {
				public void run()
				{
					sendEmailService.sendEmail(pacijent.getEmailAdresa(), body, title);
				}
			};
			t.start();
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}


    	KorisnikDTO kDTO = new KorisnikDTO((Korisnik) pacijent);
		return new ResponseEntity<KorisnikDTO>(kDTO, HttpStatus.OK);
    }
    //verify code
    @PostMapping(value = "/verifyCode")
    public ResponseEntity<KorisnikDTO> verifyCode(@RequestBody String verification) {
        verification = verification.substring(0, verification.length() - 1);
        Korisnik k = korisnikService.findUserByToken(verification);
        System.out.println(k);
        System.out.println(verification);
        if (k == null) {
            return new ResponseEntity<KorisnikDTO>(HttpStatus.BAD_REQUEST);
        }
        String ck = CookieToken.createTokenValue(k.getUsername(), k.getPassword());
        k.setCookieTokenValue(ck);
        korisnikService.saveUser(k);
        KorisnikDTO kDTO = new KorisnikDTO(k);
        return new ResponseEntity<KorisnikDTO>(kDTO, HttpStatus.OK);
    }
    @GetMapping(value = "/blocked")
    public ResponseEntity<Void> isBlocked(@RequestParam("cookie") String cookie){
        Korisnik k = korisnikService.findUserByToken(cookie);

        if(k instanceof Pacijent) {
        	if(((Pacijent) k).getBrPenala() >= 3) {
        		return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        	}
        }
        
        return new ResponseEntity<Void>(HttpStatus.OK);

    }
    @GetMapping(value = "/slobodni_farmaceuti_apoteke")
    public ResponseEntity<List<FarmaceutDTO>> slobodniFarmaceutiApoteke(@RequestParam("start") String startDate,
		 	 											  				@RequestParam("end") String endDate,
		 	 											  				@RequestParam("idApoteke") Long idApoteke)
    {
    	LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        DecimalFormat df = new DecimalFormat("###.##");
        List<FarmaceutDTO> farmaceutiDTO = new ArrayList<FarmaceutDTO>();
        
        boolean slobodan = false;
    	for(Farmaceut f : zdravstveniRadnikService.fetchFarmaceutsByApotekaId(idApoteke)) {
            if (pregledService.findAllInDateRangeByZdravstveniRadnik(start, end, f.getCookieToken()).isEmpty()) {
            	if (zdravstveniRadnikService.checkRadnoVreme(start.toLocalTime(), end.toLocalTime(), f.getCookieToken(), idApoteke)!=null) {
            		if (zdravstveniRadnikService.fetchZdravstveniRadnikWithOdsustvaInDateRange(f.getCookieToken(), start, end)==null) {
            			FarmaceutDTO fdto = new FarmaceutDTO(f);
            			fdto.setUsername(Long.toString(f.getId()));      				
            			farmaceutiDTO.add(fdto);
            		}
            	}
            }
    	}
        
        return new ResponseEntity<List<FarmaceutDTO>>(farmaceutiDTO, HttpStatus.OK);

    }
    
    @Scheduled(cron = "1 0 1 * * ?") // svakog prvog u mesecu
    //@Scheduled(cron = "1 * * * * ?") // provera svaki minut
	public void cronJob() {
		
    	System.out.println("BRISANJE PENALA");

		for(Pacijent p : pacijentService.findAll()) {
			p.setBrPenala(0);
			korisnikService.saveUser(p);
		}
	}
}
