package com.team_08.ISAproj.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team_08.ISAproj.dto.FarmaceutDTO;
import com.team_08.ISAproj.dto.LekDTO;
import com.team_08.ISAproj.dto.NarudzbenicaAdminDTO;
import com.team_08.ISAproj.dto.NarudzbenicaDTO;
import com.team_08.ISAproj.dto.PregledDTO;
import com.team_08.ISAproj.dto.RezervacijaDTO;
import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.AdminApoteke;
import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Farmaceut;
import com.team_08.ISAproj.model.Korisnik;
import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.Narudzbenica;
import com.team_08.ISAproj.model.NarudzbenicaLek;
import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.service.ApotekaLekService;
import com.team_08.ISAproj.service.ApotekaService;
import com.team_08.ISAproj.service.EmailService;
import com.team_08.ISAproj.service.KorisnikService;
import com.team_08.ISAproj.service.LekService;
import com.team_08.ISAproj.service.NarudzbenicaService;

@RestController
@RequestMapping("/narudzbine")
public class NarudzbinaController {


    @Autowired
    private NarudzbenicaService narudzbenicaService;
    @Autowired
    private LekService lekService;
    @Autowired
    private ApotekaService apotekaService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private ApotekaLekService apotekaLekService;
    @Autowired
    private EmailService sendEmailService;

    @PostMapping(value = "/admin")
    public ResponseEntity<List<NarudzbenicaAdminDTO>> dodajNarudzbenice(@RequestBody List<NarudzbenicaAdminDTO> narudzbenice) {

        Narudzbenica n = new Narudzbenica();
        Long apotekaId = Long.parseLong(narudzbenice.get(0).getApotekaId());
        n.setApoteka(apotekaService.findOne(Long.parseLong(narudzbenice.get(0).getApotekaId())));
        n.setRokPonude(narudzbenice.get(0).getDatumNarudzbine());
        n.setPreuzet(true);
        narudzbenicaService.saveNarudzbenica(n);
        for (NarudzbenicaAdminDTO nDTO : narudzbenice) {
            Lek l = lekService.findOneBySifra(nDTO.getSifra());
            NarudzbenicaLek nl = new NarudzbenicaLek(nDTO.getKolicina(), n, l);
            narudzbenicaService.saveNarudzbenicaLek(nl);
            ApotekaLek al = apotekaLekService.findInApotekaLek(l.getId(), apotekaId);
            al.setKolicina(al.getKolicina() + nDTO.getKolicina());
            apotekaLekService.saveAL(al);
        }
        return new ResponseEntity<List<NarudzbenicaAdminDTO>>(narudzbenice, HttpStatus.OK);

    }

    @GetMapping(value = "lekNarudzbina")
    public ResponseEntity<LekDTO> saveLek(@RequestParam String cookie, @RequestParam String sifra) {
        Korisnik k = korisnikService.findUserByToken(cookie);
        if (k == null) {
            return new ResponseEntity<LekDTO>(HttpStatus.NOT_FOUND);
        }
        if (k instanceof AdminApoteke) {

            AdminApoteke aa = (AdminApoteke) k;
            ApotekaLek al = lekService.addApotekaLek(sifra, aa.getApoteka().getId());
            System.out.println(al);
            if (al == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            }
            return new ResponseEntity<LekDTO>(HttpStatus.CREATED);
        }
        return new ResponseEntity<LekDTO>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping(value ="/sveNarudzbine")
    public ResponseEntity<Page<NarudzbenicaDTO>> getNarudzbenice(@RequestParam String cookie,
    		@RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortDesc") Boolean sortDesc){
    	
    	
    	AdminApoteke a = (AdminApoteke) korisnikService.findUserByToken(cookie);
    	if(a == null) {
    		return new ResponseEntity<Page<NarudzbenicaDTO>>(HttpStatus.NOT_FOUND);
    	}
    	Page<Narudzbenica> narudzbenice = null;
    	narudzbenice = narudzbenicaService.findAllNarudzbeniceApotekaPagedAndSorted(a.getApoteka().getId(), page, size, sortBy, sortDesc);
		System.out.println(narudzbenice);
    	if(narudzbenice == null) {
    		return new ResponseEntity<Page<NarudzbenicaDTO>>(Page.empty(), HttpStatus.OK);
    	}
        
    	
	    Page<NarudzbenicaDTO> narudzbeniceDTO = narudzbenice.map(new Function<Narudzbenica, NarudzbenicaDTO>() {
	        @Override
	        public NarudzbenicaDTO apply(Narudzbenica n) {
	        	List<NarudzbenicaLek> lekovi = narudzbenicaService.findNarudzbeniceLekNarudzbenica(n.getId());
	        	NarudzbenicaDTO narudzbenicaDTO = new NarudzbenicaDTO(n);
	        	narudzbenicaDTO.dodajLekove(lekovi);
	            return narudzbenicaDTO;
	        }
	    });
	    return new ResponseEntity<Page<NarudzbenicaDTO>>(narudzbeniceDTO, HttpStatus.OK);
    	
    }

}
