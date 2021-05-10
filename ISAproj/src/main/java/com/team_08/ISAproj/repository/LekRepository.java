package com.team_08.ISAproj.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.Lek;


@Repository
public interface LekRepository extends JpaRepository<Lek, Long> {

	Page<Lek> findAll(Pageable pageable);

	List<Lek> findAll();
	//@Query(value= "select l from lek inner join apotekalek al on l.id = apotekalek.lek.id where al.apoteka.id = apotekaid and l.sifra = sifra")
	//@Query(value = "select l from LEK l inner join apotekalek al on l.id = al.lek.id")
	//Lek fetch(Long apotekaid);
	//@Query(value="select al from APOTEKA_LEK al inner join LEK l on l.id = al.lek.id where l.sifra = :sifra and al.apoteka.id = :ap_id")
	@Query(value = "select l from LEK l inner join APOTEKA_LEK al on l.id = al.lek.id where al.apoteka.id = :ap_id")
	List<Lek> findAllLekoviByApotedaID(@Param("ap_id") Long ApotekaID);


	@Query(value = "select l from LEK l inner join APOTEKA_LEK al on l.id = al.lek.id where al.apoteka.id = :ap_id and UPPER(l.naziv) LIKE UPPER(:pretragaLek)")
	Page<Lek> findAllLekoviByApotedaIdPage(@Param("ap_id") Long ApotekaID,@Param("pretragaLek") String pretragaLek, Pageable pageable);

	@Query(value = "select l from LEK l inner join APOTEKA_LEK al on l.id = al.lek.id where al.apoteka.id != :ap_id")
	Page<Lek> findAllLekoviByApoteka(@Param("ap_id") Long ApotekaID, Pageable pageable);
	@Query(value = "select l from LEK l join APOTEKA_LEK al on l.id = al.lek.id where al.apoteka.id != :ap_id")
	Page<Lek> findAllLekoviNotInApoteka(@Param("ap_id") Long ApotekaID, Pageable pageable);

	Lek findOneBySifra(String sifra);

	Page<Lek> findByNazivContaining(String title,Pageable page);

	@Query(value = "select l from LEK l where l.id NOT IN (select l1.id from LEK l1 inner join APOTEKA_LEK al on l1.id = al.lek.id where al.apoteka.id = :ap_id)")
	Page<Lek> findAllNotInApoteka(@Param("ap_id") Long alId, Pageable pageable);

	@Query(value = "select l from LEK l where l.id not in (select a.id from PACIJENT p inner join p.alergije a where :idPacijenta=p.id) and upper(l.naziv) like upper(:pretraga) and l.sifra not in :vecPreporuceniSifre")
    Page<Lek> getAllByPacijentNotAllergic(@Param("idPacijenta") Long idPacijenta, @Param("pretraga") String pretraga, Pageable pageable, List<String> vecPreporuceniSifre);

	@Query(value = "select z from LEK l inner join l.zamenskiLekovi z join APOTEKA_LEK al on al.lek.id=z.id where " +
			"al.apoteka.id=:apotekaID and al.kolicina>=:kolicina and l.sifra=:nedostupanLekSifra and " +
			"z.id not in (select a.id from PACIJENT p inner join p.alergije a where :idPacijenta=p.id) and " +
			"upper(z.naziv) like upper(:pretraga) and z.sifra not in :vecPreporuceniSifre")
	Page<Lek> getAllZamenskiLekovi(Long idPacijenta, String pretraga, Pageable pageable, List<String> vecPreporuceniSifre, String nedostupanLekSifra, Long apotekaID, Integer kolicina);
}
