package com.team_08.ISAproj.repository;

import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.model.Pregled;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PregledRepository extends JpaRepository<Pregled, Long> {

    @Query(value = "SELECT DISTINCT p FROM PREGLED p LEFT OUTER JOIN FETCH p.preporuceniLekovi l where p.dermatolog.id = :id",
    countQuery = "SELECT count(DISTINCT p) FROM PREGLED p LEFT OUTER JOIN p.preporuceniLekovi l where p.dermatolog.id = :id")
    Page<Pregled> fetchPregledWithPreporuceniLekovi(@Param("id") Long id, Pageable pageable);
}
