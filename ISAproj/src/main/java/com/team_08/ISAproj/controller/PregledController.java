package com.team_08.ISAproj.controller;

import com.team_08.ISAproj.dto.LekDTO;
import com.team_08.ISAproj.dto.PregledDTO;
import com.team_08.ISAproj.dto.PregledLekDTO;
import com.team_08.ISAproj.dto.RezervacijaDTO;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    private ZdravstveniRadnikService zdravstveniRadnikService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private ApotekaService apotekaService;
    @Autowired
    private RezervacijaService rezervacijaService;
    @Autowired
    private ApotekaLekService apotekaLekService;
    @Autowired
    private EmailService sendEmailService;

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
        ArrayList<RezervacijaDTO> rezervacije = new ArrayList<>();
        List<PregledLek> lekovi = pregledDTO.getPreporuceniLekovi().stream().map(new Function<PregledLekDTO, PregledLek>() {
            @Override
            public PregledLek apply(PregledLekDTO pregledLekDTO) {
                Lek l = lekService.findOneBySifra(pregledLekDTO.getLek().getSifra());
                PregledLek pl = new PregledLek(pregledLekDTO.getKolicina(), pregledLekDTO.getTrajanjeTerapije(), pregled, l);
                rezervacije.add(new RezervacijaDTO(l.getSifra(), pl.getKolicina(),
                        Date.from(LocalDateTime.now().plusDays(7).atZone(ZoneId.systemDefault()).toInstant()),
                        pregled.getApoteka().getId().toString(), pregled.getPacijent().getCookieTokenValue()));
                return pl;
            }
        }).collect(Collectors.toList());
        pregled.setPreporuceniLekovi(new HashSet<>(lekovi));
        pregled.setPregledObavljen(true);
        pregled.setDijagnoza(pregledDTO.getDijagnoza());
        pregledService.savePregled(pregled);

        if (rezervacije.isEmpty()) return new ResponseEntity<String>(HttpStatus.OK);

        // r e z e r v a c i j a  copy paste

        Rezervacija n = new Rezervacija();
        n.setApoteka(apotekaService.findOne(Long.parseLong(rezervacije.get(0).getApotekaId())));
        n.setRokPonude(rezervacije.get(0).getDatumRezervacije());

        Pacijent pacijent = (Pacijent) korisnikService.findUserByToken(rezervacije.get(0).getPacijent());
        n.setPacijent(pacijent);
        rezervacijaService.saveRezervacija(n);

        String body = "Poštovani, " + pacijent.getIme() + "\n"
                + "Vasa rezervacija se sastoji iz:\n";

        double ukupnaCena = 0;

        for (RezervacijaDTO nDTO : rezervacije) {

            Lek l = lekService.findOneBySifra(nDTO.getSifraLeka());
            RezervacijaLek rl = new RezervacijaLek(nDTO.getKolicina(), n, l);
            rezervacijaService.saveRezervacijaLek(rl);

            ApotekaLek apotekaLek = apotekaLekService.findInApotekaLek(l.getId(), Long.parseLong(rezervacije.get(0).getApotekaId()));

            apotekaLek.setKolicina(apotekaLek.getKolicina() - nDTO.getKolicina());
            apotekaLekService.saveAL(apotekaLek);


            double cena_leka = nDTO.getKolicina() * apotekaLek.getCena();
            ukupnaCena += cena_leka;
            body += "	- " + l.getNaziv() + " x " + nDTO.getKolicina() + "kom - " + cena_leka + "din. \n";

        }


        body += "Ukupna cena je: " + ukupnaCena + " dinara \n"
                + "Rezervaciju možete pokupiti do datuma: " + rezervacije.get(0).getDatumRezervacije() + "\n\n"
                + "Za sva dodatna pitanja obratite nam se na ovaj mejl.\n"
                + "Srdačan pozdrav.";

        String title = "Potvrda Rezervacije Leka (ID:" + n.getId() + ")";

        String body_tmp = body;

        try {
            Thread t = new Thread() {
                public void run() {
                    sendEmailService.sendEmail(pacijent.getEmailAdresa(), body_tmp, title);
                }
            };
            t.start();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


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
        if (!pregledService.findAllInDateRangeByZdravstveniRadnik(start, end, cookie).isEmpty())
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        ZdravstveniRadnik zdravstveniRadnik = zdravstveniRadnikService.findOneByCookie(cookie);
        if (zdravstveniRadnik == null)
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
        if (zdravstveniRadnik == null)
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
    public ResponseEntity<PregledDTO> addSlobodanTermin(@RequestBody PregledDTO pregledDTO) {

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

    
    @GetMapping(value = "slobodniPregledi")
    public ResponseEntity<List<PregledDTO>> getSlobodaniTermini(@RequestParam("id_apoteke") Long id_apoteke){
    	
    	List<PregledDTO> pregledi = new ArrayList<PregledDTO>();
    	
    	for(Pregled p : pregledService.findAllFromApoteka(id_apoteke)) {
    		pregledi.add(new PregledDTO(p.getId(), p.getVreme(), p.getKraj(), p.getCena(), p.getZdravstveniRadnik().getIme() + " " + p.getZdravstveniRadnik().getPrezime()));
    	}
    	return new ResponseEntity<List<PregledDTO>>(pregledi, HttpStatus.OK);
	}
	@GetMapping(value="/zakaziPregled")
	public ResponseEntity<Void> otkaziRezervaciju(
			@RequestParam("id_pregleda") Long id_pregleda,
			@RequestParam("cookie") String cookie
	){
			Pacijent p = (Pacijent) korisnikService.findUserByToken(cookie);
			
			Pregled pregled = pregledService.findOneById(id_pregleda);
			pregled.setPacijent(p);
			pregled.setPregledZakazan(true);
			pregledService.savePregled(pregled);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		    String dateTime = pregled.getVreme().format(formatter);
			
			String body = "Poštovani, " + p.getIme() + "\n"
					+ "Zakazali ste pregled kod dermatologa. Više informacija u nastavku. \n"
					+ "Datum i vreme: " + dateTime + "\n"
					+ "Dermatolog: " + pregled.getZdravstveniRadnik().getIme() + " " + pregled.getZdravstveniRadnik().getPrezime() + "\n"
					+ "Cena: " + pregled.getCena() + "\n"
					+ "Za sva dodatna pitanja obratite nam se na ovaj mejl.\n"
					+ "Srdačan pozdrav.";
			
			String title = "Potvrda pregleda (ID:" + pregled.getId() + ")";
		
			try
			{
				Thread t = new Thread() {
					public void run()
					{
						sendEmailService.sendEmail(p.getEmailAdresa(), body, title);
					}
				};
				t.start();
			}
			catch(Exception e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
	    	
	    	return new ResponseEntity<>(HttpStatus.OK);
	}
    @GetMapping(value = "posete_dermatologu")
    public ResponseEntity<List<PregledDTO>> getPoseteDermatologu(@RequestParam("cookie") String cookie){
    	
    	List<PregledDTO> pregledi = new ArrayList<PregledDTO>();
    	
    	Pacijent pacijent = (Pacijent) korisnikService.findUserByToken(cookie);
    	
    	for(Pregled p : pregledService.findAllByPacijent(pacijent)) {
    		PregledDTO tmp = new PregledDTO(p);
    		tmp.setUsername(p.getZdravstveniRadnik().getIme() + " " + p.getZdravstveniRadnik().getPrezime());
    		pregledi.add(tmp);
    	}
    	return new ResponseEntity<List<PregledDTO>>(pregledi, HttpStatus.OK);
	}
}
