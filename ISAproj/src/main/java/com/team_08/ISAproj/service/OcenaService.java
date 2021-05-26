package com.team_08.ISAproj.service;

import com.team_08.ISAproj.dto.PregledDTO;
import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.AdminApoteke;
import com.team_08.ISAproj.model.Dermatolog;
import com.team_08.ISAproj.model.Farmaceut;
import com.team_08.ISAproj.model.Korisnik;
import com.team_08.ISAproj.model.Ocena;
import com.team_08.ISAproj.model.OcenaApoteka;
import com.team_08.ISAproj.model.OcenaLek;
import com.team_08.ISAproj.model.OcenaZdravstveniRadnik;
import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.repository.OcenaRepository;
import com.team_08.ISAproj.repository.PregledRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class OcenaService {
    @Autowired
    private PregledRepository pregledRepository;
    @Autowired
    private OcenaRepository ocenaRepository;
    
    public Double findProsecnaOcenaLekaByID(Long id) {
    	return ocenaRepository.findProsecnaOcenaLekaByID(id);
    }
    
    public List<Ocena> findOceneLekaByID(Long id) {
    	return ocenaRepository.findOceneLekaByID(id);
    }
    
    public OcenaLek findOcenaLekaByPacijentID(Long idLeka, Long idPacijenta) {
    	return ocenaRepository.findOcenaLekaByPacijentID(idLeka, idPacijenta);
    }
    
    public OcenaApoteka findOcenaApotekeByPacijentID(Long idApoteke, Long idPacijenta) {
    	return ocenaRepository.findOcenaApotekeByPacijentID(idApoteke, idPacijenta);
    }
    
    public Double findProsecnaOcenaApotekeByID(Long id) {
    	return ocenaRepository.findProsecnaOcenaApotekaByID(id);
    }
    
    public List<Ocena> findOceneApotekeByID(Long id) {
    	return ocenaRepository.findOceneApotekaByID(id);
    }
    
    
    public void saveOcena(Ocena o) {
        if (o instanceof OcenaLek)
        	ocenaRepository.save((OcenaLek) o);
        else if (o instanceof OcenaApoteka)
        	ocenaRepository.save((OcenaApoteka) o);
        else if (o instanceof OcenaZdravstveniRadnik)
        	ocenaRepository.save((OcenaZdravstveniRadnik) o);

    }
}
