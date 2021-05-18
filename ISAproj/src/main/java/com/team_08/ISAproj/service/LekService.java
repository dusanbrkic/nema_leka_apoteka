package com.team_08.ISAproj.service;

import com.team_08.ISAproj.dto.LekDTO;
import com.team_08.ISAproj.model.AdminApoteke;
import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Dermatolog;
import com.team_08.ISAproj.model.Farmaceut;
import com.team_08.ISAproj.model.Korisnik;
import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.repository.ApotekaLekRepository;
import com.team_08.ISAproj.repository.ApotekaRepository;
import com.team_08.ISAproj.repository.LekRepository;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LekService {

    @Autowired
    private LekRepository lekRepository;
    @Autowired
    private ApotekaLekRepository apotekaLekRepository;
    @Autowired
    private ApotekaRepository apotekaRepository;

    public Boolean saveLekApoteka(LekDTO lekDTO, String ApotekaID) {
        Lek l = lekRepository.findOneBySifra(lekDTO.getSifra());
        if (l != null) {
            return false;
        }
        l = new Lek(lekDTO);
        Apoteka a = apotekaRepository.findOneById(Long.parseLong(ApotekaID));
        ApotekaLek al = new ApotekaLek();
        al.setLek(l);
        al.setApoteka(a);
        al.setCena(lekDTO.getCena());
        al.setKolicina(lekDTO.getKolicina());
        al.setPromotivnaCena(0.0);
        al.setIstekVazenjaCene(null);

        lekRepository.save(l);
//    	apotekaRepository.save(a);
        apotekaLekRepository.save(al);
        return true;

    }

    public ApotekaLek addApotekaLek(String sifra, Long apotekaId) {
        Lek l = lekRepository.findOneBySifra(sifra);
        if (l == null) {
            return null;
        }
        Apoteka a = apotekaRepository.findOneById(apotekaId);
        ApotekaLek al = new ApotekaLek();
        al.setLek(l);
        al.setApoteka(a);
        al.setKolicina(0);
        al.setCena(0);
        al.setPromotivnaCena(0.0);
        al.setIstekVazenjaCene(null);
        apotekaLekRepository.save(al);
        return al;
    }

    public Lek findOneBySifra(String sifra) {

        return lekRepository.findOneBySifra(sifra);
    }

    public Page<Lek> findAll(Pageable paging) {
        return lekRepository.findAll(paging);
    }

    public Page<Lek> findAllApoteka(Pageable paging, Long ApotekaID, String lekPretraga) {

        return lekRepository.findAllLekoviByApotedaIdPage(ApotekaID, lekPretraga, paging);
    }

    public Page<Lek> findAllLekoviApoteka(Long ApotekaID, Pageable paging) {
        return lekRepository.findAllLekoviByApoteka(ApotekaID, paging);
    }

    public Page<Lek> findAllLekoviNotInApoteka(Long ApotekaID, Pageable paging) {

        return lekRepository.findAllLekoviNotInApoteka(ApotekaID, paging);
    }

    public Page<Lek> findAllSearch(Pageable page, String title) {


        return lekRepository.findByNazivContaining(title, page);

    }

    public Page<Lek> test(Pageable page, Long id) {
        return lekRepository.findAllNotInApoteka(id, page);
    }

    public Page<Lek> getAllByPacijentNotAllergic(int page, int pageSize, Long idPacijenta, String pretraga, List<String> vecPreporuceniSifre) {
    	pretraga = "%" + pretraga + "%";
    	return lekRepository.getAllByPacijentNotAllergic(idPacijenta, pretraga, PageRequest.of(page, pageSize), vecPreporuceniSifre);
    }

    public Page<Lek> getAllZamenskiLekovi(int page, int pageSize, Long idPacijenta, String pretraga, List<String> vecPreporuceniSifre, String nedostupanLekSifra, Long apotekaID, Integer kolicina) {
		pretraga = "%" + pretraga + "%";
    	return lekRepository.getAllZamenskiLekovi(idPacijenta, pretraga, PageRequest.of(page, pageSize), vecPreporuceniSifre, nedostupanLekSifra, apotekaID, kolicina);
    }
}
