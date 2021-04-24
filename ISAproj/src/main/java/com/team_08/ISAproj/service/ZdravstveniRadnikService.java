package com.team_08.ISAproj.service;

import com.team_08.ISAproj.model.*;
import com.team_08.ISAproj.repository.DermatologRepository;
import com.team_08.ISAproj.repository.FarmaceutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZdravstveniRadnikService {

    @Autowired
    private DermatologRepository dermatologRepository;
    @Autowired
    private FarmaceutRepository farmaceutRepository;

    public ZdravstveniRadnik fetchZdravstveniRadnikWithOdsustva(String cookie) {
        ZdravstveniRadnik z = dermatologRepository.fetchDermatologWithOdsustva(cookie);
        if (z==null) {
            z = farmaceutRepository.fetchFarmaceutWithOdsustva(cookie);
        }
        return z;
    }
}
