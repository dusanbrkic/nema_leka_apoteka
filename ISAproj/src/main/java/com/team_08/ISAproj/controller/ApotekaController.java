package com.team_08.ISAproj.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team_08.ISAproj.service.ApotekaLekService;
import com.team_08.ISAproj.service.ApotekaService;
import com.team_08.ISAproj.service.KorisnikService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.team_08.ISAproj.dto.ApotekaDTO;
import com.team_08.ISAproj.dto.CookieRoleDTO;
import com.team_08.ISAproj.dto.LekDTO;
import com.team_08.ISAproj.model.AdminApoteke;
import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Dermatolog;
import com.team_08.ISAproj.model.Farmaceut;
import com.team_08.ISAproj.model.Korisnik;
import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.model.enums.KorisnickaRola;

@RestController
@RequestMapping("/apoteke")
public class ApotekaController {

	@Autowired
	private ApotekaService apotekaService;

    @Autowired
    private KorisnikService korisnikService;
    
/*
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ApotekaDTO>> getApoteke() {
		List<Apoteka> apoteke = apotekaService.findAll();
		List<ApotekaDTO> apotekeDTO = new ArrayList<ApotekaDTO>();
		for (Apoteka a : apoteke) {
			apotekeDTO.add(new ApotekaDTO(a));
		}
		return new ResponseEntity<List<ApotekaDTO>>(apotekeDTO, HttpStatus.OK);
	}
*/
	
	
	@GetMapping("")
	public ResponseEntity<Map<String, Object>> getApoteke(
			@RequestParam(required = false) String title,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "6") int size)
    {
		try {
	    	Pageable paging = PageRequest.of(page, size);
	    	
	    	Page<Apoteka> apoteke;
	    	if (title == null)
	    		apoteke = apotekaService.findAll(paging);
	        else
	        	apoteke = apotekaService.findByLekContaining(null, paging); //todo
	
	    	List<ApotekaDTO> apotekeDTO = new ArrayList<ApotekaDTO>();
			for (Apoteka a : apoteke) {
				apotekeDTO.add(new ApotekaDTO(a));
			}
			
			Map<String, Object> response = new HashMap<>();
			response.put("apoteke", apotekeDTO);
			response.put("currentPage", apoteke.getNumber());
			response.put("totalItems", apoteke.getTotalElements());
			response.put("totalPages", apoteke.getTotalPages());
			
			
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApotekaDTO> getApoteka(@PathVariable("id") Long id) {
		Apoteka apoteka = apotekaService.findOne(id);
		
		if (apoteka == null) {
			return new ResponseEntity<ApotekaDTO>(HttpStatus.NOT_FOUND);
		}
		ApotekaDTO a = new ApotekaDTO(apoteka);
		return new ResponseEntity<ApotekaDTO>(a, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Apoteka> createApoteka(@RequestBody ApotekaDTO apotekaDTO) throws Exception {
		Apoteka apoteka = new Apoteka(apotekaDTO);
		apotekaService.create(apoteka);
		return new ResponseEntity<Apoteka>(apoteka, HttpStatus.CREATED);
	}
	//Za admina apoteke pronalazimo apoteku
	@GetMapping(value = "/getByAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApotekaDTO> getApoteka(@RequestParam("cookie") String cookie)
    {
		Korisnik k = korisnikService.findUserByToken(cookie);

        if(k == null) {
            return new ResponseEntity<ApotekaDTO>(HttpStatus.NOT_FOUND);
        }
        if(k instanceof AdminApoteke) {
        	AdminApoteke aa = (AdminApoteke) k;
        	System.out.println(aa.getApoteka().getNaziv());
        	ApotekaDTO aDTO = new ApotekaDTO(aa.getApoteka());
        	return new ResponseEntity<ApotekaDTO>(aDTO,HttpStatus.OK);
        }
		return new ResponseEntity<ApotekaDTO>(HttpStatus.NOT_FOUND);
    }
}
