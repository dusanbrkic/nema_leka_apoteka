package model.repository;
import java.util.Collection;

import model.dao.Apoteka;


public interface ApotekaRepository {

	Collection<Apoteka> findAll();
	
	Apoteka findOne(Long id);
	
	Apoteka create(Apoteka apoteka);
}
