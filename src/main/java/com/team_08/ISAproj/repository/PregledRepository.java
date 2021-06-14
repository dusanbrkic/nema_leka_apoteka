package com.team_08.ISAproj.repository;

import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.model.Pregled;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.LockModeType;

@Repository
public interface PregledRepository extends JpaRepository<Pregled, Long> {

    // za kalendar
    @Query(value = "SELECT DISTINCT p FROM PREGLED p LEFT OUTER JOIN FETCH p.preporuceniLekovi l where p.zdravstveniRadnik.cookieTokenValue = :cookie and (:startDate < p.kraj and :endDate > p.vreme)")
    List<Pregled> fetchAllWithPreporuceniLekoviInDateRangeByZdravstveniRadnik(@Param("cookie") String cookie, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT DISTINCT p FROM PREGLED p LEFT OUTER JOIN FETCH p.preporuceniLekovi l where p.id = :idPregled")
    List<Pregled> getAllPreporuceniLekoviFromPregledID(@Param("idPregled") Long idPregled);

    
    // za listu pregledanih pacijenata
    @Query(value = "SELECT p FROM PREGLED p JOIN PACIJENT pac ON p.pacijent.id=pac.id where p.zdravstveniRadnik.cookieTokenValue = :cookie and p.pregledObavljen = true and UPPER(p.pacijent.prezime) LIKE UPPER(:pretragaPrezime) and UPPER(p.pacijent.ime) LIKE UPPER(:pretragaIme)")
    Page<Pregled> findAllByZdravstveniRadnikPagedAndSortedAndSearchedAndDone(@Param("cookie") String cookie, Pageable pageable, @Param("pretragaIme") String pretragaIme, @Param("pretragaPrezime") String pretragaPrezime);

    // za proveru validnosti zahteva za odsustvo
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT p FROM PREGLED p where p.zdravstveniRadnik.cookieTokenValue = :cookie and (:start < p.kraj and :end > p.vreme)")
    List<Pregled> findAllInDateRangeByZdravstveniRadnik(LocalDateTime start, LocalDateTime end, String cookie);

    Pregled findOneById(Long id);

    //vadjenje termina za pregled
    @Query(value = "SELECT p FROM PREGLED p where p.zdravstveniRadnik.cookieTokenValue = :cookie and p.pregledZakazan=false and (:start < p.kraj and :end > p.vreme)")
    List<Pregled> findAllTermsInDateRangeByDermatolog(String cookie, LocalDateTime start, LocalDateTime end);

    //provera da li ima zakazan neki pregled u buducnosti
    @Query(value = "SELECT p FROM PREGLED p where p.zdravstveniRadnik.username = :username and p.vreme > :start")
    List<Pregled> findAllInFutureByZdravstveniRadnik(LocalDateTime start, String username);
    
    // nadji sve preglede jedne apoteke
    @Query(value = "SELECT p FROM PREGLED p "
    			+ " inner join DERMATOLOG d on d.id = p.zdravstveniRadnik.id"
    			+ " where p.apoteka.id = :apoteka_id and p.pregledZakazan = false and p.pregledObavljen = false")
    List<Pregled> findAllfromApoteka(Long apoteka_id);
    
    // nadji sve preglede jednog pacijenta
    @Query(value = "SELECT p FROM PREGLED p"
    			+ " where p.pacijent = :pacijent"
    			+ " ORDER BY"
    			+ " CASE WHEN :sortBy = 'datum' and :sortDesc = true THEN p.vreme END DESC,"
    			+ " CASE WHEN :sortBy = 'datum' and :sortDesc = false THEN p.vreme END ASC,"
    			+ " CASE WHEN :sortBy = 'cena' and :sortDesc = true THEN p.cena END DESC,"
    			+ " CASE WHEN :sortBy = 'cena' and :sortDesc = false THEN p.cena END ASC")
    List<Pregled> findAllByPacijent(Pacijent pacijent, @Param("sortBy") String sortBy, @Param("sortDesc") Boolean sortDesc);

    @Query(value = "SELECT p FROM PREGLED p where p.pacijent.id=:idPacijenta and (:vreme < p.kraj and :kraj > p.vreme)")
    List<Pregled> findAllInDateRangeByPacijentId(LocalDateTime vreme, LocalDateTime kraj, Long idPacijenta);

    @Query(value = "SELECT p from PREGLED p where p.zdravstveniRadnik.cookieTokenValue = :cookie and :start >= p.vreme and :start <= p.kraj and p.pregledObavljen=false and p.pregledZakazan=true")
    Pregled findOneStartsNow(String cookie, LocalDateTime start);
    

    
    
	@Query(value = "select p from PREGLED p where p.zdravstveniRadnik.id = :idDermatolog and p.pacijent.id = :idPacijent")
	List<Pregled> findPreglediFromKorisnikByZdravstveniRadnikID(@Param("idPacijent") Long idPacijent, @Param("idDermatolog") Long idDermatolog);
	
	@Query(value = "select p from PREGLED p where p.apoteka.id = :idApoteka and p.pacijent.id = :idPacijent")
	List<Pregled> findPreglediFromKorisnikByApotekaID(@Param("idPacijent") Long idPacijent, @Param("idApoteka") Long idApoteka);


    @Query(value = "SELECT p FROM PREGLED p where p.apoteka.id = :apoteka_id and p.pregledZakazan = true and p.pregledObavljen = true order by p.kraj")
	List<Pregled> findAllFromApotekaFinished(Long apoteka_id);

    //svi zavrseni pregledi za izabranu godinu
    @Query(value = "SELECT p FROM PREGLED p where p.apoteka.id = :apoteka_id and EXTRACT(YEAR from p.kraj) = :godina and p.pregledZakazan = true and p.pregledObavljen = true ")
	List<Pregled> findAllFromApotekaFinishedYear(Long apoteka_id, Integer godina);
    
    //svi zavrseni pregledi za izabranu godinu i mesec
    @Query(value = "SELECT p FROM PREGLED p where p.apoteka.id = :apoteka_id and EXTRACT(YEAR from p.kraj) = :godina and EXTRACT(MONTH from p.kraj) = :mesec and p.pregledZakazan = true and p.pregledObavljen = true")
	List<Pregled> findAllFromApotekaFinishedYearMonth(Long apoteka_id, Integer godina, Integer mesec);
    
    //svi zavrseni pregledi za izabranu godinu i kvartal
    @Query(value = "SELECT p FROM PREGLED p where p.apoteka.id = :apoteka_id and EXTRACT(YEAR from p.kraj) = :godina and EXTRACT(QUARTER from p.kraj) = :kvartal and p.pregledZakazan = true and p.pregledObavljen = true")
	List<Pregled> findAllFromApotekaFinishedYearQuartal(Long apoteka_id, Integer godina,Integer kvartal);
    
    //svi zavrseni pregledi u date rangeu
    @Query(value = "SELECT p FROM PREGLED p where p.apoteka.id = :apoteka_id and p.kraj > :start and  p.kraj < :end and p.pregledZakazan = true and p.pregledObavljen = true")
	List<Pregled> findAllFromApotekaFinishedDateRange(Long apoteka_id, LocalDateTime start,LocalDateTime end);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT p FROM PREGLED p where p.zdravstveniRadnik.username = :username and p.pregledZakazan=false and (:start < p.kraj and :end > p.vreme)")
	List<Pregled> findAllTermsInDateRangeByDermatologUser(String username, LocalDateTime start, LocalDateTime end);
}
