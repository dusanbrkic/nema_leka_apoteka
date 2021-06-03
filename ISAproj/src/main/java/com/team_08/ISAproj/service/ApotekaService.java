package com.team_08.ISAproj.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import com.team_08.ISAproj.repository.ApotekaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.Pregled;

import org.springframework.stereotype.Service;


@Service
public class ApotekaService {

	@Autowired
	private ApotekaRepository apotekaRepository;

	public void save(Apoteka a) {
		apotekaRepository.save(a);
	}
	
	public List<Apoteka> findAll() {
		List<Apoteka> apoteke = apotekaRepository.findAll();
		return apoteke;
	}

	public Apoteka findOne(Long id) {
		return apotekaRepository.findById(id).orElseGet(null);
	}

	public Apoteka findOneByID(Long id) {
		return apotekaRepository.findOneByID(id);
	}

	public Apoteka create(Apoteka apoteka) {
		Long id = apoteka.getId();

		//Apoteka tempApoteka = apotekaRepository.create(apoteka);
		return null;
	}

	public Page<Apoteka> findAll(Pageable page) {
		return apotekaRepository.findAll(page);
	}

	public Page<Apoteka> findByNazivContaining(String naziv, Pageable pageable) {
		return apotekaRepository.findByNazivContaining(naziv, pageable);
	}

    public Apoteka fetchOneByIdWithAdmini(Long idApoteke) {
		return apotekaRepository.fetchOneByIdWithAdmini(idApoteke);
    }
    
    public List<Apoteka> findAllInDateRangeWithFreeZdravstveniRadnik(LocalDateTime start, LocalDateTime end) {
        return apotekaRepository.findAllInDateRangeWithFreeZdravstveniRadnik(start, end);
    }
    
    public Page<Apoteka> getAllApotekePaged(int page, int pageSize, String pretragaNaziv,  String pretragaAdresa, boolean smer, Double cenaOD, Double cenaDO, String sortBy) {
    	pretragaNaziv = "%" + pretragaNaziv + "%";
    	pretragaAdresa = "%" + pretragaAdresa + "%";
    	return apotekaRepository.getAllApotekePaged(pretragaNaziv, pretragaAdresa, smer, cenaOD, cenaDO, sortBy, PageRequest.of(page, pageSize));
    }
}
