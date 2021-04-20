package com.team_08.ISAproj.controller;

import com.team_08.ISAproj.dto.SavetovanjeDTO;
import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.Savetovanje;
import com.team_08.ISAproj.service.SavetovanjeService;
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
@RequestMapping("/savetovanja")
public class SavetovanjeController {
    @Autowired
    SavetovanjeService savetovanjeService;

    @GetMapping(value = "/getSavetovanjaByFarmaceut", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<SavetovanjeDTO>> getSavetovanjaByFarmaceut(
            @RequestParam("cookie") String cookie,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        Page<Savetovanje> savetovanja = null;
        try {
            savetovanja = savetovanjeService.findAllByFarmaceut(cookie, page, size);
        } catch (CookieNotValidException e) {
            return new ResponseEntity<Page<SavetovanjeDTO>>(HttpStatus.NOT_FOUND);
        }
        if (savetovanja==null)
            return new ResponseEntity<Page<SavetovanjeDTO>>(Page.empty(), HttpStatus.OK);

        Page<SavetovanjeDTO> savetovanjaDTO = savetovanja.map(new Function<Savetovanje, SavetovanjeDTO>() {
            @Override
            public SavetovanjeDTO apply(Savetovanje s) {
                return new SavetovanjeDTO(s);
            }
        });
        return new ResponseEntity<Page<SavetovanjeDTO>>(savetovanjaDTO, HttpStatus.OK);
    }
}