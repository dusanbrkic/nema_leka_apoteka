package model.service;

import java.util.Collection;

import model.dao.Apoteka;

public interface ApotekaService {

	
	Collection<Apoteka> findAll();
	
	Apoteka findOne(Long id);
	
	Apoteka create(Apoteka apoteka);
}
