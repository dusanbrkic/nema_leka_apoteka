package com.team_08.ISAproj.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.team_08.ISAproj.exceptions.RezervacijaNeispravnaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.team_08.ISAproj.dto.LekDTO;
import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.Narudzbenica;
import com.team_08.ISAproj.model.NarudzbenicaLek;
import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.model.Rezervacija;
import com.team_08.ISAproj.model.RezervacijaLek;
import com.team_08.ISAproj.repository.NarudzbenicaLekRepository;
import com.team_08.ISAproj.repository.NarudzbenicaRepository;
import com.team_08.ISAproj.repository.RezervacijaLekRepository;
import com.team_08.ISAproj.repository.RezervacijaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RezervacijaService {
	
	@Autowired
	private RezervacijaRepository rezervacijaRepository;
	@Autowired 
	private RezervacijaLekRepository rezervacijaLekRepository;
	
	public Rezervacija saveRezervacija(Rezervacija n) {
		return rezervacijaRepository.save(n);
    }
	
	public void saveRezervacijaLek(RezervacijaLek nl)
	{
		rezervacijaLekRepository.save(nl);
	}
	
    public List<Rezervacija> findAllRezervacija() {
    	
    	return rezervacijaRepository.findAll();
    }
    public List<RezervacijaLek> findAllRezervacijaLekNotFinished(Long id,Long lek) {
    	
    	return rezervacijaLekRepository.findAllRezervacijaLekNotFinished(id,lek);
    }
    public List<RezervacijaLek> findAllRezervacijaLek() {
    	
    	return rezervacijaLekRepository.findAll();
    }
    public Rezervacija findRezervacija(Long id) {
    	
    	return rezervacijaRepository.findById(id).orElseGet(null);
    }
    public Rezervacija findRezervacijaByID(Long id) {
    	return rezervacijaRepository.findByRezervacijaId(id);
    }
    public List<RezervacijaLek> findRezervacijaLekByRezervacijaID(Long id){
    	return rezervacijaLekRepository.findAllRezervacijaLekFromRezervacija(id);
    }
    public List<Rezervacija> findRezervacijeByPacijent(Pacijent p) {
    	return rezervacijaRepository.findAllRezervacijeFromKorisnik(p);
    }
    public void removeRezervacijaLek(Long id) {
    	rezervacijaLekRepository.deleteById(id);
    }
    public void removeRezervacija(Long id) {
    	rezervacijaRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Rezervacija obradiRezervacijuKonkurentno(Long idRezervacije, Long idApoteke, LocalDateTime tommorow, boolean wait) throws RezervacijaNeispravnaException {

	    Rezervacija r = rezervacijaRepository.findRezervacijaByIdAndApotekaIdBeforeRok(idRezervacije, idApoteke, tommorow);

        if(r==null)
            throw new RezervacijaNeispravnaException();

        System.out.println("----------------------------[THREAD " + Thread.currentThread().getId() +
                "] ZAKLJUCAO SAM OBJEKAT!------------------------------");

        r.setPreuzeto(true);

        if (wait)
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        rezervacijaRepository.save(r);
        System.out.println("----------------------------[THREAD " + Thread.currentThread().getId() +
                "] SACUVAO SAM OBJEKAT!------------------------------");

	    return r;
    }

    public List<RezervacijaLek> findRezervacijaLekFromKorisnikByLek(Long idPacijent, Long idLeka) {
	    return rezervacijaRepository.findRezervacijaLekFromKorisnikByLek(idPacijent, idLeka);
    }
    
    public List<RezervacijaLek> findRezervacijaLekFromKorisnikByApoteka(Long idPacijent, Long idApoteke) {
	    return rezervacijaRepository.findRezervacijaLekFromKorisnikByApoteka(idPacijent, idApoteke);
    }
    public List<Rezervacija> findAllRezervacijeFinishedYear(Long id,int godina){
    	
    	return rezervacijaRepository.findAllRezervacijeFinishedYear(id,godina);
    	
    }
    	
    public Rezervacija fetchRezervacijaWithLekoviByIdAndApotekaIdBeforeRok(Long idRezervacije, Long idApoteke, LocalDateTime tommorow) {
	    return rezervacijaRepository.fetchRezervacijaWithLekoviByIdAndApotekaIdBeforeRok(idRezervacije, idApoteke, tommorow);
    }
    public List<Rezervacija> findAllRezervacijeFinishedDateRange(Long id,LocalDateTime start, LocalDateTime end){
    	
    	return rezervacijaRepository.findAllRezervacijeFinishedDateRange(id, start, end);
    	
    }
}