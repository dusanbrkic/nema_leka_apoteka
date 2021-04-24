package com.team_08.ISAproj.repository;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.Dermatolog;
import com.team_08.ISAproj.model.Korisnik;
import com.team_08.ISAproj.model.ZdravstveniRadnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface DermatologRepository extends JpaRepository<Dermatolog, Long> {
    Dermatolog findOneByUsername(String username);

    Korisnik findOneByCookieTokenValue(String cookie);
    Korisnik findOneByEmailAdresa(String email_adresa);

    @Query(value = "SELECT d FROM DERMATOLOG d LEFT OUTER JOIN FETCH d.odsustva o where d.cookieTokenValue = :cookie")
    Dermatolog fetchDermatologWithOdsustva(@Param("cookie") String cookie);

    @Query(value = "SELECT d FROM DERMATOLOG d LEFT OUTER JOIN FETCH d.odsustva o where d.cookieTokenValue = :cookie and (:start < o.kraj and :end > o.pocetak)")
    ZdravstveniRadnik fetchDermatologWithOdsustvaInDateRange(@Param("cookie") String cookie, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
