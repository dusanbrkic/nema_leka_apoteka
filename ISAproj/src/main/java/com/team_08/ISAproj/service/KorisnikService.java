package com.team_08.ISAproj.service;

import com.team_08.ISAproj.model.*;
import com.team_08.ISAproj.repository.AdminApotekeRepository;
import com.team_08.ISAproj.repository.DermatologRepository;
import com.team_08.ISAproj.repository.FarmaceutRepository;
import com.team_08.ISAproj.repository.PacijentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KorisnikService {

    @Autowired
    private PacijentRepository pacijentRepository;
    @Autowired
    private DermatologRepository dermatologRepository;
    @Autowired
    private FarmaceutRepository farmaceutRepository;
    @Autowired
    private AdminApotekeRepository adminApotekeRepository;

    public Korisnik findOne(Long id) {
        return null;
    }

    public Korisnik findUser(String username) {
       Korisnik k = pacijentRepository.findOneByUsername(username);
       if (k==null) k = dermatologRepository.findOneByUsername(username);
       if (k==null) k = farmaceutRepository.findOneByUsername(username);
       if (k==null) k = adminApotekeRepository.findOneByUsername(username);
       return k;
    }

    public Korisnik findUserByToken(String cookie) {
        Korisnik k = pacijentRepository.findOneByCookieTokenValue(cookie);
        if (k==null) k = dermatologRepository.findOneByCookieTokenValue(cookie);
        if (k==null) k = farmaceutRepository.findOneByCookieTokenValue(cookie);
        if (k==null) k = adminApotekeRepository.findOneByCookieTokenValue(cookie);
        return k;
    }

    public void saveUser(Korisnik k) {
        if (k instanceof Pacijent)
            pacijentRepository.save((Pacijent)k);
        else if (k instanceof Dermatolog)
            dermatologRepository.save((Dermatolog)k);
        else if (k instanceof Farmaceut)
            farmaceutRepository.save((Farmaceut) k);
        else if (k instanceof AdminApoteke)
            adminApotekeRepository.save((AdminApoteke)k);

    }

    public Korisnik fetchDermatologWithOdsustvo(String cookie) {
        return dermatologRepository.fetchDermatologWithOdsustvo(cookie);
    }
    public Korisnik fetchFarmaceutWithOdsustvo(String cookie) {
        return farmaceutRepository.fetchFarmaceutWithOdsustvo(cookie);
    }
}
