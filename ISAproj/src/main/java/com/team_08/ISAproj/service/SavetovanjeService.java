package com.team_08.ISAproj.service;

import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.Farmaceut;
import com.team_08.ISAproj.model.Savetovanje;
import com.team_08.ISAproj.repository.FarmaceutRepository;
import com.team_08.ISAproj.repository.SavetovanjeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class SavetovanjeService {
    @Autowired
    private SavetovanjeRepository savetovanjeRepository;
    @Autowired
    private FarmaceutRepository farmaceutRepository;

    public Page<Savetovanje> findAllByFarmaceut(String cookie, Integer page, Integer size, String sortBy, Boolean sortDesc, Boolean obavljena,String pretragaIme, String pretragaPrezime)
            throws CookieNotValidException {
        Farmaceut f = (Farmaceut) farmaceutRepository.findOneByCookieTokenValue(cookie);
        if (f==null) throw new CookieNotValidException();
        Page<Savetovanje> retVal = null;
        if(page==null && obavljena==null)
            retVal = savetovanjeRepository.fetchAllWithPreporuceniLekovi(f.getId(), Pageable.unpaged());
        else if (obavljena!=null && page!=null) {
            if (pretragaIme==null) pretragaIme = "";
            if (pretragaPrezime==null) pretragaPrezime = "";
            pretragaIme = "%" + pretragaIme + "%";
            pretragaPrezime = "%" + pretragaPrezime + "%";
            if (!sortBy.equals("vreme")) sortBy = "p." + sortBy;
            if (sortDesc) {
                retVal = savetovanjeRepository.getObavljenaSavetovanja(f.getId(), PageRequest.of(page, size, Sort.by(sortBy).descending()), pretragaIme, pretragaPrezime);
            }
            else {
                retVal = savetovanjeRepository.getObavljenaSavetovanja(f.getId(), PageRequest.of(page, size, Sort.by(sortBy).ascending()), pretragaIme, pretragaPrezime);
            }
        }
        return retVal;
    }

    public List<Savetovanje> findAllInDateRange(LocalDateTime start, LocalDateTime end, String cookie) {
        return savetovanjeRepository.findAllInDateRange(start, end, cookie);
    }
}
