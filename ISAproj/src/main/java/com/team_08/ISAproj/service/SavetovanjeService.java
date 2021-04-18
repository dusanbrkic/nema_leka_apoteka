package com.team_08.ISAproj.service;

import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.Farmaceut;
import com.team_08.ISAproj.model.Savetovanje;
import com.team_08.ISAproj.repository.FarmaceutRepository;
import com.team_08.ISAproj.repository.SavetovanjeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SavetovanjeService {
    @Autowired
    private SavetovanjeRepository savetovanjeRepository;
    @Autowired
    private FarmaceutRepository farmaceutRepository;

    public List<Savetovanje> findAllByFarmaceut(String cookie) throws CookieNotValidException {
        Farmaceut d = (Farmaceut) farmaceutRepository.findOneByCookieTokenValue(cookie);
        if (d==null) throw new CookieNotValidException();
        List<Savetovanje> retVal = savetovanjeRepository.fetchSavetovanjeWithPreporuceniLekovi(d.getId());
        return retVal;
    }
}
