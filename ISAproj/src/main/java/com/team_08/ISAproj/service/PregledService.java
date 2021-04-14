package com.team_08.ISAproj.service;

import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.Dermatolog;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.repository.DermatologRepository;
import com.team_08.ISAproj.repository.PregledRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PregledService {
    @Autowired
    private PregledRepository pregledRepository;
    @Autowired
    private DermatologRepository dermatologRepository;

    public List<Pregled> findAllByDermatolog(String cookie) throws CookieNotValidException {
        Dermatolog d = (Dermatolog) dermatologRepository.findOneByCookieTokenValue(cookie);
        if (d==null) throw new CookieNotValidException();
        List<Pregled> retVal = pregledRepository.findAllByDermatolog_id(d.getId());
        return retVal;
    }
}
