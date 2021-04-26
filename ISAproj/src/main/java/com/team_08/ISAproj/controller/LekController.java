package com.team_08.ISAproj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team_08.ISAproj.dto.LekDTO;
import com.team_08.ISAproj.dto.NarudzbenicaDTO;
import com.team_08.ISAproj.dto.ApotekaDTO;
import com.team_08.ISAproj.dto.CookieRoleDTO;
import com.team_08.ISAproj.dto.KorisnikDTO;
import com.team_08.ISAproj.model.AdminApoteke;
import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Korisnik;
import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.Narudzbenica;
import com.team_08.ISAproj.model.NarudzbenicaLek;
import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.service.ApotekaLekService;
import com.team_08.ISAproj.service.ApotekaService;
import com.team_08.ISAproj.service.EmailService;
import com.team_08.ISAproj.service.KorisnikService;
import com.team_08.ISAproj.service.LekService;
import com.team_08.ISAproj.service.NarudzbenicaService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Map;

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
	
	
//    @RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<LekDTO>> getLekovi1() {
//        List<ApotekaLek> apotekeLekovi = apotekaLekService.findAll();
//        if(apotekeLekovi == null) {
//        	return new ResponseEntity<List<LekDTO>>(HttpStatus.NOT_FOUND);
//        }
//        List<LekDTO> lekovi = new ArrayList<LekDTO>();
//        for(ApotekaLek a : apotekeLekovi) {
//        	lekovi.add(new LekDTO(a.getLek()));
//        }
//        return new ResponseEntity<List<LekDTO>>(lekovi, HttpStatus.OK);
//    }
	
	
	@GetMapping(value="/sviLek" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getSviLekovi(
			@RequestParam(required = false) String title,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "6") int size)
    {
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
	@GetMapping("")
	public ResponseEntity<Map<String, Object>> getLekovi(
			@RequestParam(required = false) String title,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "6") int size)
    {
		try {
	    	Pageable paging = PageRequest.of(page, size);
	    	
	    	Page<ApotekaLek> apotekeLekovi;
	    	if (title == null)
	    		apotekeLekovi = apotekaLekService.findAll(paging);
	        else
	        	apotekeLekovi = apotekaLekService.findByLekContaining(null, paging); //todo
	
			List<LekDTO> lekovi = new ArrayList<LekDTO>();
			for (ApotekaLek a : apotekeLekovi) {
				lekovi.add(new LekDTO(a));
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
	@GetMapping(value="/aa" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getLekApoteka(@RequestParam(required = false) String title,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "6") int size, 
			@RequestParam String apotekaID,
	        @RequestParam(defaultValue = "naziv") String sort,
	        @RequestParam(defaultValue = "opadajuce") String smer) {
		
		List<Order> orders = new ArrayList<Order>();
		
    	Pageable paging = PageRequest.of(page, size);
    	Page<ApotekaLek> apotekeLekovi;
    	apotekeLekovi = apotekaLekService.findLekoviByApotekaID(Long.parseLong(apotekaID), paging);
		if(apotekeLekovi == null) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		List<LekDTO> lekovi = new ArrayList<LekDTO>();
		for (ApotekaLek a : apotekeLekovi) {
			lekovi.add(new LekDTO(a));

		}
		Map<String, Object> response = new HashMap<>();
		response.put("lekovi", lekovi);
		response.put("currentPage", apotekeLekovi.getNumber());
		response.put("totalItems", apotekeLekovi.getTotalElements());
		response.put("totalPages", apotekeLekovi.getTotalPages());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@GetMapping(value="/basic" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LekDTO>> getLekApoteka(@RequestParam String apotekaID) {
    	
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
	
	
	
	// Rezervacija leka
	
	@GetMapping(value="/rezervacija-leka")
	public ResponseEntity<Void> receiveData(
			@RequestParam("sifra") String sifra,
			@RequestParam("kolicina") int kolicina,
			@RequestParam("istekRezervacije") String datum,
			@RequestParam("cookie") String cookie
			) throws ParseException
		{
		
			Lek lek = null;
			
			Pacijent k = (Pacijent) korisnikService.findUserByToken(cookie);
			
			ApotekaLek al = null;
			
			Long porudzbinaId = (long) 0;
			
			for (ApotekaLek a : apotekaLekService.findAll()) {
				if(sifra.equals(a.getLek().getSifra())) {
					lek = a.getLek();
					al = a;
					if(kolicina>al.getKolicina()) {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
					al.setKolicina(al.getKolicina()-kolicina);
		
					apotekaLekService.saveAL(al);
					
					
					Narudzbenica n = new Narudzbenica((Date) new SimpleDateFormat("yyyy-MM-dd").parse(datum));
					NarudzbenicaLek nl = new NarudzbenicaLek(kolicina, n,lek);
					
					n.addNarudzbenicaLek(nl);
					n.setApoteka(a.getApoteka());
					n.setPacijent(k);
					
					narudzbenicaService.saveNarudzbenica(n);
					narudzbenicaService.saveNarudzbenicaLek(nl);
					
					porudzbinaId = n.getId();
				}
			}
		
			if(lek == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		
			String body = "Poštovani, " + k.getIme() + "\n"
					+ "Rezervisali ste lek " + lek.getNaziv() + " x " + kolicina + "kom" +"\n"
					+ "Ukupna cena je: " + al.getCena()*kolicina + " dinara \n"
					+ "Rezervisani lek možete pokupiti do isteka rezervacije " + datum + "\n\n"
					+ "Za sva dodatna pitanja obratite nam se na ovaj mejl.\n"
					+ "Srdačan pozdrav.";
			
			String title = "Potvrda Rezervacije Leka (ID:" + porudzbinaId + ")";
		
			try
			{
				Thread t = new Thread() {
					public void run()
					{
						sendEmailService.sendEmail(k.getEmailAdresa(), body, title);
					}
				};
				t.start();
			}
			catch(Exception e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	
	// dobavljanje narudzbenica
	@GetMapping(value="/moje_porudzbine" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<NarudzbenicaDTO>> getNarudzbenice() {
    	
    	List<Narudzbenica> n = narudzbenicaService.findAllNarudzbenice();

    	List<NarudzbenicaDTO> narudzbenice = new ArrayList<NarudzbenicaDTO>();
    	
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
    	
    	for(Narudzbenica tmp : n) {
    		String lekovi = "";
        	boolean first = true;
    		for(NarudzbenicaLek nl : tmp.getLekovi()) {
    			
    			if(!first) { lekovi += ", "; } else { first = false; }
    			
    			lekovi += nl.getLek().getNaziv() + " x " + nl.getKolicina() + " kom";
    		}
    		 
    		narudzbenice.add(new NarudzbenicaDTO(
    				tmp.getId(),
    				tmp.getApoteka().getNaziv(),
    				dateFormat.format(tmp.getRokPonude()),
    				lekovi));
    	}
    	
		return new ResponseEntity<List<NarudzbenicaDTO>>(narudzbenice, HttpStatus.OK);
	}
	
	//narucivanje lekova
	@GetMapping(value="/narucivanje_lek" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getLekoveNaruci(@RequestParam("cookie") String cookie, 
			@RequestParam("apotekaID") String apotekaId, 
			@RequestParam(defaultValue = "6") int size,
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
	
}
