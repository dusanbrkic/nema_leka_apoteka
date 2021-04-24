package com.team_08.ISAproj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team_08.ISAproj.dto.LekDTO;
import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.Narudzbenica;
import com.team_08.ISAproj.model.NarudzbenicaLek;
import com.team_08.ISAproj.repository.NarudzbenicaLekRepository;
import com.team_08.ISAproj.repository.NarudzbenicaRepository;

@Service
public class NarudzbenicaService {
	
	@Autowired
	private NarudzbenicaRepository narudzbenicaRepository;
	@Autowired 
	private NarudzbenicaLekRepository narudzbenicaLekRepository;
	
	public void saveNarudzbenica(Narudzbenica n) {
		narudzbenicaRepository.save(n);
    }
	
	public void saveNarudzbenicaLek(NarudzbenicaLek nl)
	{
		narudzbenicaLekRepository.save(nl);
	}
	
    public List<Narudzbenica> findAllNarudzbenice() {
    	
    	return narudzbenicaRepository.findAll();
    }
    
    public List<NarudzbenicaLek> findAllNarudzbeniceLek() {
    	
    	return narudzbenicaLekRepository.findAll();
    }
}
