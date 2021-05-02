package com.team_08.ISAproj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team_08.ISAproj.model.Narudzbenica;
import com.team_08.ISAproj.model.NarudzbenicaLek;
import com.team_08.ISAproj.model.RezervacijaLek;

public interface RezervacijaLekRepository extends JpaRepository<RezervacijaLek, Long> {
	
	List<RezervacijaLek> findAll();
}
