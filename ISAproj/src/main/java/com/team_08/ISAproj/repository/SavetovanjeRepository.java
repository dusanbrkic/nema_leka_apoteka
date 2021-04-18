package com.team_08.ISAproj.repository;

import com.team_08.ISAproj.model.Savetovanje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavetovanjeRepository extends JpaRepository<Savetovanje, Long> {
    @Query(value = "SELECT DISTINCT s FROM SAVETOVANJE s LEFT OUTER JOIN FETCH s.preporuceniLekovi l where s.farmaceut.id = :id")
    List<Savetovanje> fetchSavetovanjeWithPreporuceniLekovi(@Param("id") Long id);
}

