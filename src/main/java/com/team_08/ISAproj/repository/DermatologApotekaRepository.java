package com.team_08.ISAproj.repository;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team_08.ISAproj.model.DermatologApoteka;

@Repository
public interface DermatologApotekaRepository extends JpaRepository<DermatologApoteka, Long> {


    @Query(value = "select da from DERMATOLOG_APOTEKA da where da.dermatolog.id = :d_id and da.apoteka.id = :ap_id")
    DermatologApoteka findOneDermatologApotekaByIds(@Param("d_id") Long dermatologId, @Param("ap_id") Long apotekaId);
    // and UPPER(da.dermatolog.prezime) LIKE UPPER(:pretragaPrezime) and UPPER(da.dermatolog.ime) LIKE UPPER(:pretragaIme)
    //@Param("pretragaIme") String pretragaIme,
	//@Param("pretragaPrezime") String pretragaPrezime
    //where da.apoteka.id = :ap_id
    @Query(value = "select da from DERMATOLOG_APOTEKA da inner join DERMATOLOG d on d.id = da.dermatolog.id where da.apoteka.id = :ap_id and UPPER(d.prezime) LIKE UPPER(:pretragaPrezime) and UPPER(d.ime) LIKE UPPER(:pretragaIme) and d.prosecnaOcena >= :ocena and (:start < da.radnoVremePocetak and :end > da.radnoVremeKraj)")
    Page<DermatologApoteka> findOneDermatologApotekaByIdSearchedSorted(Pageable pageable,@Param("ap_id") Long apotekaId,@Param("pretragaIme") String pretragaIme,@Param("pretragaPrezime") String pretragaPrezime,@Param("ocena") Double ocena,@Param("start") LocalTime start,@Param("end") LocalTime end);
    @Query(value = "select da from DERMATOLOG_APOTEKA da inner join DERMATOLOG d on d.id = da.dermatolog.id where da.apoteka.naziv = :ap_naziv and UPPER(d.prezime) LIKE UPPER(:pretragaPrezime) and UPPER(d.ime) LIKE UPPER(:pretragaIme) and d.prosecnaOcena >= :ocena and (:start < da.radnoVremePocetak and :end > da.radnoVremeKraj)")
    Page<DermatologApoteka> findAllDermatologApotekaByApotekaNazivSearched(Pageable pageable,@Param("ap_naziv") String apotekaNaziv,@Param("pretragaIme") String pretragaIme,@Param("pretragaPrezime") String pretragaPrezime,@Param("ocena") Double ocena,@Param("start") LocalTime start,@Param("end") LocalTime end);
    @Query(value = "select da from DERMATOLOG_APOTEKA da inner join DERMATOLOG d on d.id = da.dermatolog.id where UPPER(d.prezime) LIKE UPPER(:pretragaPrezime) and UPPER(d.ime) LIKE UPPER(:pretragaIme) and d.prosecnaOcena >= :ocena and (:start < da.radnoVremePocetak and :end > da.radnoVremeKraj)")
    Page<DermatologApoteka> findAllDermatologApotekaSearched(Pageable pageable,@Param("pretragaIme") String pretragaIme,@Param("pretragaPrezime") String pretragaPrezime,@Param("ocena") Double ocena,@Param("start") LocalTime start,@Param("end") LocalTime end);
}
