package com.team_08.ISAproj.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Lek;
@Repository
public interface ApotekaLekRepository extends JpaRepository<ApotekaLek, Long> {
	ApotekaLek findOneById(String Id);
	
	Page<ApotekaLek> findAllByApotekaId(Long ApotekaId, Pageable pageable);
	
	List<ApotekaLek> findAllByApotekaId(Long ApotekaId);
	List<ApotekaLek> findAll();
	
	Page<ApotekaLek> findAll(Pageable pageable);
	
	List<ApotekaLek> findAllByCena(double cena);
	
	Page<ApotekaLek> findByLekContaining(Lek lek, Pageable pageable);
	//select l from lek inner join apotekalek al on l.id = apotekalek.lek.id where al.apoteka.id = idprosledjeneapoteke and l.id = idprosledjenogleka
	//@Query(value="select l from LEK l inner join APOTEKA_LEK al on l.id = al where al.apoteka.id = apotekaId and l.sifra = sifra")
	@Query(value="select al from APOTEKA_LEK al inner join LEK l on l.id = al.lek.id where l.sifra = :sifra and al.apoteka.id = :ap_id")
	ApotekaLek fetchBySifra(@Param("ap_id") Long apotekaID,@Param("sifra") String sifra);
	
	@Query(value = "select al from APOTEKA_LEK al inner join LEK l on l.id = al.lek.id where al.apoteka.id = :ap_id and UPPER(l.naziv) LIKE UPPER(:pretragaLek)")
	Page<ApotekaLek> findAllApotekaLekoviByApotedaIdPage(@Param("ap_id") Long ApotekaID,@Param("pretragaLek") String pretragaLek, Pageable pageable);

	
	
	void deleteByLekId(Long LekId);
}
