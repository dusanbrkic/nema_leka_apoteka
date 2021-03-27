package com.team_08.ISAproj.repository;

import com.team_08.ISAproj.model.AdminApoteke;
import com.team_08.ISAproj.model.Apoteka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminApotekeRepository extends JpaRepository<AdminApoteke, Long> {

}
