package com.team_08.ISAproj.controller;

import java.util.List;

import com.team_08.ISAproj.dto.*;
import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.remoting.jaxws.SimpleHttpServerJaxWsServiceExporter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team_08.ISAproj.service.ApotekaLekService;
import com.team_08.ISAproj.service.ApotekaService;
import com.team_08.ISAproj.service.EmailService;
import com.team_08.ISAproj.service.KorisnikService;
import com.team_08.ISAproj.service.LekService;
import com.team_08.ISAproj.service.NarudzbenicaService;
import com.team_08.ISAproj.service.OcenaService;
import com.team_08.ISAproj.service.PacijentService;
import com.team_08.ISAproj.service.RezervacijaService;
import com.team_08.ISAproj.service.ZahtevLekService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lekovi")
public class LekController {


    @Autowired
    private ApotekaLekService apotekaLekService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private LekService lekService;
    @Autowired
    private ApotekaService apotekaService;
    @Autowired
    private EmailService sendEmailService;
    @Autowired
    private NarudzbenicaService narudzbenicaService;
    @Autowired
    private ZahtevLekService zahtevLekService;
    @Autowired
    private PacijentService pacijentService;
    @Autowired
    private OcenaService ocenaService;
	@Autowired
	private RezervacijaService rezervacijaService;


    @GetMapping(value = "/sviLek", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getSviLekovi(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        try {
            Pageable paging = PageRequest.of(page, size);

            Page<Lek> lekovi;
            lekovi = lekService.findAll(paging);

            List<LekDTO> lekoviDTO = new ArrayList<LekDTO>();
            for (Lek a : lekovi) {
                lekoviDTO.add(new LekDTO(a));
            }

            Map<String, Object> response = new HashMap<>();
            response.put("lekovi", lekovi);
            response.put("currentPage", lekovi.getNumber());
            response.put("totalItems", lekovi.getTotalElements());
            response.put("totalPages", lekovi.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("lekoviSaAlergijom")
    public ResponseEntity<Map<String, Object>> getLekoviSaAlergijom(
    		@RequestParam String cookie,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        try {
            Pageable paging = PageRequest.of(page, size);

            Page<ApotekaLek> apotekeLekovi;
            if (title == null)
                apotekeLekovi = apotekaLekService.findAll(paging);
            else
                apotekeLekovi = apotekaLekService.findByLekContaining(null, paging); //todo

            List<LekDTO> lekovi = new ArrayList<LekDTO>();
            int i = 0;
            
            Pacijent p = pacijentService.fetchPacijentWithAlergijeByCookie(cookie);
            
            for (ApotekaLek a : apotekeLekovi) {
                lekovi.add(new LekDTO(a));	
                for(Lek l : p.getAlergije())
                {
                	if(l.getId() == a.getLek().getId())
                	{
                		lekovi.get(i).setAlergija(true);
                	}
                }

                i++;
            }

            Map<String, Object> response = new HashMap<>();
            response.put("lekovi", lekovi);
            response.put("currentPage", apotekeLekovi.getNumber());
            response.put("totalItems", apotekeLekovi.getTotalElements());
            response.put("totalPages", apotekeLekovi.getTotalPages());


            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/aa", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<LekDTO>> getLekApoteka(@RequestParam("cookie") String cookie,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortDesc") Boolean sortDesc,
            @RequestParam("title") String title) {

    	AdminApoteke aa = (AdminApoteke) korisnikService.findUserByToken(cookie);
    	if(aa == null) {
    		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    	}
    	Page<ApotekaLek> lekovi = null;
		Sort sort;
        if (sortDesc)
            sort = Sort.by(sortBy).descending();
        else
            sort = Sort.by(sortBy).ascending();
    	lekovi = apotekaLekService.findAllApotekaLekoviSortedAndSearchedAndDone(page, size, sortBy, sortDesc, title,aa.getApoteka().getId());

    	if (lekovi == null)
            return new ResponseEntity<Page<LekDTO>>(Page.empty(), HttpStatus.OK);

        Page<LekDTO> lekoviDTO = lekovi.map(new Function<ApotekaLek, LekDTO>() {
            @Override
            public LekDTO apply(ApotekaLek l) {
            	LekDTO lekDTO = new LekDTO(l);
                return lekDTO;
            }
        });
        return new ResponseEntity<Page<LekDTO>>(lekoviDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/basic", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LekDTO>> getLekApoteka(@RequestParam String cookie) {

        Korisnik k = korisnikService.findUserByToken(cookie);

        if (k == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (k instanceof AdminApoteke) {
            AdminApoteke aa = (AdminApoteke) k;
            List<ApotekaLek> apotekeLekovi = apotekaLekService.findOneByApoteka(aa.getApoteka().getId());
            if (apotekeLekovi == null) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            List<LekDTO> lekovi = new ArrayList<LekDTO>();
            for (ApotekaLek a : apotekeLekovi) {
                lekovi.add(new LekDTO(a));
            }
            return new ResponseEntity<List<LekDTO>>(lekovi, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@GetMapping(value="/basic2" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LekDTO>> getLekByApotekaID(@RequestParam String apotekaID) {

    	List<ApotekaLek> apotekeLekovi = apotekaLekService.findOneByApoteka(Long.parseLong(apotekaID));
		if(apotekeLekovi == null) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		List<LekDTO> lekovi = new ArrayList<LekDTO>();
		for (ApotekaLek a : apotekeLekovi) {
			lekovi.add(new LekDTO(a.getLek()));
		}
		return new ResponseEntity<List<LekDTO>>(lekovi, HttpStatus.OK);
	}
	//dodavanje leka
	@PostMapping(consumes = "application/json")
	public ResponseEntity<LekDTO> saveLek(@RequestBody LekDTO lekDTO){
		Korisnik k = korisnikService.findUserByToken(lekDTO.getCookie());
		if(k == null) {
			return new ResponseEntity<LekDTO>(HttpStatus.NOT_FOUND);
		}
		if(k instanceof AdminApoteke) {

			AdminApoteke aa = (AdminApoteke) k;

			Boolean check = lekService.saveLekApoteka(lekDTO,aa.getApoteka().getId().toString());
			if(check) {
				return new ResponseEntity<LekDTO>(lekDTO,HttpStatus.CREATED);
			}
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<LekDTO>(HttpStatus.NOT_FOUND);
	}

	//uredjivanje leka
	@PutMapping(consumes = "application/json",produces = "application/json")
	public ResponseEntity<LekDTO> updateLek(@RequestBody LekDTO lekDTO){
		System.out.println(lekDTO.getCookie());
		Korisnik k = korisnikService.findUserByToken(lekDTO.getCookie());

		if (k == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if(k instanceof AdminApoteke) {
			System.out.println(lekDTO.getCookie());
			AdminApoteke aa = (AdminApoteke) k;
			Lek l = lekService.findOneBySifra(lekDTO.getSifra());
			Apoteka a = apotekaService.findOne(aa.getApoteka().getId());
			ApotekaLek al = apotekaLekService.findOneBySifra(l,aa.getApoteka().getId());
			al.update(lekDTO);
			apotekaLekService.saveAL(al);
			return new ResponseEntity<LekDTO>(lekDTO,HttpStatus.OK);
		}
		return new ResponseEntity<LekDTO>(lekDTO,HttpStatus.NOT_FOUND);
	}
	//brisanje leka
	@DeleteMapping(value = "/deleteLek")
	public ResponseEntity<Void> deleteLek(@RequestParam String sifraLeka,@RequestParam String cookie){
		Korisnik k = korisnikService.findUserByToken(cookie);
		System.out.println(k);
		if (k == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if(k instanceof AdminApoteke) {
			AdminApoteke aa = (AdminApoteke) k;
			Lek l = lekService.findOneBySifra(sifraLeka);
			if(l == null) {

				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			apotekaLekService.removeBySifra(l.getId(),aa.getApoteka().getId());
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}





	//narucivanje lekova
	@GetMapping(value="/narucivanje_lek" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getLekoveNaruci(@RequestParam("cookie") String cookie,
			@RequestParam("apotekaID") String apotekaId,
			@RequestParam(defaultValue = "15") int size,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(required = false) String title,
	        @RequestParam(defaultValue = "naziv") String sort,
	        @RequestParam(defaultValue = "opadajuce") String smer){


		Pageable paging = PageRequest.of(page, size);
		Page<Lek> lekovi;
		// svi lekovi u bazi
		//lekovi = lekService.findAllSearch(paging,title);
		lekovi = lekService.findAll(paging);
		ApotekaLek al;
		LekDTO lekDTO;
		List<LekDTO> lekoviDTO = new ArrayList<LekDTO>();
		for(Lek l: lekovi) {
			//proveravamo da li postoji u apoteci
			al = apotekaLekService.findInApotekaLek(l.getId(),Long.parseLong(apotekaId));
			if(al == null) {
				lekDTO = new LekDTO(l);
			}
			else {
				lekDTO = new LekDTO(al);
			}
			lekoviDTO.add(lekDTO);
		}
		Map<String, Object> response = new HashMap<>();
		response.put("lekovi", lekoviDTO);
		response.put("currentPage", lekovi.getNumber());
		response.put("totalItems", lekovi.getTotalElements());
		response.put("totalPages", lekovi.getTotalPages());
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}
	//dodavanje lekova iz baze
	@GetMapping(value = "/ostali")
	public ResponseEntity<Map<String, Object>> getOstaliLekovi(@RequestParam("cookie") String cookie,
			@RequestParam("apotekaID") String apotekaId,
			@RequestParam(defaultValue = "6") int size,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(required = false) String title,
	        @RequestParam(defaultValue = "naziv") String sort,
	        @RequestParam(defaultValue = "opadajuce") String smer){


		Pageable paging = PageRequest.of(page, size);
		Page<Lek> lekovi;
		lekovi = lekService.test(paging,Long.parseLong(apotekaId));
		ApotekaLek al;
		LekDTO lekDTO;
		List<LekDTO> lekoviDTO = new ArrayList<LekDTO>();
		for(Lek l: lekovi) {
			al = apotekaLekService.findInApotekaLek(l.getId(),Long.parseLong(apotekaId));
			lekoviDTO.add(new LekDTO(l));
		}
		Map<String, Object> response = new HashMap<>();
		response.put("lekovi", lekoviDTO);
		response.put("currentPage", lekovi.getNumber());
		response.put("totalItems", lekovi.getTotalElements());
		response.put("totalPages", lekovi.getTotalPages());
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}
	//narucivanje lekova
	@GetMapping(value="/narucivanje_lek_pacijant" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getLekoveNaruciPacijent(@RequestParam("cookie") String cookie,
			@RequestParam("apotekaID") String apotekaId,
			@RequestParam(defaultValue = "15") int size,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(required = false) String title,
	        @RequestParam(defaultValue = "naziv") String sort,
	        @RequestParam(defaultValue = "opadajuce") String smer){


		Pageable paging = PageRequest.of(page, size);
		Page<Lek> lekovi;
		// svi lekovi u bazi
		//lekovi = lekService.findAllSearch(paging,title);
		Page<ApotekaLek> alLista = apotekaLekService.findLekoviByApotekaID(Long.parseLong(apotekaId), paging);
		List<LekDTO> lekoviDTO = new ArrayList<LekDTO>();
		for(ApotekaLek al: alLista) {
			lekoviDTO.add(new LekDTO(al));
		}
		Map<String, Object> response = new HashMap<>();
		response.put("lekovi", lekoviDTO);
		response.put("currentPage", alLista.getNumber());
		response.put("totalItems", alLista.getTotalElements());
		response.put("totalPages", alLista.getTotalPages());
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}
	
	
	@PostMapping(value="/getAllByPacijentNotAllergic" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<LekDTO>> getAllByPacijentNotAllergic(
			@RequestBody Map<String, Object> body){

		String pretraga = (String) body.get("pretraga");
		Long idPacijenta = ((Number) body.get("idPacijenta")).longValue();
		String cookie = (String) body.get("cookie");
		int page = (int) body.get("page");
		int pageSize = (int) body.get("pageSize");
		List<String> vecPreporuceniSifre = (List<String>) body.get("vecPreporuceniSifre");
		String nedostupanLekSifra = (String) body.get("nedostupanLek");
		Integer kolicina = Integer.parseInt((String)body.get("kolicina"));
		Long apotekaID = ((Number) body.get("apotekaID")).longValue();

		vecPreporuceniSifre.add("");

		Page<Lek> lekovi = null;
		if(nedostupanLekSifra == null)
			lekovi = lekService.getAllByPacijentNotAllergic(page, pageSize, idPacijenta, pretraga, vecPreporuceniSifre);
		else
			lekovi = lekService.getAllZamenskiLekovi(page, pageSize, idPacijenta, pretraga, vecPreporuceniSifre, nedostupanLekSifra, apotekaID, kolicina);

		if (lekovi == null)
			return new ResponseEntity<Page<LekDTO>>(Page.empty(), HttpStatus.OK);

		Page<LekDTO> lekoviDTO = lekovi.map(new Function<Lek, LekDTO>() {
			@Override
			public LekDTO apply(Lek l) {
				LekDTO lekDTO = new LekDTO(l);
				return lekDTO;
			}
		});
		return new ResponseEntity<Page<LekDTO>>(lekoviDTO, HttpStatus.OK);
	}
	
	@PostMapping(value="/getAllLekovi" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<LekDTO>> getAllLekovi(
			@RequestBody Map<String, Object> body){

		String pretraga = (String) body.get("pretraga");
		int page = (int) body.get("page");
		int pageSize = (int) body.get("pageSize");

		Page<ApotekaLek> lekovi = null;
		lekovi = apotekaLekService.getAllLekovi(page, pageSize, pretraga);
		

		if (lekovi == null)
			return new ResponseEntity<Page<LekDTO>>(Page.empty(), HttpStatus.OK);

		Page<LekDTO> lekoviDTO = lekovi.map(new Function<ApotekaLek, LekDTO>() {
			@Override
			public LekDTO apply(ApotekaLek l) {
				LekDTO lekDTO = new LekDTO(l);
				lekDTO.setProsecnaOcena(ocenaService.findProsecnaOcenaLekaByID(l.getId()));
				if(lekDTO.getProsecnaOcena()==null) {
					lekDTO.setProsecnaOcena((double) 0);
				}
				lekDTO.setBrojOcena(ocenaService.findOceneLekaByID(l.getId()).size());
				return lekDTO;
			}
		});
		return new ResponseEntity<Page<LekDTO>>(lekoviDTO, HttpStatus.OK);
	}
	
	@PostMapping(value="/getAllLekoviAlergican" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<LekDTO>> getAllLekoviAlergican(
			@RequestBody Map<String, Object> body){

		String pretraga = (String) body.get("pretraga");
		
		int page = (int) body.get("page");
		int pageSize = (int) body.get("pageSize");
		Pacijent p = pacijentService.fetchPacijentWithAlergijeByCookie((String) body.get("cookie"));
		
		
		Page<ApotekaLek> lekovi = null;
		lekovi = apotekaLekService.getAllLekovi(page, pageSize, pretraga);
		

		if (lekovi == null)
			return new ResponseEntity<Page<LekDTO>>(Page.empty(), HttpStatus.OK);
		
	

		Page<LekDTO> lekoviDTO = lekovi.map(new Function<ApotekaLek, LekDTO>() {
			@Override
			public LekDTO apply(ApotekaLek l) {
				LekDTO lekDTO = new LekDTO(l);
				for(Lek lek : p.getAlergije())
	            {
	            	if(l.getId() == lek.getId())
	            	{
	            		lekDTO.setAlergija(true);
	            	}
	            }
				lekDTO.setProsecnaOcena(ocenaService.findProsecnaOcenaLekaByID(l.getId()));
				if(lekDTO.getProsecnaOcena()==null) {
					lekDTO.setProsecnaOcena((double) 0);
				}
				lekDTO.setBrojOcena(ocenaService.findOceneLekaByID(l.getId()).size());
				
				if(rezervacijaService.findRezervacijaLekFromKorisnikByLek(p.getId(), l.getId()).size() != 0) {
					lekDTO.setPravoOcene(true);
				}
				
				return lekDTO;
			}
		});
		return new ResponseEntity<Page<LekDTO>>(lekoviDTO, HttpStatus.OK);
	}
	
	//dostupnost leka
	@GetMapping(value="/proveriDostupnostLeka" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> proveriDostupnostLeka(
			@RequestParam String sifraLeka,
			@RequestParam String cookie,
			@RequestParam Long idApoteke,
			@RequestParam Integer kolicina){
		ZdravstveniRadnik zr = (ZdravstveniRadnik) korisnikService.findUserByToken(cookie);
		ApotekaLek al = apotekaLekService.findOneBySifra(sifraLeka, idApoteke);
		Apoteka apoteka = apotekaService.fetchOneByIdWithAdmini(idApoteke);
		Lek lek = lekService.findOneBySifra(sifraLeka);
		Integer kolicinaUApoteci = null;
		if (al!=null){
			kolicinaUApoteci = al.getKolicina();
		} else{
			kolicinaUApoteci = 0;
		}
		if (al==null || al.getKolicina() < kolicina) {
			ZahtevLek zahtevLek = new ZahtevLek(apoteka,lek,kolicina,zr);
			System.out.println("--------------------------------------------");
			zahtevLekService.saveZahtevLek(zahtevLek);
			System.out.println("--------------------------------------------");
			for (AdminApoteke a : apoteka.getAdmini()){
				String title = "Lek nedostaje u apoteci (" + lek.getNaziv() + ")";
				String bodyTmp = "Postovani,\n\nNedostaje lek u apoteci " + apoteka.getNaziv() +
						" kojoj ste vi administrator.\n";
				bodyTmp+=lek.toString() + '\n';
				bodyTmp+="Kolicina leka u apoteci: " + kolicinaUApoteci + '\n';
				bodyTmp+="Zatrazena kolicina: " + kolicina + '\n';
				final String body = bodyTmp;
				try
				{
					Thread t = new Thread() {
						public void run()
						{
							sendEmailService.sendEmail(a.getEmailAdresa(), body, title);
						}
					};
					t.start();
				}
				catch(Exception e) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
			return new ResponseEntity<>(false, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
	}
	
	//svi zahtevi za lekove koji nisu na stanju za apoteku
	@GetMapping(value="/zahteviZaLek")
	public ResponseEntity<Page<ZahtevLekDTO>> getZahtevLekByApotekaId(@RequestParam String cookie,
			@RequestParam("page") Integer page,
            @RequestParam("size") Integer size){

		AdminApoteke a = (AdminApoteke) korisnikService.findUserByToken(cookie);
		
		if(a == null) {
			return new ResponseEntity<Page<ZahtevLekDTO>>(HttpStatus.NOT_FOUND);
		}
		Page<ZahtevLek> zahtevLekovi = null;
		zahtevLekovi = zahtevLekService.findAllByApotekaId(page,size,a.getApoteka().getId());
    	if (zahtevLekovi == null)
            return new ResponseEntity<Page<ZahtevLekDTO>>(Page.empty(), HttpStatus.OK);

        Page<ZahtevLekDTO> zahteviLekDTO = zahtevLekovi.map(new Function<ZahtevLek, ZahtevLekDTO>() {
            @Override
            public ZahtevLekDTO apply(ZahtevLek zl) {
            	ZahtevLekDTO zahteviLekDTO = new ZahtevLekDTO(zl);
                return zahteviLekDTO;
            }
        });
        return new ResponseEntity<Page<ZahtevLekDTO>>(zahteviLekDTO, HttpStatus.OK);
		
		
	}
	
	@GetMapping(value="/setAlergija")
	public ResponseEntity<Void> setAlergija(@RequestParam String cookie,
											@RequestParam String id){
		Lek l = lekService.findOneBySifra(id);
		Pacijent p = pacijentService.fetchPacijentWithAlergijeByCookie(cookie);
		
		p.addAlergija(l);
		korisnikService.saveUser(p);

        return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value="/otkaziAlergija")
	public ResponseEntity<Void> otkaziAlergija(@RequestParam String cookie,
											@RequestParam String id){
		Lek l = lekService.findOneBySifra(id);
		Pacijent p = pacijentService.fetchPacijentWithAlergijeByCookie(cookie);
		
		System.out.println("========================================================================================================================");
		System.out.println(p.getAlergije().size());
		
		
		for(Lek lek : p.getAlergije()) {
			//if(lek.getId()==Long.getLong(id))
				p.removeAlergija(lek);
		}
		System.out.println(p.getAlergije().size());
		
		korisnikService.saveUser(p);


        return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value="/getOcena")
	public ResponseEntity<Map<String, Object>> getOcena(@RequestParam String cookie,
										 				@RequestParam String id){
		Lek l = lekService.findOneBySifra(id);
		Pacijent p = pacijentService.fetchPacijentWithAlergijeByCookie(cookie);
		OcenaLek ocenaLek = ocenaService.findOcenaLekaByPacijentID(l.getId(), p.getId());
		
		if(ocenaLek == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("ocena", ocenaLek.getOcena());
		
        return new ResponseEntity<Map<String, Object>>( response, HttpStatus.OK);
	}
	
	@GetMapping(value="/oceni")
	public ResponseEntity<Void> setOcena(@RequestParam String cookie,
										 @RequestParam String id,
										 @RequestParam Integer ocena){
		Lek l = lekService.findOneBySifra(id);
		Pacijent p = pacijentService.fetchPacijentWithAlergijeByCookie(cookie);

		OcenaLek ocenaLek = ocenaService.findOcenaLekaByPacijentID(l.getId(), p.getId());
		
		if(ocenaLek == null) {
			ocenaLek = new OcenaLek(l, ocena, LocalDateTime.now(), p);
		}
		else {
			ocenaLek.setDatum(LocalDateTime.now());
			ocenaLek.setOcena(ocena);
		}

		ocenaService.saveOcena(ocenaLek);


        return new ResponseEntity<>(HttpStatus.OK);
	}
}
