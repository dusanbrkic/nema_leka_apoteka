package com.team_08.ISAproj.controller;

import com.team_08.ISAproj.dto.CookieRoleDTO;
import com.team_08.ISAproj.dto.KorisnikDTO;
import com.team_08.ISAproj.model.*;
import com.team_08.ISAproj.model.enums.KorisnickaRola;
import com.team_08.ISAproj.service.EmailService;
import com.team_08.ISAproj.service.KorisnikService;

import java.util.Random;

import com.team_08.ISAproj.service.PregledService;
import com.team_08.ISAproj.service.ZdravstveniRadnikService;
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
@RequestMapping("/zdravstveniradnik")
public class ZdravstveniRadnikController {
    @Autowired
    private ZdravstveniRadnikService zdravstveniRadnikService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private PregledService pregledService;

    @GetMapping(value = "/putOdsustvo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putOdsustvo(@RequestParam("start") LocalDateTime start,
                                              @RequestParam("end") LocalDateTime end,
                                              @RequestParam("cookie") String cookie) {
        if (end.isBefore(start)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ZdravstveniRadnik z = zdravstveniRadnikService.fetchZdravstveniRadnikWithOdsustva(cookie);
        if(z != null) {
            if (!pregledService.findAllInDateRangeByZdravstveniRadnik(start, end, cookie).isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            z.getOdsustva().add(new Odsustvo(start, end));
        } else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        korisnikService.saveUser(z);
        return new ResponseEntity<>("User successfully updated",HttpStatus.OK);
    }

    @GetMapping(value = "/fetchOdsustva", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Odsustvo>> fetchOdsustva(@RequestParam("cookie") String cookie) {
        ZdravstveniRadnik z = zdravstveniRadnikService.fetchZdravstveniRadnikWithOdsustva(cookie);
        if(z != null) {
            return new ResponseEntity<>(z.getOdsustva(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
