package com.team_08.ISAproj.service;

import java.util.Collection;

import com.team_08.ISAproj.repository.ApotekaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.team_08.ISAproj.model.Apoteka;

import org.springframework.stereotype.Service;


@Service
public class ApotekaServiceImpl implements ApotekaService {

	@Autowired
	private ApotekaRepository apotekaRepository;

	@Override
	public Collection<Apoteka> findAll() {
		Collection<Apoteka> apoteke = apotekaRepository.findAll();
		return apoteke;
	}

	@Override
	public Apoteka findOne(Long id) {
		Apoteka apoteka = apotekaRepository.findOne(id);
		return apoteka;
	}

	@Override
	public Apoteka create(Apoteka apoteka) {
		Long id = apoteka.getId();
		
		Apoteka tempApoteka = apotekaRepository.create(apoteka);
		return tempApoteka;
	}

}
