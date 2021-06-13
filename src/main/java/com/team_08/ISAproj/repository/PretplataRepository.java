package com.team_08.ISAproj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team_08.ISAproj.model.Pretplata;

public interface PretplataRepository extends JpaRepository<Pretplata, Long> {

	
    @Query(value = "SELECT p FROM PRETPLATA p where p.apoteka.id = :ap_id")
    List<Pretplata> findPretplateApoteka(@Param("ap_id") Long apotekaId);
}
