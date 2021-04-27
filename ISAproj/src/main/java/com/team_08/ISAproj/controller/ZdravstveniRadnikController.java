package com.team_08.ISAproj.controller;

import com.team_08.ISAproj.dto.CookieRoleDTO;
import com.team_08.ISAproj.dto.DermatologDTO;
import com.team_08.ISAproj.dto.KorisnikDTO;
import com.team_08.ISAproj.model.*;
import com.team_08.ISAproj.model.enums.KorisnickaRola;
import com.team_08.ISAproj.service.EmailService;
import com.team_08.ISAproj.service.KorisnikService;

import java.time.ZoneId;
import java.util.*;

import com.team_08.ISAproj.service.PregledService;
import com.team_08.ISAproj.service.ZdravstveniRadnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<String> putOdsustvo(@RequestParam("start") String startDate,
                                              @RequestParam("end") String endDate,
                                              @RequestParam("cookie") String cookie) {
        LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        System.out.println(start);
        System.out.println(end);
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

    @GetMapping(value = "/fetchOdsustvaInDateRange", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Odsustvo>> fetchOdsustvaInDateRange(@RequestParam("cookie") String cookie,
                                                                  @RequestParam("start") String startDate,
                                                                  @RequestParam("end") String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        ZdravstveniRadnik z = zdravstveniRadnikService.fetchZdravstveniRadnikWithOdsustvaInDateRange(cookie, start, end);
        if(z != null) {
            return new ResponseEntity<>(z.getOdsustva(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new HashSet<>(), HttpStatus.OK);
    }
    
    @GetMapping(value = "/getAllDermatologApoteka", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getAllDermatologApotekaByAdmin(@RequestParam(required = false) String title,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "6") int size,
	        @RequestParam String cookie){
    	
		Korisnik k = korisnikService.findUserByToken(cookie);
		System.out.println(k);
		if (k == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
		if(k instanceof AdminApoteke) {
			AdminApoteke aa = (AdminApoteke) k;
	    	Pageable paging = PageRequest.of(page, size);
			Page<DermatologApoteka> dermatoloziApoteke = zdravstveniRadnikService.fetchDermatologsByApotekaId(aa.getApoteka().getId(), paging);
			List<DermatologDTO> listDermatologDTO = new ArrayList<DermatologDTO>();
	    	for(DermatologApoteka da: dermatoloziApoteke) {
	    		listDermatologDTO.add(new DermatologDTO(da));
	    	}
			Map<String, Object> response = new HashMap<>();
			response.put("dermatolozi", listDermatologDTO);
			response.put("currentPage", dermatoloziApoteke.getNumber());
			response.put("totalItems", dermatoloziApoteke.getTotalElements());
			response.put("totalPages", dermatoloziApoteke.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    }
}
