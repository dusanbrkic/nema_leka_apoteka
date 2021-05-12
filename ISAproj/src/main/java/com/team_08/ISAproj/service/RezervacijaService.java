package com.team_08.ISAproj.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Rezervacija findRezervacijaByIdAndApotekaIdBeforeRok(Long idRezervacije, Long idApoteke, LocalDateTime tommorow) {
	    return rezervacijaRepository.findRezervacijaByIdAndApotekaIdBeforeRok(idRezervacije, idApoteke, tommorow);
    }
}