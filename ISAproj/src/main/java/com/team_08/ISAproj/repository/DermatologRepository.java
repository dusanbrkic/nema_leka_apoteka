package com.team_08.ISAproj.repository;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.Dermatolog;
import com.team_08.ISAproj.model.DermatologApoteka;
import com.team_08.ISAproj.model.FarmaceutApoteka;
import com.team_08.ISAproj.model.Korisnik;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.model.RezervacijaLek;
import com.team_08.ISAproj.model.ZdravstveniRadnik;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface DermatologRepository extends JpaRepository<Dermatolog, Long> {
	
    Dermatolog findOneByUsername(String username);

    Dermatolog findOneByCookieTokenValue(String cookie);

    Dermatolog findOneByEmailAdresa(String email_adresa);

    @Query(value = "SELECT d FROM DERMATOLOG d LEFT OUTER JOIN FETCH d.odsustva o where d.cookieTokenValue = :cookie")
    Dermatolog fetchDermatologWithOdsustva(@Param("cookie") String cookie);

    @Query(value = "SELECT d FROM DERMATOLOG d LEFT OUTER JOIN FETCH d.odsustva o where d.cookieTokenValue = :cookie and (:start < o.kraj and :end > o.pocetak)")
    ZdravstveniRadnik fetchDermatologWithOdsustvaInDateRange(@Param("cookie") String cookie, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value = "SELECT d from DERMATOLOG d inner join DERMATOLOG_APOTEKA da on d.id = da.dermatolog.id where da.apoteka.id = :ap_id")
    Dermatolog fetchDermatologByApotekaId(@Param("ap_id") Long apotekaId);

    @Query(value = "SELECT da FROM DERMATOLOG_APOTEKA da inner join DERMATOLOG d on d.id = da.dermatolog.id where da.apoteka.id = :ap_id")
    Page<DermatologApoteka> fetchDermatologApotekaByApotekaIdPage(@Param("ap_id") Long apotekaId, Pageable page);

    @Query(value = "SELECT da FROM DERMATOLOG_APOTEKA da inner join DERMATOLOG d on d.id = da.dermatolog.id where da.apoteka.id = :ap_id")
    List<DermatologApoteka> fetchDermatologApotekaByApotekaId(@Param("ap_id") Long apotekaId);

    //proveramo da li se dermatologovi termini rada u drugim apotekama poklapaju sa novim
    @Query(value = "SELECT da FROM DERMATOLOG_APOTEKA da join DERMATOLOG d on d.id = da.dermatolog.id where :username = d.username and not ((:start > da.radnoVremeKraj and :kraj > da.radnoVremeKraj) or (:start< da.radnoVremePocetak and :kraj < da.radnoVremePocetak))")
    List<DermatologApoteka> findIfDermatologTimesOverlap(@Param("username") String username, @Param("start") LocalDateTime start, @Param("kraj") LocalDateTime end);


    //svi dermatolozi koji ne rade u apoteci - PAGE
    @Query(value = "select d from DERMATOLOG d where d.id NOT IN (select d1.id from DERMATOLOG d1 inner join DERMATOLOG_APOTEKA derma_apoteke on d1.id = derma_apoteke.dermatolog.id where derma_apoteke.apoteka.id = :ap_id)")
    Page<Dermatolog> fetchNotWorkingInApotekaPage(@Param("ap_id") Long apotekaId, Pageable page);

    //svi dermatolozi koji ne rade u apoteci - LIST
    @Query(value = "select d from DERMATOLOG d where d.id NOT IN (select d1.id from DERMATOLOG d1 inner join DERMATOLOG_APOTEKA derma_apoteke on d1.id = derma_apoteke.dermatolog.id where derma_apoteke.apoteka.id = :ap_id)")
    List<Dermatolog> fetchNotWorkingInApotekaList(@Param("ap_id") Long apotekaId);

    //sva radna vremena dermatologa
    @Query(value = "select da from DERMATOLOG_APOTEKA da where da.dermatolog.id = :d_id")
    List<DermatologApoteka> fetchAllWorkingTimesAndPricesForDermatolog(@Param("d_id") Long dermatologId);

    //proveramo da li se dermatologovi termini rada u drugim apotekama poklapaju sa izmenjenim
    @Query(value = "SELECT da FROM DERMATOLOG_APOTEKA da join DERMATOLOG d on d.id = da.dermatolog.id where :username = d.username and not ((:start > da.radnoVremeKraj and :kraj > da.radnoVremeKraj) or (:start< da.radnoVremePocetak and :kraj < da.radnoVremePocetak)) and not da.apoteka.id = :ap_id ")
    List<DermatologApoteka> findIfDermatologTimesOverlapNotInApoteka(@Param("username") String username, @Param("ap_id") Long apotekaId, @Param("start") LocalDateTime start, @Param("kraj") LocalDateTime end);

    @Query(value = "SELECT d FROM DERMATOLOG d join DERMATOLOG_APOTEKA da on da.dermatolog.id=d.id where d.cookieTokenValue=:cookie and da.apoteka.id=:idApoteke and (:start > da.radnoVremePocetak and :end > da.radnoVremePocetak) and (:start< da.radnoVremeKraj and :end < da.radnoVremeKraj)")
    Dermatolog checkRadnoVreme(LocalTime start, LocalTime end, String cookie, Long idApoteke);
    

}
