package com.team_08.ISAproj.controller;

import com.team_08.ISAproj.dto.LekDTO;
import com.team_08.ISAproj.dto.PregledDTO;
import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.service.LekService;
import com.team_08.ISAproj.service.PregledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private PregledService pregledService;
    @Autowired
    private LekService lekService;

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
}
