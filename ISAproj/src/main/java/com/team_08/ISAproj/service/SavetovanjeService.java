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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SavetovanjeService {
    @Autowired
    private SavetovanjeRepository savetovanjeRepository;
    @Autowired
    private FarmaceutRepository farmaceutRepository;

    public Page<Savetovanje> findAllByFarmaceut(String cookie, Integer page, Integer size) throws CookieNotValidException {
        Farmaceut f = (Farmaceut) farmaceutRepository.findOneByCookieTokenValue(cookie);
        if (f==null) throw new CookieNotValidException();
        Page<Savetovanje> retVal = null;
        if(page==null)
            retVal = savetovanjeRepository.fetchSavetovanjeWithPreporuceniLekovi(f.getId(), Pageable.unpaged());
        else
            retVal = savetovanjeRepository.fetchSavetovanjeWithPreporuceniLekovi(f.getId(), PageRequest.of(page, size));
        return retVal;
    }
}
