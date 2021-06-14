package com.team_08.ISAproj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team_08.ISAproj.model.Narudzbenica;
import com.team_08.ISAproj.model.NarudzbenicaLek;
import com.team_08.ISAproj.model.RezervacijaLek;

public interface RezervacijaLekRepository extends JpaRepository<RezervacijaLek, Long> {

    List<RezervacijaLek> findAll();

    @Query(value = "select rl from REZERVACIJA_LEK rl where rl.rezervacija.id = :rezervacija_id")
    List<RezervacijaLek> findAllRezervacijaLekFromRezervacija(@Param("rezervacija_id") Long id);
   
    @Query(value = "select rl from REZERVACIJA_LEK rl where rl.rezervacija.apoteka.id = :id and rl.lek.id = :lek_id and rl.rezervacija.preuzeto = false ")
    List<RezervacijaLek> findAllRezervacijaLekNotFinished(Long id, Long lek_id);
}
