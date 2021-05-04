package com.team_08.ISAproj.repository;

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

    // za kalendar
    @Query(value = "SELECT DISTINCT p FROM PREGLED p LEFT OUTER JOIN FETCH p.preporuceniLekovi l where p.zdravstveniRadnik.cookieTokenValue = :cookie and (:startDate < p.kraj and :endDate > p.vreme)")
    List<Pregled> fetchAllWithPreporuceniLekoviInDateRangeByZdravstveniRadnik(@Param("cookie") String cookie, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // za listu pregledanih pacijenata
    @Query(value = "SELECT p FROM PREGLED p JOIN PACIJENT pac ON p.pacijent.id=pac.id where p.zdravstveniRadnik.cookieTokenValue = :cookie and p.pregledObavljen = true and UPPER(p.pacijent.prezime) LIKE UPPER(:pretragaPrezime) and UPPER(p.pacijent.ime) LIKE UPPER(:pretragaIme)")
    Page<Pregled> findAllByZdravstveniRadnikPagedAndSortedAndSearchedAndDone(@Param("cookie") String cookie, Pageable pageable, @Param("pretragaIme") String pretragaIme, @Param("pretragaPrezime") String pretragaPrezime);

    // za proveru validnosti zahteva za odsustvo
    @Query(value = "SELECT p FROM PREGLED p where p.zdravstveniRadnik.cookieTokenValue = :cookie and (:start < p.kraj and :end > p.vreme)")
    List<Pregled> findAllInDateRangeByZdravstveniRadnik(LocalDateTime start, LocalDateTime end, String cookie);

    Pregled findOneById(Long id);

    //vadjenje termina za pregled
    @Query(value = "SELECT p FROM PREGLED p where p.zdravstveniRadnik.cookieTokenValue = :cookie and p.pregledZakazan=false and (:start < p.kraj and :end > p.vreme)")
    List<Pregled> findAllTermsInDateRangeByDermatolog(String cookie, LocalDateTime start, LocalDateTime end);
}
