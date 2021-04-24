package com.team_08.ISAproj.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Lek;
@Repository
public interface ApotekaLekRepository extends JpaRepository<ApotekaLek, Long> {
	ApotekaLek findOneById(String Id);
	
	Page<ApotekaLek> findAllByApotekaId(Long ApotekaId, Pageable pageable);
	
	List<ApotekaLek> findAllByApotekaId(Long ApotekaId);
	List<ApotekaLek> findAll();
	
	Page<ApotekaLek> findAll(Pageable pageable);
	
	List<ApotekaLek> findAllByCena(double cena);
	
	Page<ApotekaLek> findByLekContaining(Lek lek, Pageable pageable);
	
	
	//List<ApotekaLek> fetch(Long apotekaid,String sifra);
	
	void deleteByLekId(Long LekId);
}
