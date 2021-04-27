package com.team_08.ISAproj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.Narudzbenica;
@Repository
public interface NarudzbenicaRepository extends JpaRepository<Narudzbenica, Long> {

	List<Narudzbenica> findAll();
}
