package com.team_08.ISAproj.service;

import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.repository.PacijentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacijentService {
    @Autowired
    private PacijentRepository pacijentRepository;

    public Pacijent findOneById(Long id) {
        return pacijentRepository.findOneById(id);
    }

}
