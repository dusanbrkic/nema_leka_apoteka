package com.team_08.ISAproj.repository;

import com.team_08.ISAproj.model.AdminApoteke;
import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.Farmaceut;
import com.team_08.ISAproj.model.Korisnik;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminApotekeRepository extends JpaRepository<AdminApoteke, Long> {

    AdminApoteke findOneByUsername(String username);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select p from ADMIN_APOTEKE p where p.username = :username")
    AdminApoteke findOneByUsernameWithLock(String username);

    AdminApoteke findOneByCookieTokenValue(String cookie);

    AdminApoteke findOneByEmailAdresa(String email_adresa);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select p from ADMIN_APOTEKE p where p.emailAdresa = :email_adresa")
    AdminApoteke findOneByEmailAdresaWithLock(String email_adresa);
}
