package com.team_08.ISAproj.repository;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.Farmaceut;
import com.team_08.ISAproj.model.Korisnik;
import com.team_08.ISAproj.model.ZdravstveniRadnik;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface FarmaceutRepository extends JpaRepository<Farmaceut, Long> {
    Farmaceut findOneByUsername(String username);

    Farmaceut findOneByCookieTokenValue(String cookie);

    Farmaceut findOneByEmailAdresa(String email_adresa);

    @Query(value = "SELECT f FROM FARMACEUT f LEFT OUTER JOIN FETCH f.odsustva o where f.cookieTokenValue = :cookie")
    Farmaceut fetchFarmaceutWithOdsustva(@Param("cookie") String cookie);

    @Query(value = "SELECT f FROM FARMACEUT f LEFT OUTER JOIN FETCH f.odsustva o where f.cookieTokenValue = :cookie and (:start < o.kraj and :end > o.pocetak)")
    ZdravstveniRadnik fetchFarmaceutWithOdsustvaInDateRange(@Param("cookie") String cookie, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    List<Farmaceut> findAllByApotekaId(Long ApotekaId);

    Page<Farmaceut> findAllByApotekaId(Long ApotekaId, Pageable page);

    @Query(value = "select f from FARMACEUT f where f.apoteka.id = :ap_id and UPPER(f.prezime) LIKE UPPER(:pretragaPrezime) and UPPER(f.ime) LIKE UPPER(:pretragaIme) and f.prosecnaOcena >= :ocena and (:start < f.radnoVremePocetak and :end > f.radnoVremeKraj)")
	Page<Farmaceut> findFarmaceutApotekaByIdSearchedSorted(Pageable pageable,@Param("ap_id") Long apotekaId,@Param("pretragaIme") String pretragaIme,@Param("pretragaPrezime") String pretragaPrezime,@Param("ocena") Double ocena,@Param("start") LocalDateTime start,@Param("end") LocalDateTime end);

    @Query(value = "select f from FARMACEUT f where f.cookieTokenValue=:cookie and (:start > f.radnoVremePocetak and :end > f.radnoVremePocetak) and (:start< f.radnoVremeKraj and :end < f.radnoVremeKraj)")
    ZdravstveniRadnik checkRadnoVreme(LocalTime start, LocalTime end, String cookie);
    @Query(value = "select f from FARMACEUT f where UPPER(f.prezime) LIKE UPPER(:pretragaPrezime) and UPPER(f.ime) LIKE UPPER(:pretragaIme) and f.prosecnaOcena >= :ocena and (:start < f.radnoVremePocetak and :end > f.radnoVremeKraj)")
    Page<Farmaceut> findFarmaceutSearchedSorted(Pageable pageable,@Param("pretragaIme") String pretragaIme,@Param("pretragaPrezime") String pretragaPrezime,@Param("ocena") Double ocena,@Param("start") LocalDateTime start,@Param("end") LocalDateTime end);
    @Query(value = "select f from FARMACEUT f where f.apoteka.naziv = :ap_naziv and UPPER(f.prezime) LIKE UPPER(:pretragaPrezime) and UPPER(f.ime) LIKE UPPER(:pretragaIme) and f.prosecnaOcena >= :ocena and (:start < f.radnoVremePocetak and :end > f.radnoVremeKraj)")
	Page<Farmaceut> findFarmaceutSearchedSortedNaziv(Pageable pageable,@Param("ap_naziv") String apotekaNaziv, @Param("pretragaIme") String pretragaIme,@Param("pretragaPrezime") String pretragaPrezime,@Param("ocena") Double ocena,@Param("start") LocalDateTime start,@Param("end") LocalDateTime end);
}
