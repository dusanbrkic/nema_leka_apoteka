package com.team_08.ISAproj.controller;

import com.team_08.ISAproj.dto.FarmaceutDTO;
import com.team_08.ISAproj.dto.LekDTO;
import com.team_08.ISAproj.dto.PregledDTO;
import com.team_08.ISAproj.dto.PregledLekDTO;
import com.team_08.ISAproj.dto.RezervacijaDTO;
import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.exceptions.FarmaceutZauzetException;
import com.team_08.ISAproj.exceptions.LekNijeNaStanjuException;
import com.team_08.ISAproj.exceptions.PregledRezervisanException;
import com.team_08.ISAproj.model.*;
import com.team_08.ISAproj.service.*;

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
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.OptimisticLockException;

@RestController
@RequestMapping("/pregledi")
public class PregledController {
    @Autowired
    private PacijentService pacijentService;
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

    @GetMapping(value = "/nadjiPregled", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PregledDTO> nadjiPregled(
            @RequestParam("cookie") String cookie,
            @RequestParam("start") String startDate) {
        LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        Pregled pregled = null;
        pregled = pregledService.findOneStartsNow(cookie, start);
        if (pregled == null)
            return new ResponseEntity<PregledDTO>(HttpStatus.BAD_REQUEST);

        PregledDTO pregledDTO = new PregledDTO(pregled);

        return new ResponseEntity<PregledDTO>(pregledDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/updatePregledBezPacijenta", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePregledBezPacijenta(@RequestParam Long pregledId,
                                                            @RequestParam String cookie,
                                                            @RequestParam Long pacijentId){
        Pregled pregled = pregledService.findOneById(pregledId);
        if(pregled == null || !pregled.getZdravstveniRadnik().getCookieToken().equals(cookie))
            return new ResponseEntity<String>(HttpStatus.OK);
        pregled.setPregledObavljen(true);
        pregled.setPacijentSeNijePojavio(true);
        pregledService.savePregled(pregled);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/updatePregled", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePregled(@RequestBody PregledDTO pregledDTO) {
        Pregled pregled = pregledService.findOneById(pregledDTO.getId());
        if (pregled == null)
            return new ResponseEntity<String>(HttpStatus.OK);
        List<PregledLek> lekovi = pregledDTO.getPreporuceniLekovi().stream().map(new Function<PregledLekDTO, PregledLek>() {
            @Override
            public PregledLek apply(PregledLekDTO pregledLekDTO) {
                Lek l = lekService.findOneBySifra(pregledLekDTO.getLek().getSifra());
                PregledLek pl = new PregledLek(pregledLekDTO.getKolicina(), pregledLekDTO.getTrajanjeTerapije(), pregled, l);
                return pl;
            }
        }).collect(Collectors.toList());
        pregled.setPreporuceniLekovi(new HashSet<>(lekovi));
        pregled.setPregledObavljen(true);
        pregled.setDijagnoza(pregledDTO.getDijagnoza());

        // r e z e r v a c i j a
        if (pregled.getPreporuceniLekovi().isEmpty()) return new ResponseEntity<String>(HttpStatus.OK);

        Rezervacija rezervacija = new Rezervacija();
        rezervacija.setPreuzeto(false);
        rezervacija.setApoteka(pregled.getApoteka());
        rezervacija.setDatumPonude(LocalDateTime.now());
        rezervacija.setRokPonude(LocalDateTime.now().plusDays(7));
        rezervacija.setPacijent(pregled.getPacijent());
        rezervacija.setIsteklo(false);
        rezervacija.setRokPonuda(LocalDateTime.now().plusDays(7));
        rezervacija.setLekovi(new HashSet<RezervacijaLek>());

        String body = "Poštovani, " + rezervacija.getPacijent().getIme() + "\n"
                + "Vasa rezervacija se sastoji iz:\n";

        double ukupnaCena = 0;

        //konkurentno odredi novu kolicinu
        try {
            apotekaLekService.updateKolicinaLekovaKonkurentno(pregled.getPreporuceniLekovi(), pregled.getApoteka().getId(), false);
        } catch (LekNijeNaStanjuException e) {
            return new ResponseEntity<String>("Lek nije na stanju zbog konflikta!", HttpStatus.BAD_REQUEST);
        }

        //obracunaj cenu
        for (PregledLek pregledLek : pregled.getPreporuceniLekovi()) {
            //upit koji izvlaci pojedinacnu cena leka sa datim ID-jem
            double trenutnaCenaLeka = apotekaLekService.findInApotekaLek(pregledLek.getLek().getId(), pregled.getApoteka().getId()).getCena();

            rezervacija.getLekovi()
                    .add(new RezervacijaLek(pregledLek.getKolicina(), rezervacija, pregledLek.getLek(), trenutnaCenaLeka));
            double cenaLeka = pregledLek.getKolicina() * trenutnaCenaLeka;
            ukupnaCena += cenaLeka;
            body += "	- " + pregledLek.getLek().getNaziv() + " x " + pregledLek.getKolicina() + "kom - " + cenaLeka + "din. \n";
        }

        //sacuvaj
        rezervacijaService.saveRezervacija(rezervacija);
        pregledService.savePregled(pregled);


        body += "Ukupna cena je: " + ukupnaCena + " dinara \n"
                + "Rezervaciju možete pokupiti do datuma: " + rezervacija.getRokPonude().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm")) + "\n\n"
                + "Za sva dodatna pitanja obratite nam se na ovaj mejl.\n"
                + "Srdačan pozdrav.";

        String title = "Potvrda Rezervacije Leka (ID:" + rezervacija.getId() + ")";

        String body_tmp = body;

        try {
            Thread t = new Thread() {
                public void run() {
                    sendEmailService.sendEmail(rezervacija.getPacijent().getEmailAdresa(), body_tmp, title);
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
        if(zdravstveniRadnikService.findOneByCookie(cookie)==null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // provera da li je dermatolog ili farmaceut slobodan
        if (!pregledService.findAllInDateRangeByZdravstveniRadnik(start, end, cookie).isEmpty())
            return new ResponseEntity<String>("Vi ste zauzeti u zadatom terminu!",HttpStatus.BAD_REQUEST);
        // provera da li je pacijent slobodan
        if (!pregledService.findAllInDateRangeByPacijentId(start, end, idPacijenta).isEmpty())
            return new ResponseEntity<String>("Pacijent je zauzet u zadatom terminu!", HttpStatus.BAD_REQUEST);
        //provera da li je unutar radnog vremena
        if (zdravstveniRadnikService.checkRadnoVreme(start.toLocalTime(), end.toLocalTime(), cookie, idApoteke)==null)
            return new ResponseEntity<String>("Termin nije unutar vaseg radnog vremena!", HttpStatus.BAD_REQUEST);
        //provera da li je na odsustvu
        if (zdravstveniRadnikService.fetchZdravstveniRadnikWithOdsustvaInDateRange(cookie, start, end)!=null)
            return new ResponseEntity<String>("Vi ste na odsustvu u tom terminu!", HttpStatus.BAD_REQUEST);

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateTime = p.getVreme().format(formatter);

        String body = "Poštovani, " + p.getPacijent().getIme() + "\n"
                + "Zakazali ste pregled kod dermatologa. Više informacija u nastavku. \n"
                + "Datum i vreme: " + dateTime + "\n"
                + "Dermatolog: " + p.getZdravstveniRadnik().getIme() + " " + p.getZdravstveniRadnik().getPrezime() + "\n"
                + "Cena: " + p.getCena() + "\n"
                + "Apoteka: " + p.getApoteka().getNaziv() + "\n"
                + "Za sva dodatna pitanja obratite nam se na ovaj mejl.\n"
                + "Srdačan pozdrav.";

        String title = "Potvrda pregleda (ID:" + p.getId() + ")";

        try
        {
            Thread t = new Thread() {
                public void run()
                {
                    sendEmailService.sendEmail(p.getPacijent().getEmailAdresa(), body, title);
                }
            };
            t.start();
        }
        catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

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
        if (!pregledService.findAllInDateRangeByPacijentId(p.getVreme(), p.getKraj(), idPacijenta).isEmpty())
            return new ResponseEntity<String>("Pacijent je zauzet u zadatom terminu!", HttpStatus.BAD_REQUEST);
        p.setPacijent(pacijentService.findOneById(idPacijenta));
        p.setCena(p.getApoteka().getCenaPregleda());
        p.setPregledZakazan(true);
        pregledService.savePregled(p);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateTime = p.getVreme().format(formatter);

        String body = "Poštovani, " + p.getPacijent().getIme() + "\n"
                + "Zakazali ste pregled kod dermatologa. Više informacija u nastavku. \n"
                + "Datum i vreme: " + dateTime + "\n"
                + "Dermatolog: " + p.getZdravstveniRadnik().getIme() + " " + p.getZdravstveniRadnik().getPrezime() + "\n"
                + "Cena: " + p.getCena() + "\n"
                + "Apoteka: " + p.getApoteka().getNaziv() + "\n"
                + "Za sva dodatna pitanja obratite nam se na ovaj mejl.\n"
                + "Srdačan pozdrav.";

        String title = "Potvrda pregleda (ID:" + p.getId() + ")";

        try
        {
            Thread t = new Thread() {
                public void run()
                {
                    sendEmailService.sendEmail(p.getPacijent().getEmailAdresa(), body, title);
                }
            };
            t.start();
        }
        catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

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
    		if(p.getZdravstveniRadnik() instanceof Dermatolog) {
    			pregledi.add(new PregledDTO(p.getId(), p.getVreme(), p.getKraj(), p.getCena(), p.getZdravstveniRadnik().getIme() + " " + p.getZdravstveniRadnik().getPrezime()));
    		}
    	}
    	return new ResponseEntity<List<PregledDTO>>(pregledi, HttpStatus.OK);
	}
	@GetMapping(value="/zakaziPregled")
	public ResponseEntity<Void> otkaziRezervaciju(
			@RequestParam("id_pregleda") Long id_pregleda,
			@RequestParam("cookie") String cookie
	) throws InterruptedException{
			Pacijent p = (Pacijent) korisnikService.findUserByToken(cookie);
			
			Pregled pregled = null;
			
			
			// zakljucavamo pregled
			try {
					pregled = pregledService.zakaziPregledKonkurentno(id_pregleda, p);
			} catch (PregledRezervisanException e) {
	            return new ResponseEntity<>(HttpStatus.CONFLICT);
	        }
			
			//Pregled pregled = pregledService.findOneById(id_pregleda);
			
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
    public ResponseEntity<List<PregledDTO>> getPoseteDermatologu(
    		@RequestParam("cookie") String cookie,
    		@RequestParam("sortBy") String sortBy,
    		@RequestParam("sortDesc") Boolean sortDesc){
    	
    	List<PregledDTO> pregledi = new ArrayList<PregledDTO>();
    	
    	Pacijent pacijent = (Pacijent) korisnikService.findUserByToken(cookie);
    	
    	for(Pregled p : pregledService.findAllByPacijent(pacijent, sortBy, sortDesc)) {
    		if (p.getZdravstveniRadnik() instanceof Dermatolog) {
	    		PregledDTO tmp = new PregledDTO(p);
	    		tmp.setUsername(p.getZdravstveniRadnik().getIme() + " " + p.getZdravstveniRadnik().getPrezime());
	    		
	    		Set<PregledLekDTO> set = new HashSet<PregledLekDTO>();
	    		
	    		for(PregledLek pl : pregledService.getAllPreporuceniLekoviFromPregledID(p.getId()).get(0).getPreporuceniLekovi()) {
	    			set.add(new PregledLekDTO(pl));
	    		}
	    		tmp.setPreporuceniLekovi(set);
	    		
	    		pregledi.add(tmp);
    		}
    	}
    	return new ResponseEntity<List<PregledDTO>>(pregledi, HttpStatus.OK);
	}
	@GetMapping(value="/otkazi-pregled")
	public ResponseEntity<Void> otkaziPregled(@RequestParam String id_pregleda){
		
			Pregled p = pregledService.findOneById(Long.parseLong(id_pregleda));
			if(p==null || !p.isPregledZakazan()) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
	    	
			p.setPacijent(null);
			p.setPregledZakazan(false);
			pregledService.savePregled(p);
	    	
	    	return new ResponseEntity<>(HttpStatus.OK);
	}
    @GetMapping(value = "/createSavetovanje")
    public ResponseEntity<Void> createSavetovanje(@RequestParam("start") String startDate,
                                                       @RequestParam("end") String endDate,
                                                       @RequestParam("cookie") String cookie,
                                                       @RequestParam("idFarmaceuta") Long idFarmaceuta,
                                                       @RequestParam("idApoteke") Long idApoteke) throws InterruptedException {
    	
        LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        
        ZdravstveniRadnik zdravstveniRadnik = (ZdravstveniRadnik) zdravstveniRadnikService.findOneById(idFarmaceuta);
        Pacijent pac = (Pacijent) korisnikService.findUserByToken(cookie);
        Apoteka a = apotekaService.findOne(idApoteke);
        
        Pregled p = null;
        
        // pessimistic save
        try {
            p = pregledService.savePregledAndCheckIfFarmacistsIsFreeConcurent(pac, idApoteke, idFarmaceuta, start, end);
        } catch (FarmaceutZauzetException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        
        String body = "Poštovani, " + pac.getIme() + "\n"
				+ "Zakazali ste savetovanje kod farmaceuta. Više informacija u nastavku. \n"
				+ "Datum i vreme: " + start + "\n"
				+ "Farmaceut: " + zdravstveniRadnik.getIme() + " " + zdravstveniRadnik.getPrezime() + "\n"
				+ "Cena: " + a.getCenaSavetovanja() + " din. \n"
				+ "Za sva dodatna pitanja obratite nam se na ovaj mejl.\n"
				+ "Srdačan pozdrav.";
		
		String title = "Potvrda savetovanja (ID:" + p.getId() + ")";
	
		try
		{
			Thread t = new Thread() {
				public void run()
				{
					sendEmailService.sendEmail(pac.getEmailAdresa(), body, title);
				}
			};
			t.start();
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping(value = "savetovanja_farmaceuta")
    public ResponseEntity<List<PregledDTO>> getSavetovanjaFarmaceuta(
    		@RequestParam("cookie") String cookie,
    		@RequestParam("sortBy") String sortBy,
    		@RequestParam("sortDesc") Boolean sortDesc){
    	
    	List<PregledDTO> savetovanja = new ArrayList<PregledDTO>();
    	
    	Pacijent pacijent = (Pacijent) korisnikService.findUserByToken(cookie);
    	
    	for(Pregled p : pregledService.findAllByPacijent(pacijent, sortBy, sortDesc)) {
    		if (p.getZdravstveniRadnik() instanceof Farmaceut) {
	    		PregledDTO tmp = new PregledDTO(p);
	    		tmp.setUsername(p.getZdravstveniRadnik().getIme() + " " + p.getZdravstveniRadnik().getPrezime());
	    		savetovanja.add(tmp);
    		}
    	}
    	return new ResponseEntity<List<PregledDTO>>(savetovanja, HttpStatus.OK);
	}
    @GetMapping(value = "/pregledIzvestaj")
    public ResponseEntity<Map<Integer,Integer>> pregledIzvestaj(@RequestParam("cookie") String cookie,
    		@RequestParam("vremenskiPeriod") String vremenskiPeriod,
    		@RequestParam(required = false) int godina){
    	
    	Korisnik k = korisnikService.findUserByToken(cookie);
    	if(k == null) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	AdminApoteke a = (AdminApoteke) k;
    	Map<Integer,Integer> izvestaj = new HashMap<Integer,Integer>();	
    	if(vremenskiPeriod.equals("Mesecni")) {
    		List<Pregled> pregledi = pregledService.findAllFromApotekaFinishedYear(a.getApoteka().getId(),godina);
        	Map<LocalDateTime, List<Pregled>> data = pregledi.stream().collect(Collectors.groupingBy(d -> d.getVreme().withDayOfMonth(1)));
        	
        	for(int i = 1; i<13;i++) {
        		izvestaj.put(i, 0);
        	}
        	for (LocalDateTime date : data.keySet()) {
        		izvestaj.put(date.toLocalDate().getMonth().getValue(), data.get(date).size());
        	}
    	}
    	else if(vremenskiPeriod.equals("Godisnji")) {
    		for(int i = 0;i<5;i++) {
    			izvestaj.put(2018+i, pregledService.findAllFromApotekaFinishedYear(a.getApoteka().getId(),2018+i).size());
    		}
    	}
    	else if(vremenskiPeriod.equals("Kvartalni")) {
    		List<Pregled> pregledi = pregledService.findAllFromApotekaFinishedYear(a.getApoteka().getId(),godina);
    		Map<Object, List<Pregled>> data = pregledi.stream().collect(Collectors.groupingBy(d -> d.getVreme().get(IsoFields.QUARTER_OF_YEAR)));
    		//System.out.print(data);
    		for(int i = 1;i<5;i++) {
    			izvestaj.put(i,0);
    		}
    		for (Object date : data.keySet()) {
    			System.out.println(date);
    			izvestaj.put((Integer) date, data.get(date).size());
        	}
    	}
    	
    	//System.out.println(izvestaj);

    	return new ResponseEntity<Map<Integer,Integer>> (izvestaj, HttpStatus.OK);
    }
}

