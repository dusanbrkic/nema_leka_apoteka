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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/korisnici")
public class KorisnikController {
    @Autowired
    private KorisnikService korisnikService;

    @GetMapping(value = "/loginUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CookieRoleDTO> loginUser(@RequestParam("username") String username,
                                                   @RequestParam("password") String password){
        Korisnik k = korisnikService.findUser(username);

        if(k == null) {
            return new ResponseEntity<CookieRoleDTO>(HttpStatus.NOT_FOUND);
        }
        if(k.getPassword().equals(password)){
            String ck = CookieToken.createTokenValue(username, password);
            k.setCookieToken(ck);
            KorisnickaRola korisnickaRola = null;
            if(k instanceof Pacijent) korisnickaRola = KorisnickaRola.PACIJENT;
            else if(k instanceof Dermatolog) korisnickaRola = KorisnickaRola.DERMATOLOG;
            else if(k instanceof Farmaceut) korisnickaRola = KorisnickaRola.FARMACEUT;
            else if(k instanceof AdminApoteke) korisnickaRola = KorisnickaRola.ADMIN_APOTEKE;
            CookieRoleDTO cookieRoleDTO = new CookieRoleDTO(ck, korisnickaRola);
            return new ResponseEntity<CookieRoleDTO>(cookieRoleDTO, HttpStatus.OK);
        }

        return new ResponseEntity<CookieRoleDTO>(HttpStatus.NOT_FOUND);
    }
    //Licne informacije o korisniku
    @GetMapping(value = "/infoUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KorisnikDTO> infoUser(@RequestParam("username") String username,
            @RequestParam("password") String password){
        Korisnik k = korisnikService.findUser(username);

        if(k == null) {
            return new ResponseEntity<KorisnikDTO>(HttpStatus.NOT_FOUND);
        }
        if(k.getPassword().equals(password)){
        	KorisnikDTO korisnikDTO = new KorisnikDTO(k);
        	return new ResponseEntity<KorisnikDTO>(korisnikDTO, HttpStatus.OK);
        }
        
        return new ResponseEntity<KorisnikDTO>(HttpStatus.NOT_FOUND);
    }
}
