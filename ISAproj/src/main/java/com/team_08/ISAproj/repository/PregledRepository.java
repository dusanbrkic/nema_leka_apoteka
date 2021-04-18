package com.team_08.ISAproj.repository;

import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.model.Pregled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PregledRepository extends JpaRepository<Pregled, Long> {

    @Query(value = "SELECT DISTINCT p FROM PREGLED p LEFT OUTER JOIN FETCH p.preporuceniLekovi l where p.dermatolog.id = :id")
    List<Pregled> fetchPregledWithPreporuceniLekovi(@Param("id") Long id);
}
