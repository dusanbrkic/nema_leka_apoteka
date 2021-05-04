package com.team_08.ISAproj.service;

import com.team_08.ISAproj.model.*;
import com.team_08.ISAproj.repository.DermatologApotekaRepository;
import com.team_08.ISAproj.repository.DermatologRepository;
import com.team_08.ISAproj.repository.FarmaceutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ZdravstveniRadnikService {

    @Autowired
    private DermatologRepository dermatologRepository;
    @Autowired
    private FarmaceutRepository farmaceutRepository;
    @Autowired
    private DermatologApotekaRepository dermatologApotekaRepository;
    public ZdravstveniRadnik fetchZdravstveniRadnikWithOdsustva(String cookie) {
        ZdravstveniRadnik z = dermatologRepository.fetchDermatologWithOdsustva(cookie);
        if (z==null) {
            z = farmaceutRepository.fetchFarmaceutWithOdsustva(cookie);
        }
        return z;
    }

    public ZdravstveniRadnik fetchZdravstveniRadnikWithOdsustvaInDateRange(String cookie, LocalDateTime start, LocalDateTime end) {
        ZdravstveniRadnik z = dermatologRepository.fetchDermatologWithOdsustvaInDateRange(cookie, start, end);
        if (z==null) {
            z = farmaceutRepository.fetchFarmaceutWithOdsustvaInDateRange(cookie, start, end);
        }
        return z;
    }
    //svi dermatolozi u apoteci page
    public Page<DermatologApoteka> fetchDermatologsByApotekaId(Long ApotekaId,Pageable page){
		
    	
    	return dermatologRepository.fetchDermatologApotekaByApotekaIdPage(ApotekaId, page);
    }
    public Dermatolog findOneByUsername(String username) {
    	
    	return dermatologRepository.findOneByUsername(username);
    }
  //svi dermatolozi u apoteci list
    public List<DermatologApoteka> fetchDermatologsByApotekaId(Long ApotekaId){
		
  
    	return dermatologRepository.fetchDermatologApotekaByApotekaId(ApotekaId);
    }
    
    public List<Farmaceut> fetchFarmaceutsByApotekaId(Long ApotekaId){
    	
    	
    	return farmaceutRepository.findAllByApotekaId(ApotekaId);
    }
    //dodavanje farmaceuta
    public void saveFarmaceut(Farmaceut farmaceut) {
    	farmaceutRepository.save(farmaceut);
    }
    //svi dermatolozi koji ne rade u apoteci PAGE
    public Page<Dermatolog> fetchDermatologsNotInApotekaPage(Long ApotekaId,Pageable page){	
		return dermatologRepository.fetchNotWorkingInApotekaPage(ApotekaId, page);	
    }
    //svi dermatolozi koji ne rade u apoteci LIST
    public List<Dermatolog> fetchDermatologsNotInApotekaList(Long ApotekaId){	
		return dermatologRepository.fetchNotWorkingInApotekaList(ApotekaId);	
    }
    //za dermatologa sva njegova radna mesta
    public List<DermatologApoteka> fetchAllWorkingTimesAndPricesForDermatolog(Long dermatologId){
    	return dermatologRepository.fetchAllWorkingTimesAndPricesForDermatolog(dermatologId);
    }
    //provera radnog vremena dermatolog
    public List<DermatologApoteka> checkIfGivenWorkHoursAreOk(String username,LocalDateTime start, LocalDateTime end){
    	
    	return dermatologRepository.findIfDermatologTimesOverlap(username,start, end);
    }

	public void addDermatologApoteke(DermatologApoteka da) {
		dermatologApotekaRepository.save(da);
		
	}
	public DermatologApoteka findDermatologApoteka(Long dermatologId,Long apotekaId) {
		return dermatologApotekaRepository.findOneDermatologApotekaByIds(dermatologId,apotekaId);
	}
	public void deleteDermatologApoteke(Long dermatologId,Long apotekaId) {
		dermatologApotekaRepository.deleteById(dermatologApotekaRepository.findOneDermatologApotekaByIds(dermatologId,apotekaId).getId());
	}
	//provera za termine rada osim za izabranu apoteku
	public List<DermatologApoteka> checkIfGivenWorkHoursAreOk(String username, Long apotekaId, LocalDateTime start, LocalDateTime end) {
		return dermatologRepository.findIfDermatologTimesOverlapNotInApoteka(username,apotekaId,start,end);
	}

	public void deleteFarmaceuta(Long id) {
		farmaceutRepository.deleteById(id);
		
	}
}
