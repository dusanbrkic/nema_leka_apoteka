package com.team_08.ISAproj.service;

import com.team_08.ISAproj.dto.FarmaceutDTO;
import com.team_08.ISAproj.dto.PregledDTO;
import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.Dermatolog;
import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.model.ZdravstveniRadnik;
import com.team_08.ISAproj.repository.DermatologRepository;
import com.team_08.ISAproj.repository.PregledRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.persistence.OptimisticLockException;
//import javax.transaction.Transactional;

@Service
@Transactional(readOnly = false)
public class PregledService {
    @Autowired
    private PregledRepository pregledRepository;
    @Autowired
    private ZdravstveniRadnikService zdravstveniRadnikService;

    
    //@Transactional(readOnly = false, rollbackFor = { SQLException.class })
    public Pregled dodajSlobTerminKonk(Pregled p) throws OptimisticLockException, CookieNotValidException, InterruptedException {
    	
    	//Thread.sleep(2000);
    	//LOCK
    	
    	//provera preklopa
    	if(!findAllTermsInDateRangeByDermatolog(p.getDermatolog().getCookieToken(), p.getVreme(), p.getKraj()).isEmpty()) {
    		throw new OptimisticLockException();
    	}
    	
    	

        pregledRepository.save(p);  	
		return p;
    	
    }
    //@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW,rollbackFor = { SQLException.class })
    public List<Pregled> findAllTermsInDateRangeByDermatolog(String cookie, LocalDateTime start, LocalDateTime end)
            throws CookieNotValidException {
        return pregledRepository.findAllTermsInDateRangeByDermatolog(cookie, start, end);

    }
    public Page<Pregled> findAllByZdravstveniRadnikPagedAndSortedAndSearchedAndDone(
            String cookie, Integer page, Integer size, String sortBy, Boolean sortDesc,
            String pretragaIme, String pretragaPrezime) throws CookieNotValidException {
        pretragaIme = "%" + pretragaIme + "%";
        pretragaPrezime = "%" + pretragaPrezime + "%";
        if (!sortBy.equals("vreme"))
            sortBy = "pac." + sortBy;
        Sort sort;
        if (sortDesc)
            sort = Sort.by(sortBy).descending();
        else
            sort = Sort.by(sortBy).ascending();
        return pregledRepository.findAllByZdravstveniRadnikPagedAndSortedAndSearchedAndDone(
                cookie, PageRequest.of(page, size, sort), pretragaIme, pretragaPrezime);
    }
    
    public List<Pregled> findPreglediFromKorisnikByZdravstveniRadnikID(Long idPacijent, Long idDermatolog) {
	    return pregledRepository.findPreglediFromKorisnikByZdravstveniRadnikID(idPacijent, idDermatolog);
    }
    
    public List<Pregled> findPreglediFromKorisnikByApotekaID(Long idPacijent, Long idApoteka) {
	    return pregledRepository.findPreglediFromKorisnikByApotekaID(idPacijent, idApoteka);
    }

    public List<Pregled> fetchAllWithPreporuceniLekoviInDateRangeByZdravstveniRadnik(
            String cookie, LocalDateTime dateStart, LocalDateTime dateEnd) throws CookieNotValidException {
        return pregledRepository.fetchAllWithPreporuceniLekoviInDateRangeByZdravstveniRadnik(cookie, dateStart, dateEnd);
    }

	@Transactional(readOnly = false)
    public List<Pregled> findAllInDateRangeByZdravstveniRadnik(LocalDateTime start, LocalDateTime end, String cookie) {
        return pregledRepository.findAllInDateRangeByZdravstveniRadnik(start, end, cookie);
    }

    public Pregled findOneById(Long id) {
        return pregledRepository.findOneById(id);
    }

	@Transactional(readOnly = false)
    public void savePregled(Pregled pregled) {
        pregledRepository.save(pregled);
    }

    public void saveSlobodanTermin(Pregled pregled) {
        pregledRepository.save(pregled);
    }

    public List<Pregled> proveraOdDatumaPregleda(LocalDateTime start, String username) {
        return pregledRepository.findAllInFutureByZdravstveniRadnik(start, username);
    }
    
    public List<Pregled> findAllFromApoteka(Long apoteka_id){
    	return pregledRepository.findAllfromApoteka(apoteka_id);
    }

    public List<Pregled> findAllByPacijent(Pacijent p, String sortBy, Boolean sortDesc){
    	return pregledRepository.findAllByPacijent(p, sortBy, sortDesc);
    	
    }

    public List<Pregled> findAllFromApotekaFinished(Long apoteka_id){
    	return pregledRepository.findAllFromApotekaFinished(apoteka_id);
    }

    
    public List<Pregled> findAllTermsInDateRangeByDermatologUser(String username, LocalDateTime start, LocalDateTime end){
        return pregledRepository.findAllTermsInDateRangeByDermatologUser(username, start, end);

    }
    public List<Pregled> findAllInDateRangeByPacijentId(LocalDateTime vreme, LocalDateTime kraj, Long idPacijenta) {
        return pregledRepository.findAllInDateRangeByPacijentId(vreme, kraj, idPacijenta);
    }

    public Pregled findOneStartsNow(String cookie, LocalDateTime start) {
        return pregledRepository.findOneStartsNow(cookie, start);
    }
    
    public List<Pregled> getAllPreporuceniLekoviFromPregledID(Long id) {
        return pregledRepository.getAllPreporuceniLekoviFromPregledID(id);
    }

	public List<Pregled> findAllFromApotekaFinishedYearMonth(Long id, int godina, int mesec) {
		// TODO Auto-generated method stub
		return pregledRepository.findAllFromApotekaFinishedYearMonth(id,godina,mesec);
	}
	public List<Pregled> findAllFromApotekaFinishedYear(Long id, int godina) {
		// TODO Auto-generated method stub
		return pregledRepository.findAllFromApotekaFinishedYear(id,godina);
	}
	public List<Pregled> findAllFromApotekaFinishedYearQuartal(Long id, int godina, int kvartal) {
		// TODO Auto-generated method stub
		return pregledRepository.findAllFromApotekaFinishedYearQuartal(id,godina,kvartal);
	}
	public List<Pregled> findAllFromApotekaFinishedDateRange(Long id, LocalDateTime start, LocalDateTime end) {
		// TODO Auto-generated method stub
		return pregledRepository.findAllFromApotekaFinishedDateRange(id,start,end);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public Pregled zakaziPregledKonkurentno(Long idPregleda, Pacijent p) throws InterruptedException, PregledRezervisanException {
		
		Pregled pregled = pregledRepository.findOneByIdWithLock(idPregleda);
		
		Thread.sleep(2000);
		
		if(pregled.isPregledZakazan()) {
			throw new PregledRezervisanException();
		}
		
		pregled.setPacijent(p);
		pregled.setPregledZakazan(true);
		pregledRepository.save(pregled);
		
		return pregled;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void savePregledAndCheckIfFarmacistsIsFreeConcurent(Pregled p, ZdravstveniRadnik farmaceut, LocalDateTime start, LocalDateTime end) throws OptimisticLockException, InterruptedException{
		
		//Thread.sleep(2000);		
        if (!findAllInDateRangeByZdravstveniRadnik(start, end, farmaceut.getCookieToken()).isEmpty()) {
        	throw new OptimisticLockException();
        }
        
        pregledRepository.save(p);
	}
}
