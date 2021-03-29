package com.team_08.ISAproj.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.team_08.ISAproj.service.ApotekaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.team_08.ISAproj.dto.ApotekaDTO;
import com.team_08.ISAproj.model.Apoteka;

@RestController
@RequestMapping("/apoteke")
public class ApotekaController {

	@Autowired
	private ApotekaService apotekaService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ApotekaDTO>> getApoteke() {
		List<Apoteka> apoteke = apotekaService.findAll();
		List<ApotekaDTO> apotekeDTO = new ArrayList<ApotekaDTO>();
		for (Apoteka a : apoteke) {
			apotekeDTO.add(new ApotekaDTO(a));
		}
		return new ResponseEntity<List<ApotekaDTO>>(apotekeDTO, HttpStatus.OK);
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

}
