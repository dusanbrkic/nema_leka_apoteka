package com.team_08.ISAproj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.team_08.ISAproj.dto.LekDTO;
import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.Narudzbenica;
import com.team_08.ISAproj.model.NarudzbenicaLek;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.repository.NarudzbenicaLekRepository;
import com.team_08.ISAproj.repository.NarudzbenicaRepository;

@Service
public class NarudzbenicaService {

    @Autowired
    private NarudzbenicaRepository narudzbenicaRepository;
    @Autowired
    private NarudzbenicaLekRepository narudzbenicaLekRepository;

    public Narudzbenica saveNarudzbenica(Narudzbenica n) {
        return narudzbenicaRepository.save(n);
    }

    public void saveNarudzbenicaLek(NarudzbenicaLek nl) {
        narudzbenicaLekRepository.save(nl);
    }

    public List<Narudzbenica> findAllNarudzbenice() {

        return narudzbenicaRepository.findAll();
    }

    public List<NarudzbenicaLek> findAllNarudzbeniceLek() {

        return narudzbenicaLekRepository.findAll();
    }
    public List<NarudzbenicaLek> findNarudzbeniceLekNarudzbenica(Long id){
    	
    	return narudzbenicaLekRepository.findNarudzbeniceLekNarudzbenica(id);
    }
    public Narudzbenica findNarudzbenica(Long id) {

        return narudzbenicaRepository.findById(id).orElseGet(null);
    }

//    public Page<Narudzbenica> findAllNarudzbenicePagedAndSorted(
//            String cookie, Integer page, Integer size, String sortBy, Boolean sortDesc) throws CookieNotValidException {
//        if (!sortBy.equals("vreme"))
//            sortBy = "pac." + sortBy;
//        Sort sort;
//        if (sortDesc)
//            sort = Sort.by(sortBy).descending();
//        else
//            sort = Sort.by(sortBy).ascending();
//
//        return narudzbenicaRepository.findAllNarudzbenicePagedAndSorted(cookie, PageRequest.of(page, size, sort));
//    }
    public Page<Narudzbenica> findAllNarudzbeniceApotekaPagedAndSorted(Long apotekaId, Integer page, Integer size, String sortBy, Boolean sortDesc){
        Sort sort;
        if (sortDesc)
            sort = Sort.by(sortBy).descending();
        else
            sort = Sort.by(sortBy).ascending();
        return narudzbenicaRepository.findAllNarudzbeniceApotekaPagedAndSorted(apotekaId, PageRequest.of(page, size, sort));
    }
}
