package com.team_08.ISAproj.service;

import com.team_08.ISAproj.model.*;
import com.team_08.ISAproj.repository.DermatologRepository;
import com.team_08.ISAproj.repository.FarmaceutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ZdravstveniRadnikService {

    @Autowired
    private DermatologRepository dermatologRepository;
    @Autowired
    private FarmaceutRepository farmaceutRepository;

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
    
    public Page<DermatologApoteka> fetchDermatologsByApotekaId(Long ApotekaId,Pageable page){
		
    	
    	return dermatologRepository.fetchDermatologApotekaByApotekaId(ApotekaId, page);
    }
    public Dermatolog findOneByUsername(String username) {
    	
    	return dermatologRepository.findOneByUsername(username);
    }

    public ZdravstveniRadnik findOneByCookie(String cookie) {
        ZdravstveniRadnik z = dermatologRepository.findOneByCookieTokenValue(cookie);
        if (z==null) {
            z = farmaceutRepository.findOneByCookieTokenValue(cookie);
        }
        return z;
    }
}
