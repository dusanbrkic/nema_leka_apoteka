package com.team_08.ISAproj.service;

import java.util.Collection;

import com.team_08.ISAproj.model.Apoteka;

public interface ApotekaService {

	
	Collection<Apoteka> findAll();
	
	Apoteka findOne(Long id);
	
	Apoteka create(Apoteka apoteka);
}
