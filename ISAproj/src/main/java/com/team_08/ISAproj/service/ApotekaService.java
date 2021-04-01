package com.team_08.ISAproj.service;

import java.util.Collection;
import java.util.List;

import com.team_08.ISAproj.repository.ApotekaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.team_08.ISAproj.model.Apoteka;

import org.springframework.stereotype.Service;


@Service
public class ApotekaService {

	@Autowired
	private ApotekaRepository apotekaRepository;

	public List<Apoteka> findAll() {
		List<Apoteka> apoteke = apotekaRepository.findAll();
		return apoteke;
	}

	public Apoteka findOne(Long id) {
		return apotekaRepository.findById(id).orElseGet(null);
	}
	
	
	public Apoteka create(Apoteka apoteka) {
		Long id = apoteka.getId();
		
		//Apoteka tempApoteka = apotekaRepository.create(apoteka);
		return null;
	}

}
