package com.team_08.ISAproj.service;

import com.team_08.ISAproj.dto.LekDTO;
import com.team_08.ISAproj.model.AdminApoteke;
import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Dermatolog;
import com.team_08.ISAproj.model.Farmaceut;
import com.team_08.ISAproj.model.Korisnik;
import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.repository.ApotekaLekRepository;
import com.team_08.ISAproj.repository.ApotekaRepository;
import com.team_08.ISAproj.repository.LekRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LekService {
	
	@Autowired
	private LekRepository lekRepository;
	@Autowired
	private ApotekaLekRepository apotekaLekRepository;
	@Autowired
	private ApotekaRepository apotekaRepository;
	
    public void saveLekApoteka(LekDTO lekDTO,String ApotekaID) {
    	Lek l = new Lek(lekDTO);
    	Apoteka a = apotekaRepository.findOneById(ApotekaID);
    	ApotekaLek al = new ApotekaLek();
    	al.setLek(l);
    	al.setApoteka(a);
    	al.setCena(lekDTO.getCena());
    	al.setKolicina(lekDTO.getKolicina());
    	al.setStaraCena(lekDTO.getStaraCena());
    	al.setIstekVazenjaCene(lekDTO.getIstekVazenjaCene());
    	a.getLekovi().add(al);
    	lekRepository.save(l);
    	apotekaLekRepository.save(al);
    	apotekaRepository.save(a);

    }
}
