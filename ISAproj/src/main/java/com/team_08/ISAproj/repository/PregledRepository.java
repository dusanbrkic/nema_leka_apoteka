package com.team_08.ISAproj.repository;

import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.model.Pregled;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface PregledRepository extends JpaRepository<Pregled, Long> {

    @Query(value = "SELECT DISTINCT p FROM PREGLED p LEFT OUTER JOIN FETCH p.preporuceniLekovi l where p.dermatolog.id = :id",
            countQuery = "SELECT count(DISTINCT p) FROM PREGLED p LEFT OUTER JOIN p.preporuceniLekovi l where p.dermatolog.id = :id")
    Page<Pregled> fetchAllWithPreporuceniLekovi(@Param("id") Long id, Pageable pageable);

    @Query(value = "SELECT p FROM PREGLED p JOIN PACIJENT pac ON p.pacijent.id=pac.id where p.dermatolog.id = :id and UPPER(p.pacijent.prezime) LIKE UPPER(:pretragaPrezime) and UPPER(p.pacijent.ime) LIKE UPPER(:pretragaIme)")
    Page<Pregled> getAllObavljeni(@Param("id") Long id, Pageable pageable, @Param("pretragaIme") String pretragaIme, @Param("pretragaPrezime") String pretragaPrezime);

    @Query(value = "SELECT p FROM PREGLED p where p.dermatolog.cookieTokenValue = :cookie and ((p.vreme < :end and p.vreme > :start) or (p.kraj < :end and p.kraj > :start))")
    List<Pregled> findAllInDateRange(LocalDateTime start, LocalDateTime end, String cookie);
}
