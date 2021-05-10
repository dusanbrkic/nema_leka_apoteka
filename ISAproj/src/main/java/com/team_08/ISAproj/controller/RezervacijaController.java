package com.team_08.ISAproj.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team_08.ISAproj.dto.NarudzbenicaDTO;
import com.team_08.ISAproj.dto.RezervacijaDTO;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Korisnik;
import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.Narudzbenica;
import com.team_08.ISAproj.model.NarudzbenicaLek;
import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.model.Rezervacija;
import com.team_08.ISAproj.model.RezervacijaLek;
import com.team_08.ISAproj.service.ApotekaLekService;
import com.team_08.ISAproj.service.ApotekaService;
import com.team_08.ISAproj.service.EmailService;
import com.team_08.ISAproj.service.KorisnikService;
import com.team_08.ISAproj.service.LekService;
import com.team_08.ISAproj.service.NarudzbenicaService;
import com.team_08.ISAproj.service.RezervacijaService;

@RestController
@RequestMapping("/rezervacije")
public class RezervacijaController {
	
	@Autowired
	private RezervacijaService rezervacijaService;
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
	
	// rezervacija iz jedne apoteke
	@PostMapping(value="/pacijent")
	public ResponseEntity<List<RezervacijaDTO>> dodajRezervaciju(@RequestBody List<RezervacijaDTO> rezervacije){
		
		Rezervacija n = new Rezervacija();
		n.setApoteka(apotekaService.findOne(Long.parseLong(rezervacije.get(0).getApotekaId())));
		n.setRokPonude(rezervacije.get(0).getDatumRezervacije());
		
		Pacijent pacijent = (Pacijent) korisnikService.findUserByToken(rezervacije.get(0).getPacijent());
		n.setPacijent(pacijent);
		rezervacijaService.saveRezervacija(n);
		
		String body = "Poštovani, " + pacijent.getIme() + "\n" 
					+ "Vasa rezervacija se sastoji iz:\n";
		
		double ukupnaCena = 0;
		
		for(RezervacijaDTO nDTO: rezervacije) {
			
			Lek l = lekService.findOneBySifra(nDTO.getSifraLeka());
			RezervacijaLek rl = new RezervacijaLek(nDTO.getKolicina(), n, l);
			rezervacijaService.saveRezervacijaLek(rl);
			
			ApotekaLek apotekaLek = apotekaLekService.findInApotekaLek(l.getId(), Long.parseLong(rezervacije.get(0).getApotekaId()));
			
			apotekaLek.setKolicina(apotekaLek.getKolicina()-nDTO.getKolicina());
			apotekaLekService.saveAL(apotekaLek);
			
			
			double cena_leka = nDTO.getKolicina()*apotekaLek.getCena();
			ukupnaCena += cena_leka;
			body +=	"	- " + l.getNaziv() + " x " + nDTO.getKolicina() + "kom - " + cena_leka + "din. \n";

		}
		

		body += "Ukupna cena je: " + ukupnaCena + " dinara \n"
				+ "Rezervaciju možete pokupiti do datuma: " + rezervacije.get(0).getDatumRezervacije() + "\n\n"
				+ "Za sva dodatna pitanja obratite nam se na ovaj mejl.\n"
				+ "Srdačan pozdrav.";
		
		String title = "Potvrda Rezervacije Leka (ID:" + n.getId() + ")";
	
		String body_tmp = body;
		
		try
		{
			Thread t = new Thread() {
				public void run()
				{
					sendEmailService.sendEmail(pacijent.getEmailAdresa(), body_tmp, title);
				}
			};
			t.start();
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<List<RezervacijaDTO>>(rezervacije,HttpStatus.OK);
		
	}
	// Rezervacija jednog leka
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
					
					
					Rezervacija n = new Rezervacija((Date) new SimpleDateFormat("yyyy-MM-dd").parse(datum));
					RezervacijaLek nl = new RezervacijaLek(kolicina, n,lek);
					
					n.addRezervacijaLek(nl);
					n.setApoteka(a.getApoteka());
					n.setPacijent(k);
					
					rezervacijaService.saveRezervacija(n);
					rezervacijaService.saveRezervacijaLek(nl);
					
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
	
	// dobavljanje rezervacija
	@GetMapping(value="/moje_rezervacije" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RezervacijaDTO>> getRezervacije(
			@RequestParam("cookie") String cookie)
	{
		

    	List<Rezervacija> n = rezervacijaService.findAllRezervacija();

    	List<RezervacijaDTO> rezervacije = new ArrayList<RezervacijaDTO>();
    	
    	Korisnik korisnik = korisnikService.findUserByToken(cookie);
    	
    	for(Rezervacija tmp : n) {
    		
    		if(tmp.getPacijent().getId().equals(korisnik.getId())) {
    		
	    		String lekovi = "";
	        	boolean first = true;

	    		for(RezervacijaLek rl : rezervacijaService.findAllRezervacijaLek()) {
	    			
	    			if(rl.getRezervacija().getId() == tmp.getId()) {
	    				
	    				if(!first) { lekovi += ", "; } else { first = false; }
	    				lekovi += rl.getLek().getNaziv() + " x " + rl.getKolicina() + " kom";
	    			}
	    		}
	    		
	    		rezervacije.add(new RezervacijaDTO(
	    				tmp.getId(),
	    				lekovi,
	    				tmp.getRokPonude(),
	    				tmp.getApoteka().getNaziv(),
	    				tmp.isPreuzeto()));
    		
    		}
    	}
    	
    	return new ResponseEntity<List<RezervacijaDTO>>(rezervacije, HttpStatus.OK);

		
	}
	
	// rezervacija iz jedne apoteke
	@GetMapping(value="/otkazi-rezervaciju")
	public ResponseEntity<Void> otkaziRezervaciju(@RequestParam String id_rezervacije){
		
			Rezervacija r = rezervacijaService.findRezervacijaByID(Long.parseLong(id_rezervacije));
			if(r==null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
	    	
			List<RezervacijaLek> rl = rezervacijaService.findRezervacijaLekByRezervacijaID(Long.parseLong(id_rezervacije));
			
			for(RezervacijaLek tmp: rl) {
				ApotekaLek al = apotekaLekService.findOneBySifra(tmp.getLek(), r.getApoteka().getId());
				al.setKolicina(al.getKolicina()+tmp.getKolicina());
				apotekaLekService.saveAL(al);
			}
			
	    	rezervacijaService.removeRezervacija(r.getId());
	    	
	    	return new ResponseEntity<>(HttpStatus.OK);
	}
}
