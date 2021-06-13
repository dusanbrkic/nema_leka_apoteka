package com.team_08.ISAproj.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.Ocena;
import com.team_08.ISAproj.model.OcenaApoteka;
import com.team_08.ISAproj.model.OcenaLek;
import com.team_08.ISAproj.model.OcenaZdravstveniRadnik;


@Repository
public interface OcenaRepository extends JpaRepository<Ocena, Long> {

	// pronadji prosecnu ocenu leka
	@Query(value = "select AVG(o.ocena) from OCENA_LEK o where o.lek.id = :id")
	Double findProsecnaOcenaLekaByID(@Param("id") Long id);
	
	// vrati sve ocene leka
	@Query(value = "select o from OCENA_LEK o where o.lek.id = :id")
	List<Ocena> findOceneLekaByID(@Param("id") Long id);
	
	// vrati ocenu leka od jednog pacijenta
	@Query(value = "select o from OCENA_LEK o where o.lek.id = :idLeka and o.pacijent.id = :idPacijenta")
	OcenaLek findOcenaLekaByPacijentID(@Param("idLeka") Long idLeka, @Param("idPacijenta") Long idPacijenta);

	@Query(value = "select o from OCENA_APOTEKA o where o.apoteka.id = :idApoteke and o.pacijent.id = :idPacijenta")
	OcenaApoteka findOcenaApotekeByPacijentID(@Param("idApoteke") Long idApoteke, @Param("idPacijenta") Long idPacijenta);
	
	@Query(value = "select AVG(o.ocena) from OCENA_APOTEKA o where o.apoteka.id = :id")
	Double findProsecnaOcenaApotekaByID(@Param("id") Long id);
	
	@Query(value = "select o from OCENA_APOTEKA o where o.apoteka.id = :id")
	List<Ocena> findOceneApotekaByID(@Param("id") Long id);
	
	
	
	// pronadji prosecnu ocenu zdravstvenog radnika
	@Query(value = "select AVG(o.ocena) from OCENA_ZDRAVSTVENI_RADNIK o where o.zdravstveniRadnik.id = :id")
	Double findProsecnaOcenaZdravstvenogRadnikaByID(@Param("id") Long id);
	
	@Query(value = "select o from OCENA_ZDRAVSTVENI_RADNIK o where o.zdravstveniRadnik.id = :idRadnika and o.pacijent.id = :idPacijenta")
	OcenaZdravstveniRadnik findZdravstveniRadnikByPacijentID(@Param("idRadnika") Long idRadnika, @Param("idPacijenta") Long idPacijenta);

}
