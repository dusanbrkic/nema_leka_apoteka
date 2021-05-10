package com.team_08.ISAproj.repository;

import java.util.Collection;
import java.util.List;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Lek;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApotekaRepository extends JpaRepository<Apoteka, Long> {

	Apoteka findOneById(Long Id);
	
	List<Apoteka> findAll();
	
	Page<Apoteka> findAll(Pageable pageable);
	
	Page<Apoteka> findByNazivContaining(String naziv, Pageable pageable);

	@Query(value="select a from APOTEKA a join fetch a.admini aa")
	Apoteka fetchOneByIdWithAdmini(Long idApoteke);
}
