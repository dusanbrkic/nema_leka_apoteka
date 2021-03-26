package com.team_08.ISAproj.controller;


import com.team_08.ISAproj.dto.CookieRoleDTO;
import com.team_08.ISAproj.model.CookieToken;
import com.team_08.ISAproj.model.Korisnik;
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
            CookieToken ck = new CookieToken(username, password);
            k.setCookieToken(ck);
            CookieRoleDTO cookieRoleDTO = new CookieRoleDTO(ck.getValue(), k.getRola());
            return new ResponseEntity<CookieRoleDTO>(cookieRoleDTO, HttpStatus.OK);
        }

        return new ResponseEntity<CookieRoleDTO>(HttpStatus.NOT_FOUND);
    }
}
