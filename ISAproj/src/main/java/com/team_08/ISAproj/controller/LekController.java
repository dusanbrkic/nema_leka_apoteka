package com.team_08.ISAproj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team_08.ISAproj.dto.LekDTO;
import com.team_08.ISAproj.dto.ApotekaDTO;
import com.team_08.ISAproj.dto.CookieRoleDTO;
import com.team_08.ISAproj.dto.KorisnikDTO;
import com.team_08.ISAproj.model.AdminApoteke;
import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Korisnik;
import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.service.ApotekaLekService;
import com.team_08.ISAproj.service.ApotekaService;
import com.team_08.ISAproj.service.KorisnikService;
import com.team_08.ISAproj.service.LekService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lekovi")
public class LekController {


	@Autowired
	private ApotekaLekService apotekaLekService;
    @Autowired
    private KorisnikService korisnikService;
	@Autowired
	private LekService lekService;
	@Autowired
	private ApotekaService apotekaService;
    @RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LekDTO>> getLekovi() {
        List<ApotekaLek> apotekeLekovi = apotekaLekService.findAll();
        if(apotekeLekovi == null) {
        	return new ResponseEntity<List<LekDTO>>(HttpStatus.NOT_FOUND);
        }
        List<LekDTO> lekovi = new ArrayList<LekDTO>();
        for(ApotekaLek a : apotekeLekovi) {
        	lekovi.add(new LekDTO(a.getLek()));
        }
        return new ResponseEntity<List<LekDTO>>(lekovi, HttpStatus.OK);
    }
	@GetMapping("")
	public ResponseEntity<Map<String, Object>> getLekovi(
			@RequestParam(required = false) String title,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "6") int size)
    {
		try {
	    	Pageable paging = PageRequest.of(page, size);
	    	
	    	Page<ApotekaLek> apotekeLekovi;
	    	if (title == null)
	    		apotekeLekovi = apotekaLekService.findAll(paging);
	        else
	        	apotekeLekovi = apotekaLekService.findByLekContaining(null, paging); //todo
	
			List<LekDTO> lekovi = new ArrayList<LekDTO>();
			for (ApotekaLek a : apotekeLekovi) {
				lekovi.add(new LekDTO(a.getLek()));
			}
			
			Map<String, Object> response = new HashMap<>();
			response.put("lekovi", lekovi);
			response.put("currentPage", apotekeLekovi.getNumber());
			response.put("totalItems", apotekeLekovi.getTotalElements());
			response.put("totalPages", apotekeLekovi.getTotalPages());
			
			
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LekDTO>> getLekApoteka(@PathVariable("id") Long id) {
		List<ApotekaLek> apotekaLekovi = apotekaLekService.findOneByApoteka(id);
		if(apotekaLekovi == null) {
			return new ResponseEntity<List<LekDTO>>(HttpStatus.NOT_FOUND);
		}
		List<LekDTO> lekovi = new ArrayList<LekDTO>();
		for (ApotekaLek al : apotekaLekovi) {
			//lekovi.add(al.getLek());
			lekovi.add(new LekDTO(al.getLek()));
		}
		return new ResponseEntity<List<LekDTO>>(lekovi, HttpStatus.OK);
	}
	@PostMapping(consumes = "application/json")
	public ResponseEntity<LekDTO> saveLek(@RequestBody LekDTO lekDTO, @RequestParam("cookie") String cookie){
		Korisnik k = korisnikService.findUserByToken(cookie);
		if(k == null) {
			return new ResponseEntity<LekDTO>(HttpStatus.NOT_FOUND);
		}
        if(k instanceof AdminApoteke) {
        	AdminApoteke aa = (AdminApoteke) k;
        	lekService.saveLekApoteka(lekDTO,aa.getApoteka().getId().toString());
        	
        	return new ResponseEntity<LekDTO>(lekDTO,HttpStatus.OK);
        }
		return new ResponseEntity<LekDTO>(HttpStatus.NOT_FOUND);
	
	}
}
