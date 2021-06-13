package com.team_08.ISAproj.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.Pregled;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApotekaRepository extends JpaRepository<Apoteka, Long> {

	Apoteka findOneById(Long Id);

	List<Apoteka> findAll();

	Page<Apoteka> findAll(Pageable pageable);

	Page<Apoteka> findByNazivContaining(String naziv, Pageable pageable);

	@Query(value="select a from APOTEKA a join fetch a.admini aa")
	Apoteka fetchOneByIdWithAdmini(Long idApoteke);
	
	@Query(value="select a from APOTEKA a where a.id = :idApoteke")
	Apoteka findOneByID(@Param("idApoteke") Long idApoteke);
	
	// pronalazimo sve apoteke sa slobodnim farmaceutom
    @Query(value = "SELECT a FROM APOTEKA a")
    		//+ " JOIN FARMACEUT f ON a.id = f.apoteka_id"
    		//+ " JOIN PREGLED p ON "
    		//+ " WHERE :start < p.kraj and :end > p.vreme")
    List<Apoteka> findAllInDateRangeWithFreeZdravstveniRadnik(LocalDateTime start, LocalDateTime end);
    
    
	@Query(value = "SELECT a FROM APOTEKA a"
				+ " WHERE UPPER(a.naziv) LIKE UPPER(:pretragaNaziv) AND UPPER(a.adresa) LIKE UPPER(:pretragaAdresa)"
				+ " AND a.prosecnaOcena >= :ocenaOD AND a.prosecnaOcena <= :ocenaDO"
				+ " ORDER BY"
				+ " CASE WHEN :sortBy = 'ocena' and :smer = true THEN a.prosecnaOcena END DESC,"
				+ " CASE WHEN :sortBy = 'ocena' and :smer = false THEN a.prosecnaOcena END ASC,"
				+ " CASE WHEN :sortBy = 'naziv' and :smer = true THEN a.naziv END DESC,"
				+ " CASE WHEN :sortBy = 'naziv' and :smer = false THEN a.naziv END ASC,"
				+ " CASE WHEN :sortBy = 'adresa' and :smer = true THEN a.adresa END DESC,"
				+ " CASE WHEN :sortBy = 'adresa' and :smer = false THEN a.adresa END ASC")
    Page<Apoteka> getAllApotekePaged(
    		@Param("pretragaNaziv") String pretragaNaziv,
    		@Param("pretragaAdresa") String pretragaAdresa,
    		@Param("smer") Boolean smer,
    		@Param("ocenaOD") Double ocenaOD,
    		@Param("ocenaDO") Double ocenaDO,
    		@Param("sortBy") String sortBy,
    		Pageable pageable);

}
