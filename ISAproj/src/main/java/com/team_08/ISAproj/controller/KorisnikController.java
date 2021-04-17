package com.team_08.ISAproj.controller;

import com.team_08.ISAproj.dto.CookieRoleDTO;
import com.team_08.ISAproj.dto.KorisnikDTO;
import com.team_08.ISAproj.model.*;
import com.team_08.ISAproj.model.enums.KorisnickaRola;
import com.team_08.ISAproj.service.KorisnikService;
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

@RestController
@RequestMapping("/korisnici")
public class KorisnikController {
    @Autowired
    private KorisnikService korisnikService;

    
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
    	System.out.println(ck);
    	korisnikDTO.setCookie(ck);
    	korisnikDTO.setFirstLogin(false);
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
    	k.setIme(korisnikDTO.getIme());
        k.setPrezime(korisnikDTO.getPrezime());
        k.setDatumRodjenja(korisnikDTO.getDatumRodjenja());
        k.updateUser(korisnikDTO);
        korisnikService.saveUser(k);
        return new ResponseEntity<>("User successfully updated",HttpStatus.OK);
    }
    

}
