package com.team_08.ISAproj.service;

import java.util.*;

import com.team_08.ISAproj.exceptions.LekNijeNaStanjuException;
import com.team_08.ISAproj.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.team_08.ISAproj.repository.ApotekaLekRepository;
import com.team_08.ISAproj.repository.LekRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Service
public class ApotekaLekService {
	@Autowired
	private ApotekaLekRepository apotekaLekRepository;
	@Autowired
	private LekRepository lekRepository;


	public ApotekaLek findOne(Long id) {
		return apotekaLekRepository.findOneById(id);
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
	
	public ApotekaLek findApotekaLekByLekID(Long lekID) {
		return apotekaLekRepository.findApotekaLekByLekID(lekID);
	}

	public Page<ApotekaLek> findAll(Pageable page) {
		return apotekaLekRepository.findAll(page);
	}
	public List<ApotekaLek> findAll() {
		return apotekaLekRepository.findAll();
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void updateKolicinaSlobodnihLekovaKonkurentno(ArrayList<Long> lekIDs, ArrayList<Integer> kolicine) throws LekNijeNaStanjuException, InterruptedException{
		Map<Long, Integer> kolicineLekova = new HashMap<>();

		int i = 0;
		for (Long id : lekIDs) {
			kolicineLekova.put(id, kolicine.get(i));
			i++;
		}
		
		List<ApotekaLek> apotekaLekovi = apotekaLekRepository.findAllApotekaLekByIDWithLock(kolicineLekova.keySet());
		
		for (ApotekaLek apotekaLek : apotekaLekovi) {
			if (kolicineLekova.get(apotekaLek.getLek().getId()) > apotekaLek.getKolicina())
				throw  new LekNijeNaStanjuException();
			apotekaLek.setKolicina(apotekaLek.getKolicina() - kolicineLekova.get(apotekaLek.getLek().getId()));
		}
		
		Thread.sleep(2000);
		
		apotekaLekRepository.saveAll(apotekaLekovi);
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


    public ApotekaLek findInApotekaLek(Long id, Long apotekaId) {
        return apotekaLekRepository.findApotekaLekById(id, apotekaId);

    }

    public Page<ApotekaLek> findLekoviByApotekaIDSearch(Long apotekaId, String title, Pageable paging) {
        return apotekaLekRepository.findAllApotekaLekoviByApotedaIdPageSearch(apotekaId, title, paging);
    }

	public Page<ApotekaLek> findAllApotekaLekoviSortedAndSearchedAndDone(Integer page, Integer size,
			String sortBy, Boolean sortDesc, String title,Long apotekaId) {
		title = "%" + title + "%";
		if(!(sortBy.equals("promotivnaCena") || sortBy.equals("kolicina") || sortBy.equals("cena") || sortBy.equals("istekVazenjaCene"))) {
			sortBy = "l." + sortBy;
		}
		Sort sort;
        if (sortDesc)
            sort = Sort.by(sortBy).descending();
        else
            sort = Sort.by(sortBy).ascending();
        return apotekaLekRepository.findAllApotekaLekoviSortedAndSearchedAndDone(PageRequest.of(page, size, sort), title, apotekaId);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public void updateKolicinaLekovaKonkurentno(Set<PregledLek> preporuceniLekovi, Long idApoteke, boolean wait) throws LekNijeNaStanjuException {
		System.out.println("----------------------------[THREAD " + Thread.currentThread().getId() + "] USAO SAM U TRANSAKCIJU I POKUSAVAM DA UZMEM LEK!------------------------------");
		Map<Long, Integer> kolicineLekova = new HashMap<>();

		for (PregledLek pregledLek : preporuceniLekovi) {
			kolicineLekova.put(pregledLek.getLek().getId(), pregledLek.getKolicina());
		}

		List<ApotekaLek> apotekaLekovi = apotekaLekRepository.findApotekaLekoviByIdWithLock(kolicineLekova.keySet(), idApoteke);
		System.out.println("----------------------------[THREAD " + Thread.currentThread().getId() + "] ZAKLJUCAO SAM OBJEKAT!------------------------------");

		if (wait)
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}

		for (ApotekaLek apotekaLek : apotekaLekovi) {
			if (kolicineLekova.get(apotekaLek.getLek().getId()) > apotekaLek.getKolicina())
				throw  new LekNijeNaStanjuException();
			apotekaLek.setKolicina(apotekaLek.getKolicina() - kolicineLekova.get(apotekaLek.getLek().getId()));
		}
		apotekaLekRepository.saveAll(apotekaLekovi);
		System.out.println("----------------------------[THREAD " + Thread.currentThread().getId() + "] SACUVAO SAM OBJEKAT!------------------------------");

		System.out.println("----------------------------[THREAD " + Thread.currentThread().getId() + "] ZAVRSIO SAM SA PREUZIMANJEM LEKA!------------------------------");


	}

    public Page<ApotekaLek> getAllLekovi(int page, int pageSize, String pretraga) {
    	pretraga = "%" + pretraga + "%";
    	return apotekaLekRepository.getAllLekovi(pretraga, PageRequest.of(page, pageSize));
    }
}
