package com.team_08.ISAproj.controller;

import com.team_08.ISAproj.dto.LekDTO;
import com.team_08.ISAproj.dto.PregledDTO;
import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.*;
import com.team_08.ISAproj.service.*;
import com.team_08.ISAproj.model.Pregled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pregledi")
public class PregledController {
    @Autowired
    PacijentService pacijentService;
    @Autowired
    private PregledService pregledService;
    @Autowired
    private LekService lekService;
    @Autowired
    ZdravstveniRadnikService zdravstveniRadnikService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private ApotekaService apotekaService;

    @GetMapping(value = "/getPregledaniKorisniciByZdravstveniRadnik", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<PregledDTO>> getPregledaniKorisniciByZdravstveniRadnik(
            @RequestParam("cookie") String cookie,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortDesc") Boolean sortDesc,
            @RequestParam("pretragaIme") String pretragaIme,
            @RequestParam("pretragaPrezime") String pretragaPrezime) {
        Page<Pregled> pregledi = null;
        try {
            pregledi = pregledService.findAllByZdravstveniRadnikPagedAndSortedAndSearchedAndDone(cookie, page, size, sortBy, sortDesc, pretragaIme, pretragaPrezime);
        } catch (CookieNotValidException e) {
            return new ResponseEntity<Page<PregledDTO>>(HttpStatus.NOT_FOUND);
        }
        if (pregledi == null)
            return new ResponseEntity<Page<PregledDTO>>(Page.empty(), HttpStatus.OK);

        Page<PregledDTO> preglediDTO = pregledi.map(new Function<Pregled, PregledDTO>() {
            @Override
            public PregledDTO apply(Pregled p) {
                PregledDTO pregledDTO = new PregledDTO(p);
                if (sortBy == null) pregledDTO.loadLekovi(p);
                return pregledDTO;
            }
        });
        return new ResponseEntity<Page<PregledDTO>>(preglediDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/getPreglediByZdravstveniRadnik", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PregledDTO>> getPreglediByZdravstveniRadnik(
            @RequestParam("cookie") String cookie,
            @RequestParam("start") String startDate,
            @RequestParam("end") String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        List<Pregled> pregledi = null;
        try {
            pregledi = pregledService.fetchAllWithPreporuceniLekoviInDateRangeByZdravstveniRadnik(cookie, start, end);
        } catch (CookieNotValidException e) {
            return new ResponseEntity<List<PregledDTO>>(HttpStatus.NOT_FOUND);
        }
        if (pregledi == null)
            return new ResponseEntity<List<PregledDTO>>(new ArrayList<>(), HttpStatus.OK);

        List<PregledDTO> preglediDTO = pregledi.stream().map(new Function<Pregled, PregledDTO>() {
            @Override
            public PregledDTO apply(Pregled p) {
                PregledDTO pregledDTO = new PregledDTO(p);
                pregledDTO.loadLekovi(p);
                return pregledDTO;
            }
        }).collect(Collectors.toList());
        return new ResponseEntity<List<PregledDTO>>(preglediDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/getTerminiInDateRangeByDermatolog", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PregledDTO>> getTerminiInDateRangeByDermatolog(
            @RequestParam("cookie") String cookie,
            @RequestParam("start") String startDate,
            @RequestParam("end") String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        List<Pregled> pregledi = null;
        try {
            pregledi = pregledService.findAllTermsInDateRangeByDermatolog(cookie, start, end);
        } catch (CookieNotValidException e) {
            return new ResponseEntity<List<PregledDTO>>(HttpStatus.NOT_FOUND);
        }
        if (pregledi == null)
            return new ResponseEntity<List<PregledDTO>>(new ArrayList<>(), HttpStatus.OK);

        List<PregledDTO> preglediDTO = pregledi.stream().map(new Function<Pregled, PregledDTO>() {
            @Override
            public PregledDTO apply(Pregled p) {
                PregledDTO pregledDTO = new PregledDTO(p);
                return pregledDTO;
            }
        }).collect(Collectors.toList());
        return new ResponseEntity<List<PregledDTO>>(preglediDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/updatePregled", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePregled(@RequestBody PregledDTO pregledDTO) {
        Pregled pregled = pregledService.findOneById(pregledDTO.getId());
        if (pregled == null)
            return new ResponseEntity<String>(HttpStatus.OK);
        List<Lek> lekovi = pregledDTO.getPreporuceniLekovi().stream().map(new Function<LekDTO, Lek>() {
            @Override
            public Lek apply(LekDTO lekDTO) {
                Lek l = lekService.findOneBySifra(lekDTO.getSifra());
                return l;
            }
        }).collect(Collectors.toList());
        pregled.setPreporuceniLekovi(new HashSet<>(lekovi));
        pregled.setPregledObavljen(true);
        pregled.setDijagnoza(pregledDTO.getDijagnoza());
        pregledService.savePregled(pregled);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PostMapping(value = "/createZakazanPregled")
    public ResponseEntity<String> createZakazanPregled(@RequestParam("start") String startDate,
                                                       @RequestParam("end") String endDate,
                                                       @RequestParam("cookie") String cookie,
                                                       @RequestParam("idPacijenta") Long idPacijenta,
                                                       @RequestParam("idApoteke") Long idApoteke) {
        LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        if(!pregledService.findAllInDateRangeByZdravstveniRadnik(start, end, cookie).isEmpty())
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        ZdravstveniRadnik zdravstveniRadnik = zdravstveniRadnikService.findOneByCookie(cookie);
        if(zdravstveniRadnik==null)
            return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

        //nova klasa
        Apoteka a = apotekaService.findOne(idApoteke);
        Pregled p = new Pregled();
        p.setVreme(start);
        p.setKraj(end);
        p.setPregledZakazan(true);
        p.setPregledObavljen(false);
        p.setZdravstveniRadnik(zdravstveniRadnik);
        p.setApoteka(a);
        p.setPacijent(pacijentService.findOneById(idPacijenta));
        if (zdravstveniRadnik instanceof Dermatolog)
            p.setCena(a.getCenaPregleda());
        else if (zdravstveniRadnik instanceof Farmaceut)
            p.setCena(a.getCenaSavetovanja());
        pregledService.savePregled(p);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PostMapping(value = "/updateZakazanPregled")
    public ResponseEntity<String> updateZakazanPregled(@RequestParam("cookie") String cookie,
                                                       @RequestParam("idPacijenta") Long idPacijenta,
                                                       @RequestParam("idTermina") Long idTermina) {
        ZdravstveniRadnik zdravstveniRadnik = zdravstveniRadnikService.findOneByCookie(cookie);
        if(zdravstveniRadnik==null)
            return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

        //nova klasa
        Pregled p = pregledService.findOneById(idTermina);
        p.setPacijent(pacijentService.findOneById(idPacijenta));
        p.setCena(p.getApoteka().getCenaPregleda());
        p.setPregledZakazan(true);
        pregledService.savePregled(p);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PostMapping(value = "addSlobodanTermin")
    public ResponseEntity<PregledDTO> addSlobodanTermin(@RequestBody PregledDTO pregledDTO){

    	String apotekaId = pregledDTO.getApotekaId();
    	Apoteka a = apotekaService.findOne(Long.parseLong(apotekaId));
    	Dermatolog d = zdravstveniRadnikService.findOneByUsername(pregledDTO.getUsername());
    	Pregled p = new Pregled(pregledDTO);
    	p.setApoteka(a);
    	p.setDermatolog(d);


    	pregledService.saveSlobodanTermin(p);

    	//Korisnik k = korisnikService.findUserByToken(cookie);
//    	if(k == null) {
//    		return new ResponseEntity<PregledDTO>(HttpStatus.NOT_FOUND);
//    	}
//    	if(k instanceof AdminApoteke) {
//    		AdminApoteke aa = (AdminApoteke) k;
//    		Long ApotekaId = zdravstveniRadnikService.
//        	Page<DermatologApoteka> dermatoloziApoteke = null;
//
//        	dermatoloziApoteke = zdravstveniRadnikService.fetchDermatologsByApotekaId(Long.parseLong(aa.getApoteka().getId()), page);
//    	}


    	return new ResponseEntity<PregledDTO>(pregledDTO, HttpStatus.OK);
	}


}
