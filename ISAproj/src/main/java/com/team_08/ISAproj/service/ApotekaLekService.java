package com.team_08.ISAproj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.repository.ApotekaLekRepository;
import com.team_08.ISAproj.repository.LekRepository;
@Service
public class ApotekaLekService {

	
	@Autowired
	private ApotekaLekRepository apotekaLekRepository;
	@Autowired 
	private LekRepository lekRepository;
	
	public List<ApotekaLek> findAll() {
		List<ApotekaLek> apotekaLekovi = apotekaLekRepository.findAll();
		return apotekaLekovi;
	}

	public ApotekaLek findOne(Long id) {
		return apotekaLekRepository.findById(id).orElseGet(null);
	}
	public Page<ApotekaLek> findOneByApoteka(Long id,Pageable pageable) {
		return apotekaLekRepository.findAllByApotekaId(id, pageable);
	}
	public List<ApotekaLek> findOneByApoteka(Long id) {
		return apotekaLekRepository.findAllByApotekaId(id);
	}
	public Apoteka create(ApotekaLekRepository apotekaLek) {
		return null;
	}
	
	public Page<ApotekaLek> findAll(Pageable page) {
		return apotekaLekRepository.findAll(page);
	}
	
	public Page<ApotekaLek> findByLekContaining(Lek lek, Pageable pageable) {
		return apotekaLekRepository.findByLekContaining(lek, pageable);
	}
	public Page<ApotekaLek> findLekoviByApotekaID(Long ApotekaID, Pageable paging){
		return apotekaLekRepository.findAllApotekaLekoviByApotedaIdPage(ApotekaID, paging);
	}
	public ApotekaLek findOneBySifra(Lek l, Long ApotekaID) {
    	List<ApotekaLek> apotekaLekovi = apotekaLekRepository.findAllByApotekaId(ApotekaID);
    	ApotekaLek al = apotekaLekRepository.fetchBySifra(ApotekaID, l.getSifra());
    	if(al == null) {
    		return null;
    	}
    	return al;
	}
	public ApotekaLek findOneBySifra(String sifraLeka, Long apotekaID) {
		return apotekaLekRepository.fetchBySifra(apotekaID, sifraLeka);
	}
    public void removeBySifra(Long lekID,Long apotekaID) {
    	ApotekaLek al = apotekaLekRepository.findApotekaLekById(lekID,apotekaID);
    	System.out.println(al);
    	if(al == null) {
    		return;
    	}
    	apotekaLekRepository.deleteById(al.getId());
    }
    public void saveAL(ApotekaLek al) {
    	apotekaLekRepository.save(al);
    }
    
    
	public ApotekaLek findInApotekaLek(Long id,Long apotekaId) {
		
		
		return apotekaLekRepository.findApotekaLekById(id,apotekaId);
		
	}
	public Page<ApotekaLek> findLekoviByApotekaIDSearch(Long apotekaId, String title, Pageable paging){
		return apotekaLekRepository.findAllApotekaLekoviByApotedaIdPageSearch(apotekaId, title, paging);
	}
}
