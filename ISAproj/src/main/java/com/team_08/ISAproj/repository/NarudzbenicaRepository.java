package com.team_08.ISAproj.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.Narudzbenica;
import com.team_08.ISAproj.model.Pregled;
@Repository
public interface NarudzbenicaRepository extends JpaRepository<Narudzbenica, Long> {

	List<Narudzbenica> findAll();
	
    // za listu rezervisanih lekova
    @Query(value = "SELECT p FROM NARUDZBENICA p JOIN PACIJENT pac ON p.pacijent.id=pac.id where p.pacijent.cookieTokenValue = :cookie")
    Page<Narudzbenica> findAllNarudzbenicePagedAndSorted(@Param("cookie") String cookie, Pageable pageable);

}
