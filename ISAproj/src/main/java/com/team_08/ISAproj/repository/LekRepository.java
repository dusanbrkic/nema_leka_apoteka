package com.team_08.ISAproj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team_08.ISAproj.model.Lek;


@Repository
public interface LekRepository extends JpaRepository<Lek, Long> {

	
	
}
