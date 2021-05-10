package com.team_08.ISAproj.repository;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.Korisnik;
import com.team_08.ISAproj.model.Pacijent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacijentRepository extends JpaRepository<Pacijent, Long> {

    Pacijent findOneByUsername(String username);

    Pacijent findOneByCookieTokenValue(String cookie);

    Pacijent findOneByEmailAdresa(String email_adresa);

    Pacijent findOneById(Long id);
}
