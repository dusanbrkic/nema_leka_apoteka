package com.team_08.ISAproj.controller;

import com.team_08.ISAproj.dto.PregledDTO;
import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.service.PregledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/pregledi")
public class PregledController {
    @Autowired
    PregledService pregledService;

    @GetMapping(value = "/getPreglediByDermatolog", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<PregledDTO>> getPreglediByDermatolog(
            @RequestParam("cookie") String cookie,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        Page<Pregled> pregledi = null;
        try {
            pregledi = pregledService.findAllByDermatolog(cookie, page, size);
        } catch (CookieNotValidException e) {
            return new ResponseEntity<Page<PregledDTO>>(HttpStatus.NOT_FOUND);
        }
        if (pregledi==null)
            return new ResponseEntity<Page<PregledDTO>>(Page.empty(), HttpStatus.OK);

        Page<PregledDTO> preglediDTO = pregledi.map(new Function<Pregled, PregledDTO>() {
            @Override
            public PregledDTO apply(Pregled s) {
                return new PregledDTO(s);
            }
        });
        return new ResponseEntity<Page<PregledDTO>>(preglediDTO, HttpStatus.OK);
    }
}
