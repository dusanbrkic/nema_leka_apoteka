package com.team_08.ISAproj.repository;
import java.awt.print.Pageable;
import java.util.List;

import com.team_08.ISAproj.model.Apoteka;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApotekaRepository extends JpaRepository<Apoteka, Long> {

}
