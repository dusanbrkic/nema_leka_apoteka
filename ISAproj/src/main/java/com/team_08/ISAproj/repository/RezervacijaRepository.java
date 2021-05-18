package com.team_08.ISAproj.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Narudzbenica;
import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.model.Rezervacija;
import com.team_08.ISAproj.model.RezervacijaLek;

@Repository
public interface RezervacijaRepository extends JpaRepository<Rezervacija, Long> {

	List<Rezervacija> findAll();
	
    // za listu rezervisanih lekova
    //@Query(value = "SELECT p FROM NARUDZBENICA p JOIN PACIJENT pac ON p.pacijent.id=pac.id where p.pacijent.cookieTokenValue = :cookie")
    //Page<Narudzbenica> findAllNarudzbenicePagedAndSorted(@Param("cookie") String cookie, Pageable pageable);

	@Query(value = "select r from REZERVACIJA r where r.id = :param_id")
	Rezervacija findByRezervacijaId(@Param("param_id") Long id);
	
	@Query(value = "select r from REZERVACIJA r where r.pacijent = pacijent")
	List<Rezervacija> findAllRezervacijeFromKorisnik(@Param("pacijent") Pacijent p);

	@Query(value = "select r from REZERVACIJA r where r.apoteka.id = :idApoteke and r.id=:idRezervacije and r.rokPonude>:tommorow and r.preuzeto=false")
    Rezervacija findRezervacijaByIdAndApotekaIdBeforeRok(Long idRezervacije, Long idApoteke, LocalDateTime tommorow);

	@Query(value = "select r from REZERVACIJA r join fetch r.lekovi l where r.apoteka.id = :idApoteke and r.id=:idRezervacije and r.rokPonude>:tommorow and r.preuzeto=false")
	Rezervacija fetchRezervacijaWithLekoviByIdAndApotekaIdBeforeRok(Long idRezervacije, Long idApoteke, LocalDateTime tommorow);
}