package com.team_08.ISAproj.repository;

import com.team_08.ISAproj.model.Savetovanje;
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
public interface SavetovanjeRepository extends JpaRepository<Savetovanje, Long> {
    @Query(value = "SELECT DISTINCT s FROM SAVETOVANJE s LEFT OUTER JOIN FETCH s.preporuceniLekovi l where s.farmaceut.id = :id",
    countQuery = "SELECT count(DISTINCT s) FROM SAVETOVANJE s LEFT OUTER JOIN s.preporuceniLekovi l where s.farmaceut.id = :id")
    Page<Savetovanje> fetchAllWithPreporuceniLekovi(@Param("id") Long id, Pageable pageable);

    @Query(value = "SELECT s FROM SAVETOVANJE s JOIN PACIJENT p ON s.pacijent.id=p.id where s.farmaceut.id = :id and  UPPER(s.pacijent.prezime) LIKE UPPER(:pretragaPrezime) and UPPER(s.pacijent.ime) LIKE UPPER(:pretragaIme)")
    Page<Savetovanje> getObavljenaSavetovanja(@Param("id") Long id, Pageable pageable, @Param("pretragaIme") String pretragaIme, @Param("pretragaPrezime") String pretragaPrezime);

    @Query(value = "SELECT s FROM SAVETOVANJE s where s.farmaceut.cookieTokenValue = :cookie and ((s.vreme < :end and s.vreme > :start) or (s.kraj < :end and s.kraj > :start))")
    List<Savetovanje> findAllInDateRange(LocalDateTime start, LocalDateTime end, String cookie);
}
