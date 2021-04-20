package com.team_08.ISAproj.repository;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.Dermatolog;
import com.team_08.ISAproj.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DermatologRepository extends JpaRepository<Dermatolog, Long> {
    Dermatolog findOneByUsername(String username);

    Korisnik findOneByCookieTokenValue(String cookie);
    Korisnik findOneByEmailAdresa(String email_adresa);
}
