package com.team_08.ISAproj.service;

import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.model.Pretplata;
import com.team_08.ISAproj.repository.PacijentRepository;
import com.team_08.ISAproj.repository.PretplataRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacijentService {
    @Autowired
    private PacijentRepository pacijentRepository;
    @Autowired 
    private PretplataRepository pretplataRepository;
    public Pacijent findOneById(Long id) {
        return pacijentRepository.findOneById(id);
    }
    
    
    public List<Pretplata> findPretplateApoteka(Long apotekaId){
    	return pretplataRepository.findPretplateApoteka(apotekaId);
    }
    
    public List<Pacijent> findAll(){
    	return pacijentRepository.findAll();
    }
    
    public Pacijent fetchPacijentWithAlergijeByCookie(String cookie){
    	return pacijentRepository.fetchPacijentWithAlergijeByCookie(cookie);
    }
}
