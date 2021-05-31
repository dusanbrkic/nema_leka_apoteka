package com.team_08.ISAproj.service;

import com.team_08.ISAproj.model.*;
import com.team_08.ISAproj.repository.DermatologApotekaRepository;
import com.team_08.ISAproj.repository.DermatologRepository;
import com.team_08.ISAproj.repository.FarmaceutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Service
public class ZdravstveniRadnikService {

    @Autowired
    private DermatologRepository dermatologRepository;
    @Autowired
    private FarmaceutRepository farmaceutRepository;
    @Autowired
    private DermatologApotekaRepository dermatologApotekaRepository;

    public void saveDermatolog(Dermatolog d) {
    	dermatologRepository.save(d);
    }
    
    public List<ZdravstveniRadnik> findAll() {
    	List<ZdravstveniRadnik> l = dermatologRepository.findAllZdravstveniRadnici();
    	l.addAll(farmaceutRepository.findAllZdravstveniRadnici());
        return l;
    }
    
    
    public ZdravstveniRadnik fetchZdravstveniRadnikWithOdsustva(String cookie) {
        ZdravstveniRadnik z = dermatologRepository.fetchDermatologWithOdsustva(cookie);
        if (z == null) {
            z = farmaceutRepository.fetchFarmaceutWithOdsustva(cookie);
        }
        return z;
    }

    public ZdravstveniRadnik fetchZdravstveniRadnikWithOdsustvaInDateRange(String cookie, LocalDateTime start, LocalDateTime end) {
        ZdravstveniRadnik z = dermatologRepository.fetchDermatologWithOdsustvaInDateRange(cookie, start, end);
        if (z == null) {
            z = farmaceutRepository.fetchFarmaceutWithOdsustvaInDateRange(cookie, start, end);
        }
        return z;
    }
    
    
    //svi dermatolozi u apoteci page
    public Page<DermatologApoteka> fetchDermatologsByApotekaId(Long ApotekaId, Pageable page) {


        return dermatologRepository.fetchDermatologApotekaByApotekaIdPage(ApotekaId, page);
    }

    public Dermatolog findOneByUsername(String username) {

        return dermatologRepository.findOneByUsername(username);
    }
    
    public ZdravstveniRadnik findZdravstveniRadnikByUsername(String username) {
    	ZdravstveniRadnik zr = dermatologRepository.findOneByUsername(username);
    	if(zr == null) {
    		zr = farmaceutRepository.findOneByUsername(username);
    	}
        return zr;
    }

    public ZdravstveniRadnik findOneByCookie(String cookie) {
        ZdravstveniRadnik z = dermatologRepository.findOneByCookieTokenValue(cookie);
        if (z == null) {
            z = farmaceutRepository.findOneByCookieTokenValue(cookie);
        }
        return z;
    }

    //svi dermatolozi u apoteci list
    public List<DermatologApoteka> fetchDermatologsByApotekaId(Long ApotekaId) {


        return dermatologRepository.fetchDermatologApotekaByApotekaId(ApotekaId);
    }

    public List<Farmaceut> fetchFarmaceutsByApotekaId(Long ApotekaId) {


        return farmaceutRepository.findAllByApotekaId(ApotekaId);
    }
    
    public Farmaceut findOneById(Long id) {
        return farmaceutRepository.findOneById(id);
    }

    //dodavanje farmaceuta
    public void saveFarmaceut(Farmaceut farmaceut) {
        farmaceutRepository.save(farmaceut);
    }

    //svi dermatolozi koji ne rade u apoteci PAGE
    public Page<Dermatolog> fetchDermatologsNotInApotekaPage(Long ApotekaId, Pageable page) {
        return dermatologRepository.fetchNotWorkingInApotekaPage(ApotekaId, page);
    }

    //svi dermatolozi koji ne rade u apoteci LIST
    public List<Dermatolog> fetchDermatologsNotInApotekaList(Long ApotekaId) {
        return dermatologRepository.fetchNotWorkingInApotekaList(ApotekaId);
    }

    //za dermatologa sva njegova radna mesta
    public List<DermatologApoteka> fetchAllWorkingTimesAndPricesForDermatolog(Long dermatologId) {
        return dermatologRepository.fetchAllWorkingTimesAndPricesForDermatolog(dermatologId);
    }

    //provera radnog vremena dermatolog
    public List<DermatologApoteka> checkIfGivenWorkHoursAreOk(String username, LocalDateTime start, LocalDateTime end) {

        return dermatologRepository.findIfDermatologTimesOverlap(username, start, end);
    }

    public void addDermatologApoteke(DermatologApoteka da) {
        dermatologApotekaRepository.save(da);

    }

    public DermatologApoteka findDermatologApoteka(Long dermatologId, Long apotekaId) {
        return dermatologApotekaRepository.findOneDermatologApotekaByIds(dermatologId, apotekaId);
    }

    public void deleteDermatologApoteke(Long dermatologId, Long apotekaId) {
        dermatologApotekaRepository.deleteById(dermatologApotekaRepository.findOneDermatologApotekaByIds(dermatologId, apotekaId).getId());
    }

    //provera za termine rada osim za izabranu apoteku
    public List<DermatologApoteka> checkIfGivenWorkHoursAreOk(String username, Long apotekaId, LocalDateTime start, LocalDateTime end) {
        return dermatologRepository.findIfDermatologTimesOverlapNotInApoteka(username, apotekaId, start, end);
    }

    public void deleteFarmaceuta(Long id) {
        farmaceutRepository.deleteById(id);

    }
    //svi dermatolozi u apoteci page sort search filter
    public Page<DermatologApoteka> findOneDermatologApotekaByIdSearchedSorted(Integer page, Integer size,
			String sortBy, Boolean sortDesc,
    		Long apotekaId,
    		String pretragaIme,
    		String pretragaPrezime, Double ocena,String pocetak,String kraj){
    	LocalTime start = LocalTime.parse(pocetak, DateTimeFormatter.ofPattern("HH:mm:ss.SSS'Z'"));
    	LocalTime end = LocalTime.parse(kraj, DateTimeFormatter.ofPattern("HH:mm:ss.SSS'Z'"));
    	pretragaIme = "%" + pretragaIme + "%";
    	pretragaPrezime = "%" + pretragaPrezime + "%";
    	System.out.println(pretragaIme +" "+ pretragaPrezime);
    	if(!(sortBy.equals("radnoVremePocetak") || sortBy.equals("radnoVremeKraj"))) {
    		sortBy = "d." + sortBy;
    	}
    	Sort sort;
        if (sortDesc)
            sort = Sort.by(sortBy).descending();
        else
            sort = Sort.by(sortBy).ascending();
		return dermatologApotekaRepository.findOneDermatologApotekaByIdSearchedSorted(PageRequest.of(page, size, sort), apotekaId, pretragaIme, pretragaPrezime,ocena,start,end);
        //return dermatologApotekaRepository.findOneDermatologApotekaByIdSearchedSorted(PageRequest.of(page, size, sort));
    }
    //svi farmaceuti iz apoteke
    public Page<Farmaceut> findFarmaceutApotekaByIdSearchedSorted(Integer page, Integer size,
			String sortBy, Boolean sortDesc,
    		Long apotekaId,
    		String pretragaIme,
    		String pretragaPrezime, Double ocena,String pocetak,String kraj
    		){
    	LocalTime start = LocalTime.parse(pocetak, DateTimeFormatter.ofPattern("HH:mm:ss.SSS'Z'"));
    	LocalTime end = LocalTime.parse(kraj, DateTimeFormatter.ofPattern("HH:mm:ss.SSS'Z'"));
    	pretragaIme = "%" + pretragaIme + "%";
    	pretragaPrezime = "%" + pretragaPrezime + "%";
    	System.out.println(pretragaIme +" "+ pretragaPrezime);
    	Sort sort;
        if (sortDesc)
            sort = Sort.by(sortBy).descending();
        else
            sort = Sort.by(sortBy).ascending();
		return farmaceutRepository.findFarmaceutApotekaByIdSearchedSorted(PageRequest.of(page, size, sort), apotekaId, pretragaIme, pretragaPrezime,ocena,start,end);
    }

    public ZdravstveniRadnik checkRadnoVreme(LocalTime start, LocalTime end, String cookie, Long idApoteke) {
        if (dermatologRepository.findOneByCookieTokenValue(cookie)!=null)
            return dermatologRepository.checkRadnoVreme(start, end, cookie, idApoteke);
        else
            return farmaceutRepository.checkRadnoVreme(start, end, cookie);
          }
    //svi farmaceuti
    public Page<Farmaceut> findFarmaceutSearchedSorted(Integer page, Integer size,
			String sortBy, Boolean sortDesc,
			String apotekaIme,
    		String pretragaIme,
    		String pretragaPrezime, Double ocena,String pocetak,String kraj
    		){
    	LocalTime start = LocalTime.parse(pocetak, DateTimeFormatter.ofPattern("HH:mm:ss.SSS'Z'"));
    	LocalTime end = LocalTime.parse(kraj, DateTimeFormatter.ofPattern("HH:mm:ss.SSS'Z'"));
    	pretragaIme = "%" + pretragaIme + "%";
    	pretragaPrezime = "%" + pretragaPrezime + "%";
    	System.out.println(pretragaIme +" "+ pretragaPrezime);
    	Sort sort;
        if (sortDesc)
            sort = Sort.by(sortBy).descending();
        else
            sort = Sort.by(sortBy).ascending();
        if(apotekaIme.equals("")) {
        	return farmaceutRepository.findFarmaceutSearchedSorted(PageRequest.of(page, size, sort),pretragaIme, pretragaPrezime,ocena,start,end);
        }
		return farmaceutRepository.findFarmaceutSearchedSortedNaziv(PageRequest.of(page, size, sort),apotekaIme,pretragaIme, pretragaPrezime,ocena,start,end);
    }
    //svi derma po nazivu apoteke
    public Page<DermatologApoteka> findAllDermatologApotekaByApotekaNazivSearched(Integer page, Integer size,
			String sortBy, Boolean sortDesc,
    		String naziv,
    		String pretragaIme,
    		String pretragaPrezime, Double ocena,String pocetak,String kraj){
    	LocalTime start = LocalTime.parse(pocetak, DateTimeFormatter.ofPattern("HH:mm:ss.SSS'Z'"));
    	LocalTime end = LocalTime.parse(kraj, DateTimeFormatter.ofPattern("HH:mm:ss.SSS'Z'"));
    	pretragaIme = "%" + pretragaIme + "%";
    	pretragaPrezime = "%" + pretragaPrezime + "%";
    	System.out.println(pretragaIme +" "+ pretragaPrezime);
    	if(!(sortBy.equals("radnoVremePocetak") || sortBy.equals("radnoVremeKraj"))) {
    		sortBy = "d." + sortBy;
    	}
    	Sort sort;
        if (sortDesc)
            sort = Sort.by(sortBy).descending();
        else
            sort = Sort.by(sortBy).ascending();
        if(naziv.equals("")) {
        	return dermatologApotekaRepository.findAllDermatologApotekaSearched(PageRequest.of(page, size, sort), pretragaIme, pretragaPrezime,ocena,start,end);
        }
        return dermatologApotekaRepository.findAllDermatologApotekaByApotekaNazivSearched(PageRequest.of(page, size, sort), naziv, pretragaIme, pretragaPrezime,ocena,start,end);
    }
}
