package com.team_08.ISAproj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.repository.ApotekaLekRepository;
@Service
public class ApotekaLekService {

	
	@Autowired
	private ApotekaLekRepository apotekaLekRepository;
	
	
	public List<ApotekaLek> findAll() {
		List<ApotekaLek> apotekaLekovi = apotekaLekRepository.findAll();
		return apotekaLekovi;
	}

	public ApotekaLek findOne(Long id) {
		return apotekaLekRepository.findById(id).orElseGet(null);
	}
	
	public List<ApotekaLek> findOneByApoteka(Long id) {
		return apotekaLekRepository.findAllByApotekaId(id);
	}
	public Apoteka create(ApotekaLekRepository apotekaLek) {
		//Long id = apotekaLek.getId();
		
		//Apoteka tempApoteka = apotekaRepository.create(apoteka);
		return null;
	}
}
