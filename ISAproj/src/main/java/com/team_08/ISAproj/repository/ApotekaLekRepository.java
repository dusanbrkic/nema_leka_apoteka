package com.team_08.ISAproj.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
@Repository
public interface ApotekaLekRepository extends JpaRepository<ApotekaLek, Long> {
	ApotekaLek findOneById(String Id);
	
	List<ApotekaLek> findAllByApotekaId(Long ApotekaId);
	
	
	List<ApotekaLek> findAll();
}
