package com.team_08.ISAproj.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.team_08.ISAproj.model.Odsustvo;
import com.team_08.ISAproj.repository.OdsustvoRepository;

@Service
public class OdsustvoService {

	@Autowired 
	private OdsustvoRepository odsustvoRepository;

	public void saveOdsustvo(Odsustvo odsustvo) {
		odsustvoRepository.save(odsustvo);
		
	}
	public List<Odsustvo> fetchOdsustvaByApotekaId(Long apotekaId){
		
		List<Odsustvo> odsustvaDerma = odsustvoRepository.findAllDermaOdsustvoApotekaId(apotekaId,"cekanju");
		List<Odsustvo> odsustvaFarma = odsustvoRepository.findAllFarmaOdsustvoApotekaId(apotekaId,"cekanju");
		for(Odsustvo o: odsustvaFarma) {
			odsustvaDerma.add(o);
		}
		return odsustvaDerma;
		
	}
	public Odsustvo findOne(Long id) {
		return odsustvoRepository.findById(id).orElseGet(null);
	}

    public Set<Odsustvo> fetchOdsustvaByZdravstveniRadnikCookieInDateRange(String cookie, LocalDateTime start, LocalDateTime end) {
		return odsustvoRepository.fetchOdsustvaByZdravstveniRadnikCookieInDateRange(cookie, start, end);
    }
}
