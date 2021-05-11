package com.team_08.ISAproj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team_08.ISAproj.model.Promocija;
import com.team_08.ISAproj.repository.PromocijaRepository;

@Service
public class PromocijaService {
	@Autowired
	private PromocijaRepository promocijaRepository;
	
	
	public void savePromocija(Promocija p) {
		promocijaRepository.save(p);
	}
}
