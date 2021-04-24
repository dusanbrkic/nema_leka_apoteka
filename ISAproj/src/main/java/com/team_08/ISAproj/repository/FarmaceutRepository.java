package com.team_08.ISAproj.repository;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.Farmaceut;
import com.team_08.ISAproj.model.Korisnik;
import com.team_08.ISAproj.model.ZdravstveniRadnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface FarmaceutRepository extends JpaRepository<Farmaceut, Long> {
    Farmaceut findOneByUsername(String username);

    Korisnik findOneByCookieTokenValue(String cookie);

    Korisnik findOneByEmailAdresa(String email_adresa);

    @Query(value = "SELECT f FROM FARMACEUT f LEFT OUTER JOIN FETCH f.odsustva o where f.cookieTokenValue = :cookie")
    Farmaceut fetchFarmaceutWithOdsustva(@Param("cookie") String cookie);

    @Query(value = "SELECT f FROM FARMACEUT f LEFT OUTER JOIN FETCH f.odsustva o where f.cookieTokenValue = :cookie and (:start < o.kraj and :end > o.pocetak)")
    ZdravstveniRadnik fetchFarmaceutWithOdsustvaInDateRange(@Param("cookie") String cookie, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
