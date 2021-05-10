package com.team_08.ISAproj.service;

import com.team_08.ISAproj.dto.PregledDTO;
import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.repository.PregledRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PregledService {
    @Autowired
    private PregledRepository pregledRepository;

    public Page<Pregled> findAllByZdravstveniRadnikPagedAndSortedAndSearchedAndDone(
            String cookie, Integer page, Integer size, String sortBy, Boolean sortDesc,
            String pretragaIme, String pretragaPrezime) throws CookieNotValidException {
        pretragaIme = "%" + pretragaIme + "%";
        pretragaPrezime = "%" + pretragaPrezime + "%";
        if (!sortBy.equals("vreme"))
            sortBy = "pac." + sortBy;
        Sort sort;
        if (sortDesc)
            sort = Sort.by(sortBy).descending();
        else
            sort = Sort.by(sortBy).ascending();
        return pregledRepository.findAllByZdravstveniRadnikPagedAndSortedAndSearchedAndDone(
                cookie, PageRequest.of(page, size, sort), pretragaIme, pretragaPrezime);
    }

    public List<Pregled> fetchAllWithPreporuceniLekoviInDateRangeByZdravstveniRadnik(
            String cookie, LocalDateTime dateStart, LocalDateTime dateEnd) throws CookieNotValidException {
        return pregledRepository.fetchAllWithPreporuceniLekoviInDateRangeByZdravstveniRadnik(cookie, dateStart, dateEnd);
    }

    public List<Pregled> findAllInDateRangeByZdravstveniRadnik(LocalDateTime start, LocalDateTime end, String cookie) {
        return pregledRepository.findAllInDateRangeByZdravstveniRadnik(start, end, cookie);
    }

    public Pregled findOneById(Long id) {
        return pregledRepository.findOneById(id);
    }

    public void savePregled(Pregled pregled) {
        pregledRepository.save(pregled);
    }

    public void saveSlobodanTermin(Pregled pregled) {
        pregledRepository.save(pregled);
    }

    public List<Pregled> proveraOdDatumaPregleda(LocalDateTime start, String username) {
        return pregledRepository.findAllInFutureByZdravstveniRadnik(start, username);
    }
    
    public List<Pregled> findAllFromApoteka(Long apoteka_id){
    	return pregledRepository.findAllfromApoteka(apoteka_id);
    }
    public List<Pregled> findAllByPacijent(Pacijent p){
    	return pregledRepository.findAllByPacijent(p);
    }
    public List<Pregled> findAllTermsInDateRangeByDermatolog(String cookie, LocalDateTime start, LocalDateTime end)
            throws CookieNotValidException {
        return pregledRepository.findAllTermsInDateRangeByDermatolog(cookie, start, end);

    }
}
