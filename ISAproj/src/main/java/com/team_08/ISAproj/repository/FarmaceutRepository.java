package com.team_08.ISAproj.repository;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.Farmaceut;
import com.team_08.ISAproj.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmaceutRepository extends JpaRepository<Farmaceut, Long> {
    Farmaceut findOneByUsername(String username);

    Korisnik findOneByCookieTokenValue(String cookie);

    @Query(value = "SELECT f FROM FARMACEUT f LEFT OUTER JOIN FETCH f.odsustva o where f.cookieTokenValue = :cookie")
    Korisnik fetchFarmaceutWithOdsustvo(@Param("cookie") String cookie);
}
