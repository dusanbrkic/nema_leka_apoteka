package com.team_08.ISAproj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team_08.ISAproj.dto.LekDTO;
import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.service.ApotekaLekService;
import com.team_08.ISAproj.service.ApotekaService;
import com.team_08.ISAproj.service.LekService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/lekovi")
public class LekController {

	// @Autowired
	// private LekService lekService;
	@Autowired
	private ApotekaLekService apotekaLekService;

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
}
