package com.team_08.ISAproj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team_08.ISAproj.model.Narudzbenica;
import com.team_08.ISAproj.model.NarudzbenicaLek;

public interface NarudzbenicaLekRepository extends JpaRepository<NarudzbenicaLek, Long> {

    List<NarudzbenicaLek> findAll();

    @Query(value = "SELECT nl FROM NARUDZBENICA_LEK nl where nl.narudzbenica.id = :id")
	List<NarudzbenicaLek> findNarudzbeniceLekNarudzbenica(@Param("id") Long id);
}
