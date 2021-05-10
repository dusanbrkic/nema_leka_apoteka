package com.team_08.ISAproj.controller;

import com.team_08.ISAproj.dto.LekDTO;
import com.team_08.ISAproj.dto.PregledDTO;
import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.model.Rezervacija;
import com.team_08.ISAproj.model.RezervacijaLek;
import com.team_08.ISAproj.service.LekService;
import com.team_08.ISAproj.model.AdminApoteke;
import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Dermatolog;
import com.team_08.ISAproj.model.DermatologApoteka;
import com.team_08.ISAproj.model.Korisnik;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.service.ApotekaService;
import com.team_08.ISAproj.service.EmailService;
import com.team_08.ISAproj.service.KorisnikService;
import com.team_08.ISAproj.service.PregledService;
import com.team_08.ISAproj.service.ZdravstveniRadnikService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pregledi")
public class PregledController {
    @Autowired
    private PregledService pregledService;
    @Autowired
    private LekService lekService;
    @Autowired
    ZdravstveniRadnikService zdravstveniRadnikService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private ApotekaService apotekaService;
    @Autowired
    private EmailService sendEmailService;
    
    @GetMapping(value = "/getPregledaniKorisniciByZdravstveniRadnik", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<PregledDTO>> getPregledaniKorisniciByZdravstveniRadnik(
            @RequestParam("cookie") String cookie,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortDesc") Boolean sortDesc,
            @RequestParam("pretragaIme") String pretragaIme,
            @RequestParam("pretragaPrezime") String pretragaPrezime) {
        Page<Pregled> pregledi = null;
        try {
            pregledi = pregledService.findAllByZdravstveniRadnikPagedAndSortedAndSearchedAndDone(cookie, page, size, sortBy, sortDesc, pretragaIme, pretragaPrezime);
        } catch (CookieNotValidException e) {
            return new ResponseEntity<Page<PregledDTO>>(HttpStatus.NOT_FOUND);
        }
        if (pregledi == null)
            return new ResponseEntity<Page<PregledDTO>>(Page.empty(), HttpStatus.OK);

        Page<PregledDTO> preglediDTO = pregledi.map(new Function<Pregled, PregledDTO>() {
            @Override
            public PregledDTO apply(Pregled p) {
                PregledDTO pregledDTO = new PregledDTO(p);
                if (sortBy == null) pregledDTO.loadLekovi(p);
                return pregledDTO;
            }
        });
        return new ResponseEntity<Page<PregledDTO>>(preglediDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/getPreglediByZdravstveniRadnik", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PregledDTO>> getPreglediByZdravstveniRadnik(
            @RequestParam("cookie") String cookie,
            @RequestParam("start") String startDate,
            @RequestParam("end") String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        List<Pregled> pregledi = null;
        try {
            pregledi = pregledService.fetchAllWithPreporuceniLekoviInDateRangeByZdravstveniRadnik(cookie, start, end);
        } catch (CookieNotValidException e) {
            return new ResponseEntity<List<PregledDTO>>(HttpStatus.NOT_FOUND);
        }
        if (pregledi == null)
            return new ResponseEntity<List<PregledDTO>>(new ArrayList<>(), HttpStatus.OK);

        List<PregledDTO> preglediDTO = pregledi.stream().map(new Function<Pregled, PregledDTO>() {
            @Override
            public PregledDTO apply(Pregled p) {
                PregledDTO pregledDTO = new PregledDTO(p);
                pregledDTO.loadLekovi(p);
                return pregledDTO;
            }
        }).collect(Collectors.toList());
        return new ResponseEntity<List<PregledDTO>>(preglediDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/updatePregled", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePregled(@RequestBody PregledDTO pregledDTO) {
        Pregled pregled = pregledService.findOneById(pregledDTO.getId());
        if (pregled == null)
            return new ResponseEntity<String>(HttpStatus.OK);
        List<Lek> lekovi = pregledDTO.getPreporuceniLekovi().stream().map(new Function<LekDTO, Lek>() {
            @Override
            public Lek apply(LekDTO lekDTO) {
                Lek l = lekService.findOneBySifra(lekDTO.getSifra());
                return l;
            }
        }).collect(Collectors.toList());
        pregled.setPreporuceniLekovi(new HashSet<>(lekovi));
        pregled.setPregledObavljen(true);
        pregled.setDijagnoza(pregledDTO.getDijagnoza());
        pregledService.savePregled(pregled);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PostMapping(value = "addSlobodanTermin")
    public ResponseEntity<PregledDTO> addSlobodanTermin(@RequestBody PregledDTO pregledDTO){

    	String apotekaId = pregledDTO.getApotekaId();
    	Apoteka a = apotekaService.findOne(Long.parseLong(apotekaId));
    	Dermatolog d = zdravstveniRadnikService.findOneByUsername(pregledDTO.getUsername());
    	Pregled p = new Pregled(pregledDTO);
    	p.setApoteka(a);
    	p.setDermatolog(d);


    	pregledService.saveSlobodanTermin(p);

    	//Korisnik k = korisnikService.findUserByToken(cookie);
//    	if(k == null) {
//    		return new ResponseEntity<PregledDTO>(HttpStatus.NOT_FOUND);
//    	}
//    	if(k instanceof AdminApoteke) {
//    		AdminApoteke aa = (AdminApoteke) k;
//    		Long ApotekaId = zdravstveniRadnikService.
//        	Page<DermatologApoteka> dermatoloziApoteke = null;
//
//        	dermatoloziApoteke = zdravstveniRadnikService.fetchDermatologsByApotekaId(Long.parseLong(aa.getApoteka().getId()), page);
//    	}


    	return new ResponseEntity<PregledDTO>(pregledDTO, HttpStatus.OK);
	}

    
    @GetMapping(value = "slobodniPregledi")
    public ResponseEntity<List<PregledDTO>> getSlobodaniTermini(@RequestParam("id_apoteke") Long id_apoteke){
    	
    	List<PregledDTO> pregledi = new ArrayList<PregledDTO>();
    	
    	for(Pregled p : pregledService.findAllFromApoteka(id_apoteke)) {
    		pregledi.add(new PregledDTO(p.getId(), p.getVreme(), p.getKraj(), p.getCena(), p.getZdravstveniRadnik().getIme() + " " + p.getZdravstveniRadnik().getPrezime()));
    	}
    	return new ResponseEntity<List<PregledDTO>>(pregledi, HttpStatus.OK);
	}
	@GetMapping(value="/zakaziPregled")
	public ResponseEntity<Void> otkaziRezervaciju(
			@RequestParam("id_pregleda") Long id_pregleda,
			@RequestParam("cookie") String cookie
	){
			Pacijent p = (Pacijent) korisnikService.findUserByToken(cookie);
			
			Pregled pregled = pregledService.findOneById(id_pregleda);
			pregled.setPacijent(p);
			pregled.setPregledZakazan(true);
			pregledService.savePregled(pregled);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		    String dateTime = pregled.getVreme().format(formatter);
			
			String body = "Poštovani, " + p.getIme() + "\n"
					+ "Zakazali ste pregled kod dermatologa. Više informacija u nastavku. \n"
					+ "Datum i vreme: " + dateTime + "\n"
					+ "Dermatolog: " + pregled.getZdravstveniRadnik().getIme() + " " + pregled.getZdravstveniRadnik().getPrezime() + "\n"
					+ "Cena: " + pregled.getCena() + "\n"
					+ "Za sva dodatna pitanja obratite nam se na ovaj mejl.\n"
					+ "Srdačan pozdrav.";
			
			String title = "Potvrda pregleda (ID:" + pregled.getId() + ")";
		
			try
			{
				Thread t = new Thread() {
					public void run()
					{
						sendEmailService.sendEmail(p.getEmailAdresa(), body, title);
					}
				};
				t.start();
			}
			catch(Exception e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
	    	
	    	return new ResponseEntity<>(HttpStatus.OK);
	}
    @GetMapping(value = "posete_dermatologu")
    public ResponseEntity<List<PregledDTO>> getPoseteDermatologu(@RequestParam("cookie") String cookie){
    	
    	List<PregledDTO> pregledi = new ArrayList<PregledDTO>();
    	
    	Pacijent pacijent = (Pacijent) korisnikService.findUserByToken(cookie);
    	
    	for(Pregled p : pregledService.findAllByPacijent(pacijent)) {
    		PregledDTO tmp = new PregledDTO(p);
    		tmp.setUsername(p.getZdravstveniRadnik().getIme() + " " + p.getZdravstveniRadnik().getPrezime());
    		pregledi.add(tmp);
    	}
    	return new ResponseEntity<List<PregledDTO>>(pregledi, HttpStatus.OK);
	}
}
