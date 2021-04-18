package com.team_08.ISAproj.controller;

import com.team_08.ISAproj.dto.SavetovanjeDTO;
import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.Savetovanje;
import com.team_08.ISAproj.service.SavetovanjeService;
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
@RequestMapping("/savetovanja")
public class SavetovanjeController {
    @Autowired
    SavetovanjeService savetovanjeService;

    @GetMapping(value = "/getSavetovanjaByFarmaceut", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SavetovanjeDTO>> getSavetovanjaByFarmaceut(@RequestParam("cookie") String cookie) {
        List<SavetovanjeDTO> savetovanjaDTO = new ArrayList<>();
        List<Savetovanje> savetovanja = null;
        try {
            savetovanja = savetovanjeService.findAllByFarmaceut(cookie);
        } catch (CookieNotValidException e) {
            return new ResponseEntity<List<SavetovanjeDTO>>(HttpStatus.NOT_FOUND);
        }
        if (savetovanja==null)
            return new ResponseEntity<List<SavetovanjeDTO>>(new ArrayList<SavetovanjeDTO>(), HttpStatus.OK);

        for (Savetovanje s : savetovanja)
            savetovanjaDTO.add(new SavetovanjeDTO(s));
        return new ResponseEntity<List<SavetovanjeDTO>>(savetovanjaDTO, HttpStatus.OK);
    }
}