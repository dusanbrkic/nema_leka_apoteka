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
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

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
	
	
//    @RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<LekDTO>> getLekovi1() {
//        List<ApotekaLek> apotekeLekovi = apotekaLekService.findAll();
//        if(apotekeLekovi == null) {
//        	return new ResponseEntity<List<LekDTO>>(HttpStatus.NOT_FOUND);
//        }
//        List<LekDTO> lekovi = new ArrayList<LekDTO>();
//        for(ApotekaLek a : apotekeLekovi) {
//        	lekovi.add(new LekDTO(a.getLek()));
//        }
//        return new ResponseEntity<List<LekDTO>>(lekovi, HttpStatus.OK);
//    }
	
	
	@GetMapping(value="/sviLek" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getSviLekovi(
			@RequestParam(required = false) String title,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "6") int size)
    {
		try {
	    	Pageable paging = PageRequest.of(page, size);
	    	
	    	Page<Lek> lekovi;
	    	lekovi = lekService.findAll(paging);
	
			List<LekDTO> lekoviDTO = new ArrayList<LekDTO>();
			for (Lek a : lekovi) {
				lekoviDTO.add(new LekDTO(a));
			}
			
			Map<String, Object> response = new HashMap<>();
			response.put("lekovi", lekovi);
			response.put("currentPage", lekovi.getNumber());
			response.put("totalItems", lekovi.getTotalElements());
			response.put("totalPages", lekovi.getTotalPages());
			
			
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
	@GetMapping(value="/aa" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getLekApoteka(@RequestParam(required = false) String title,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "6") int size, 
			@RequestParam String apotekaID,
	        @RequestParam(defaultValue = "naziv") String sort,
	        @RequestParam(defaultValue = "opadajuce") String smer) {
		
		List<Order> orders = new ArrayList<Order>();
		
    	Pageable paging = PageRequest.of(page, size);
    	
    	Page<ApotekaLek> apotekeLekovi;
    	apotekeLekovi = apotekaLekService.findOneByApoteka(Long.parseLong(apotekaID),paging);
		if(apotekeLekovi == null) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		List<LekDTO> lekovi = new ArrayList<LekDTO>();
		for (ApotekaLek a : apotekeLekovi) {
			if(title.equals("")) {
				lekovi.add(new LekDTO(a.getLek()));
			}else{
				if(a.getLek().getNaziv().contains(title)) {
					lekovi.add(new LekDTO(a.getLek()));
				}
			}

		}
		Map<String, Object> response = new HashMap<>();
		response.put("lekovi", lekovi);
		response.put("currentPage", apotekeLekovi.getNumber());
		response.put("totalItems", apotekeLekovi.getTotalElements());
		response.put("totalPages", apotekeLekovi.getTotalPages());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@GetMapping(value="/basic" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LekDTO>> getLekApoteka(@RequestParam String apotekaID) {
    	
    	List<ApotekaLek> apotekeLekovi = apotekaLekService.findOneByApoteka(Long.parseLong(apotekaID));
		if(apotekeLekovi == null) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		List<LekDTO> lekovi = new ArrayList<LekDTO>();
		for (ApotekaLek a : apotekeLekovi) {
			lekovi.add(new LekDTO(a.getLek()));
		}
		return new ResponseEntity<List<LekDTO>>(lekovi, HttpStatus.OK);
	}
	//dodavanje leka
	@PostMapping(consumes = "application/json")
	public ResponseEntity<LekDTO> saveLek(@RequestBody LekDTO lekDTO){
//		System.out.print(lekDTO.getCookie());
		Korisnik k = korisnikService.findUserByToken(lekDTO.getCookie());
		if(k == null) {
			return new ResponseEntity<LekDTO>(HttpStatus.NOT_FOUND);
		}
		if(k instanceof AdminApoteke) {
    	
			AdminApoteke aa = (AdminApoteke) k;
    	
			Boolean check = lekService.saveLekApoteka(lekDTO,aa.getApoteka().getId().toString());
			System.out.println(check);
			if(check) {
				return new ResponseEntity<LekDTO>(lekDTO,HttpStatus.CREATED);
			}
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
		}
		return new ResponseEntity<LekDTO>(HttpStatus.NOT_FOUND);

	}
}
