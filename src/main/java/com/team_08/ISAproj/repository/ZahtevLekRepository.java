package com.team_08.ISAproj.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team_08.ISAproj.model.ZahtevLek;
@Repository
public interface ZahtevLekRepository extends JpaRepository<ZahtevLek, Long> {

	@Query(value = "SELECT zl from ZAHTEV_LEK zl where zl.apoteka.id = :ap_id")
	Page<ZahtevLek> findAllByApotekaId(Pageable page, @Param("ap_id")Long id);
	
}
