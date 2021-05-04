package com.team_08.ISAproj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team_08.ISAproj.model.DermatologApoteka;
@Repository
public interface DermatologApotekaRepository extends JpaRepository<DermatologApoteka, Long> {

	
	@Query(value = "select da from DERMATOLOG_APOTEKA da where da.dermatolog.id = :d_id and da.apoteka.id = :ap_id")
	DermatologApoteka findOneDermatologApotekaByIds(@Param("d_id") Long dermatologId, @Param("ap_id") Long apotekaId);
}
