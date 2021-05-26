package com.team_08.ISAproj.repository;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.Pretplata;
import com.team_08.ISAproj.model.Korisnik;
import com.team_08.ISAproj.model.Pacijent;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PacijentRepository extends JpaRepository<Pacijent, Long> {

    Pacijent findOneByUsername(String username);

    Pacijent findOneByCookieTokenValue(String cookie);

    Pacijent findOneByEmailAdresa(String email_adresa);

    Pacijent findOneById(Long id);
    
    @Query(value = "select p from PACIJENT p left outer join fetch p.alergije a where p.cookieTokenValue = :cookie")
    Pacijent fetchPacijentWithAlergijeByCookie(String cookie);
   
}
