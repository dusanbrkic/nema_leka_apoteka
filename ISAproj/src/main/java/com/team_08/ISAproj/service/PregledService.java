package com.team_08.ISAproj.service;

import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.Dermatolog;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.repository.DermatologRepository;
import com.team_08.ISAproj.repository.PregledRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PregledService {
    @Autowired
    private PregledRepository pregledRepository;
    @Autowired
    private DermatologRepository dermatologRepository;

    public Page<Pregled> findAllByDermatolog(String cookie, Integer page, Integer size, String sortBy, Boolean sortDesc, Boolean obavljen, String pretragaIme, String pretragaPrezime)
            throws CookieNotValidException {
        Dermatolog d = (Dermatolog) dermatologRepository.findOneByCookieTokenValue(cookie);
        if (d==null) throw new CookieNotValidException();
        Page<Pregled> retVal = null;
        if(page==null && obavljen==null)
            retVal = pregledRepository.fetchAllWithPreporuceniLekovi(d.getId(), Pageable.unpaged());
        else if (obavljen!=null && page!=null) {
            if (pretragaIme==null) pretragaIme = "";
            if (pretragaPrezime==null) pretragaPrezime = "";
            pretragaIme = "%" + pretragaIme + "%";
            pretragaPrezime = "%" + pretragaPrezime + "%";
            if (!sortBy.equals("vreme")) sortBy = "pac." + sortBy;
            if (sortDesc) {
                retVal = pregledRepository.getAllObavljeni(d.getId(), PageRequest.of(page, size, Sort.by(sortBy).descending()), pretragaIme, pretragaPrezime);
            }
            else {
                retVal = pregledRepository.getAllObavljeni(d.getId(), PageRequest.of(page, size, Sort.by(sortBy).ascending()), pretragaIme, pretragaPrezime);
            }
        }
        return retVal;
    }

    public List<Pregled> findAllInDateRange(LocalDateTime start, LocalDateTime end, String cookie) {
        return pregledRepository.findAllInDateRange(start, end, cookie);
    }
}
