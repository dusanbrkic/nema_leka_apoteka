package com.team_08.ISAproj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.team_08.ISAproj.model.ZahtevLek;
import com.team_08.ISAproj.repository.ZahtevLekRepository;

@Service
public class ZahtevLekService {
	
	@Autowired
	private ZahtevLekRepository zahtevLekRepository;
	
	public void saveZahtevLek(ZahtevLek zl) {
		
		zahtevLekRepository.save(zl);
	}

	public Page<ZahtevLek> findAllByApotekaId(Integer page, Integer size, Long id) {
		// TODO Auto-generated method stub
		return zahtevLekRepository.findAllByApotekaId(PageRequest.of(page, size),id);
	}
	
}
