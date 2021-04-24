package com.team_08.ISAproj.controller;

import com.team_08.ISAproj.dto.CookieRoleDTO;
import com.team_08.ISAproj.dto.KorisnikDTO;
import com.team_08.ISAproj.model.*;
import com.team_08.ISAproj.model.enums.KorisnickaRola;
import com.team_08.ISAproj.service.EmailService;
import com.team_08.ISAproj.service.KorisnikService;

import java.util.Random;

import com.team_08.ISAproj.service.PregledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/korisnici")
public class KorisnikController {
    @Autowired
    private KorisnikService korisnikService;
  	@Autowired
  	private EmailService sendEmailService;
    @Autowired
    private PregledService pregledService;


    //change password
    @PutMapping(value = "/updatePass", consumes = "application/json",produces = "application/json")
    public ResponseEntity<KorisnikDTO> changePassUser(@RequestBody KorisnikDTO korisnikDTO) throws Exception {
    	Korisnik k = korisnikService.findUserByToken(korisnikDTO.getCookie());
        if(k == null) {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    	if(k.getPassword().equals(korisnikDTO.getPassword())) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	String ck = CookieToken.createTokenValue(korisnikDTO.getUsername(), korisnikDTO.getPassword());
        k.setFirstLogin(false);
        k.updateUser(korisnikDTO);
        korisnikService.saveUser(k);
        return new ResponseEntity<>(korisnikDTO,HttpStatus.OK);
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
            if(k instanceof Pacijent) korisnickaRola = KorisnickaRola.PACIJENT;
            else if(k instanceof Dermatolog) korisnickaRola = KorisnickaRola.DERMATOLOG;
            else if(k instanceof Farmaceut) korisnickaRola = KorisnickaRola.FARMACEUT;
            else if(k instanceof AdminApoteke) korisnickaRola = KorisnickaRola.ADMIN_APOTEKE;
            CookieRoleDTO cookieRoleDTO = new CookieRoleDTO(ck, korisnickaRola,k.getFirstLogin());
            return new ResponseEntity<CookieRoleDTO>(cookieRoleDTO, HttpStatus.OK);
        }

        return new ResponseEntity<CookieRoleDTO>(HttpStatus.UNAUTHORIZED);
    }
    //Licne informacije o korisniku
    @GetMapping(value = "/infoUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KorisnikDTO> infoUser(@RequestParam("cookie") String cookie){
        Korisnik k = korisnikService.findUserByToken(cookie);

        if(k == null) {
            return new ResponseEntity<KorisnikDTO>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<KorisnikDTO> registerUser(@RequestBody KorisnikDTO korisnik){

    	Korisnik k = korisnikService.findUserByEmail(korisnik.getEmailAdresa());
    	if(k != null) {
    		return new ResponseEntity<KorisnikDTO>(HttpStatus.NOT_FOUND);
    	}
    	if(korisnikService.findUser(korisnik.getUsername()) != null) {
    		return new ResponseEntity<KorisnikDTO>(HttpStatus.NOT_FOUND);
    	}
    	Random rand = new Random();
        String verificationCode = "";
        for(int i = 0 ; i < 7 ; i++)
        {
            verificationCode += String.valueOf(rand.nextInt(10));
        }
        Pacijent pacijent = new Pacijent();
        pacijent.UpdatePacijent(korisnik);
        pacijent.setCookieTokenValue(verificationCode);
        korisnikService.saveUser(pacijent);

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
}
