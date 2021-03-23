package com.team_08.ISAproj.repository;
import java.util.Collection;

import com.team_08.ISAproj.model.Apoteka;


public interface ApotekaRepository {

	Collection<Apoteka> findAll();
	
	Apoteka findOne(Long id);
	
	Apoteka create(Apoteka apoteka);
}
