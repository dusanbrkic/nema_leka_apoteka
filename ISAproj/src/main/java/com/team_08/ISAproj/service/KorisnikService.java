package com.team_08.ISAproj.service;

import com.team_08.ISAproj.model.Korisnik;
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
        return new Korisnik();
    }
}
