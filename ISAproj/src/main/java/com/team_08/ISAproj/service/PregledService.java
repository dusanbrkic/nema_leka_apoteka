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
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PregledService {
    @Autowired
    private PregledRepository pregledRepository;
    @Autowired
    private DermatologRepository dermatologRepository;

    public Page<Pregled> findAllByDermatolog(String cookie, Integer page, Integer size) throws CookieNotValidException {
        Dermatolog d = (Dermatolog) dermatologRepository.findOneByCookieTokenValue(cookie);
        if (d==null) throw new CookieNotValidException();
        Page<Pregled> retVal = null;
        if(page==null)
            retVal = pregledRepository.fetchPregledWithPreporuceniLekovi(d.getId(), Pageable.unpaged());
        else
            retVal = pregledRepository.fetchPregledWithPreporuceniLekovi(d.getId(), PageRequest.of(page, size));
        return retVal;
    }
}
