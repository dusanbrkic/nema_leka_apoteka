package com.team_08.ISAproj.service;

import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.Dermatolog;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.repository.DermatologRepository;
import com.team_08.ISAproj.repository.PregledRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class PregledService {
    @Autowired
    private PregledRepository pregledRepository;
    @Autowired
    private DermatologRepository dermatologRepository;

    public List<Pregled> findAllByDermatolog(String cookie) throws CookieNotValidException {
        Dermatolog d = (Dermatolog) dermatologRepository.findOneByCookieTokenValue(cookie);
        if (d==null) throw new CookieNotValidException();
        List<Pregled> retVal = pregledRepository.fetchPregledWithPreporuceniLekovi(d.getId());
        return retVal;
    }
}
