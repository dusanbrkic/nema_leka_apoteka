package com.team_08.ISAproj.service;

import com.team_08.ISAproj.dto.KorisnikDTO;
import com.team_08.ISAproj.exceptions.KorisnikPostojiException;
import com.team_08.ISAproj.model.*;
import com.team_08.ISAproj.repository.AdminApotekeRepository;
import com.team_08.ISAproj.repository.DermatologRepository;
import com.team_08.ISAproj.repository.FarmaceutRepository;
import com.team_08.ISAproj.repository.PacijentRepository;

import javax.persistence.OptimisticLockException;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(readOnly = true)
    public Korisnik findUser(String username) {
        Korisnik k = pacijentRepository.findOneByUsername(username);
        if (k == null) k = dermatologRepository.findOneByUsername(username);
        if (k == null) k = farmaceutRepository.findOneByUsername(username);
        if (k == null) k = adminApotekeRepository.findOneByUsername(username);
        return k;
    }
	
    public Korisnik findUserWithLock(String username) {
        return pacijentRepository.findOneByUsernameWithLock(username);
    }
	
    public Korisnik findUserByToken(String cookie) {
        Korisnik k = pacijentRepository.findOneByCookieTokenValue(cookie);
        if (k == null) k = dermatologRepository.findOneByCookieTokenValue(cookie);
        if (k == null) k = farmaceutRepository.findOneByCookieTokenValue(cookie);
        if (k == null) k = adminApotekeRepository.findOneByCookieTokenValue(cookie);
        return k;
    }
    
    public Korisnik findUserByTokenWithLock(String cookie) {
    	return pacijentRepository.findOneByCookieTokenValueWithLock(cookie);
    }

	@Transactional(readOnly = true)
    public Korisnik findUserByEmail(String email) {
        Korisnik k = pacijentRepository.findOneByEmailAdresa(email);
        if (k == null) k = dermatologRepository.findOneByEmailAdresa(email);
        if (k == null) k = farmaceutRepository.findOneByEmailAdresa(email);
        if (k == null) k = adminApotekeRepository.findOneByEmailAdresa(email);
        return k;

    }
    public Korisnik findUserByEmailWithLock(String email) {
        Korisnik k = pacijentRepository.findOneByEmailAdresaWithLock(email);
        if (k == null) k = dermatologRepository.findOneByEmailAdresaWithLock(email);
        if (k == null) k = farmaceutRepository.findOneByEmailAdresaWithLock(email);
        if (k == null) k = adminApotekeRepository.findOneByEmailAdresaWithLock(email);
        return k;

    }

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Pacijent savePacijentKonkurentno(KorisnikDTO korisnik, String verificationCode) throws KorisnikPostojiException, InterruptedException {
    	
    	Korisnik k = findUserByEmailWithLock(korisnik.getEmailAdresa());
    	Korisnik k2 = findUserWithLock(korisnik.getUsername());
    	
    	if(k != null || k2 != null) {
    		throw new KorisnikPostojiException();
    	}
    	
    	Pacijent pacijent = new Pacijent();
        pacijent.UpdatePacijent(korisnik);
        pacijent.setCookieTokenValue(verificationCode);
    	
    	Thread.sleep(2000);
    	try {
    		pacijentRepository.save(pacijent);
    	} catch (Exception e) {
			throw new KorisnikPostojiException();
		}
        
        return pacijent;
    }
    
    public void saveUser(Korisnik k) {
        if (k instanceof Pacijent)
            pacijentRepository.save((Pacijent) k);
        else if (k instanceof Dermatolog)
            dermatologRepository.save((Dermatolog) k);
        else if (k instanceof Farmaceut)
            farmaceutRepository.save((Farmaceut) k);
        else if (k instanceof AdminApoteke)
            adminApotekeRepository.save((AdminApoteke) k);

    }
}
