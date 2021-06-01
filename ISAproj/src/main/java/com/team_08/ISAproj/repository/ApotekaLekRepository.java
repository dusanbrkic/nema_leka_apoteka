package com.team_08.ISAproj.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Lek;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

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
    @Query(value = "select al from APOTEKA_LEK al inner join LEK l on l.id = al.lek.id where l.sifra = :sifra and al.apoteka.id = :ap_id")
    ApotekaLek fetchBySifra(@Param("ap_id") Long apotekaID, @Param("sifra") String sifra);

    @Query(value = "select al from APOTEKA_LEK al inner join LEK l on l.id = al.lek.id where al.apoteka.id = :ap_id")
    Page<ApotekaLek> findAllApotekaLekoviByApotedaIdPage(@Param("ap_id") Long ApotekaID, Pageable pageable);

    @Query(value = "select al from APOTEKA_LEK al inner join LEK l on l.id = al.lek.id where al.apoteka.id = :ap_id and UPPER(l.naziv) LIKE UPPER(:pretragaLek)")
    Page<ApotekaLek> findAllApotekaLekoviByApotedaIdPageSearch(@Param("ap_id") Long ApotekaID, @Param("pretragaLek") String pretragaLek, Pageable pageable);

    @Query(value = "select al from APOTEKA_LEK al where al.lek.id = :l_id and al.apoteka.id = :ap_id")
    ApotekaLek findApotekaLekById(@Param("l_id") Long id, @Param("ap_id") Long apotekaId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select al from APOTEKA_LEK al where al.lek.id in :l_id_set and al.apoteka.id = :ap_id")
    List<ApotekaLek> findApotekaLekoviByIdWithLock(@Param("l_id_set") Set<Long> id, @Param("ap_id") Long apotekaId);

    @Query(value = "select al from APOTEKA_LEK al inner join LEK l on l.id = al.lek.id where al.apoteka.id = :ap_id and UPPER(l.naziv) LIKE UPPER(:pretragaLek)")
	Page<ApotekaLek> findAllApotekaLekoviSortedAndSearchedAndDone(Pageable pageable,@Param("pretragaLek") String title,@Param("ap_id") Long apotekaId);


	@Query(value = "select l from APOTEKA_LEK l where upper(l.lek.naziv) like upper(:pretraga)")
    Page<ApotekaLek> getAllLekovi(@Param("pretraga") String pretraga, Pageable pageable);
}
