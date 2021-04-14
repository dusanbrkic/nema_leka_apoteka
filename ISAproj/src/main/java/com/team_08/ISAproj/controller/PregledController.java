package com.team_08.ISAproj.controller;

import com.team_08.ISAproj.dto.PregledDTO;
import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.service.PregledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pregledi")
public class PregledController {
    @Autowired
    PregledService pregledService;

    @GetMapping(value = "/getPreglediByDermatolog", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PregledDTO>> getPreglediByDermatolog(@RequestParam("cookie") String cookie) {
        List<PregledDTO> preglediDTO = new ArrayList<>();
        List<Pregled> pregledi = null;
        try {
            pregledi = pregledService.findAllByDermatolog(cookie);
        } catch (CookieNotValidException e) {
            return new ResponseEntity<List<PregledDTO>>(HttpStatus.NOT_FOUND);
        }
        if (pregledi==null)
            return new ResponseEntity<List<PregledDTO>>(new ArrayList<PregledDTO>(), HttpStatus.OK);

        for (Pregled p : pregledi)
            preglediDTO.add(new PregledDTO(p));
        return new ResponseEntity<List<PregledDTO>>(preglediDTO, HttpStatus.OK);
    }
}
