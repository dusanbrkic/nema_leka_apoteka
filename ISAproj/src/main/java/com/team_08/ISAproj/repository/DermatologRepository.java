package com.team_08.ISAproj.repository;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.Dermatolog;
import com.team_08.ISAproj.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DermatologRepository extends JpaRepository<Dermatolog, Long> {
    Dermatolog findOneByUsername(String username);

    Korisnik findOneByCookieTokenValue(String cookie);
    Korisnik findOneByEmailAdresa(String email_adresa);

    @Query(value = "SELECT d FROM DERMATOLOG d LEFT OUTER JOIN FETCH d.odsustva o where d.cookieTokenValue = :cookie")
    Korisnik fetchDermatologWithOdsustvo(@Param("cookie") String cookie);
}
