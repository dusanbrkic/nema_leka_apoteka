package com.team_08.ISAproj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.Promocija;

@Repository
public interface PromocijaRepository extends JpaRepository<Promocija, Long> {

}
