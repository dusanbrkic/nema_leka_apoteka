package com.team_08.ISAproj.controller;

import java.util.Collection;

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

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.service.ApotekaService;


@RestController
@RequestMapping("/apoteke")
public class ApotekaController {

	@Autowired
	private ApotekaService apotekaService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Apoteka>> getApoteke(){
		Collection<Apoteka> apoteke = apotekaService.findAll();
		return new ResponseEntity<Collection<Apoteka>>(apoteke,HttpStatus.OK);
	}
	@GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Apoteka> getApoteka(@PathVariable("id") Long id){
		Apoteka apoteka = apotekaService.findOne(id);
		
		if(apoteka == null) {
			return new ResponseEntity<Apoteka>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Apoteka>(apoteka,HttpStatus.OK);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Apoteka> createApoteka(@RequestBody Apoteka apoteka) throws Exception{
		Apoteka tempApoteka = apotekaService.create(apoteka);
		return new ResponseEntity<Apoteka>(tempApoteka,HttpStatus.CREATED);
	}
	
}
