package com.team_08.ISAproj.controller;

import java.text.DateFormat;
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

import com.team_08.ISAproj.dto.LekDTO;
import com.team_08.ISAproj.dto.NarudzbenicaAdminDTO;
import com.team_08.ISAproj.dto.NarudzbenicaDTO;
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

	@PostMapping(value="/admin")
	public ResponseEntity<List<NarudzbenicaAdminDTO>> dodajNarudzbenice(@RequestBody List<NarudzbenicaAdminDTO> narudzbenice){
		
		Narudzbenica n = new Narudzbenica();
		n.setApoteka(apotekaService.findOne(Long.parseLong(narudzbenice.get(0).getApotekaId())));
		n.setRokPonude(narudzbenice.get(0).getDatumNarudzbine());
		n.setPacijent(null);
		narudzbenicaService.saveNarudzbenica(n);
		for(NarudzbenicaAdminDTO nDTO: narudzbenice) {
			Lek l = lekService.findOneBySifra(nDTO.getSifra());
			NarudzbenicaLek nl = new NarudzbenicaLek(nDTO.getKolicina(), n, l);
			narudzbenicaService.saveNarudzbenicaLek(nl);

		}
		return new ResponseEntity<List<NarudzbenicaAdminDTO>>(narudzbenice,HttpStatus.OK);
		
	}
	@GetMapping(value="lekNarudzbina")
	public ResponseEntity<LekDTO> saveLek(@RequestParam String cookie,@RequestParam String sifra){
		Korisnik k = korisnikService.findUserByToken(cookie);
		if(k == null) {
			return new ResponseEntity<LekDTO>(HttpStatus.NOT_FOUND);
		}
		if(k instanceof AdminApoteke) {
    	
			AdminApoteke aa = (AdminApoteke) k;
			ApotekaLek al = lekService.addApotekaLek(sifra,aa.getApoteka().getId());
			System.out.println(al);
			if(al == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
				
			}
			return new ResponseEntity<LekDTO>(HttpStatus.CREATED);
		}
		return new ResponseEntity<LekDTO>(HttpStatus.NOT_FOUND);
	}
	// rezervacija iz jedne apoteke
	@PostMapping(value="/pacijent")
	public ResponseEntity<List<NarudzbenicaDTO>> dodajRezervaciju(@RequestBody List<NarudzbenicaDTO> narudzbenice){
		
		Narudzbenica n = new Narudzbenica();
		n.setApoteka(apotekaService.findOne(Long.parseLong(narudzbenice.get(0).getApotekaId())));
		n.setRokPonude(narudzbenice.get(0).getDatumNarudzbine());
		
		Pacijent pacijent = (Pacijent) korisnikService.findUserByToken(narudzbenice.get(0).getPacijent());
		n.setPacijent(pacijent);
		narudzbenicaService.saveNarudzbenica(n);
		
		String body = "Poštovani, " + pacijent.getIme() + "\n" 
					+ "Vasa rezervacija se sastoji iz:\n";
		
		double ukupnaCena = 0;
		
		for(NarudzbenicaDTO nDTO: narudzbenice) {
			
			Lek l = lekService.findOneBySifra(nDTO.getSifra());
			NarudzbenicaLek nl = new NarudzbenicaLek(nDTO.getKolicina(), n, l);
			narudzbenicaService.saveNarudzbenicaLek(nl);
			
			ApotekaLek apotekaLek = apotekaLekService.findInApotekaLek(l.getId(), Long.parseLong(narudzbenice.get(0).getApotekaId()));
			
			apotekaLek.setKolicina(apotekaLek.getKolicina()-nDTO.getKolicina());
			apotekaLekService.saveAL(apotekaLek);
			
			
			double cena_leka = nDTO.getKolicina()*apotekaLek.getCena();
			ukupnaCena += cena_leka;
			body +=	"	- " + l.getNaziv() + " x " + nDTO.getKolicina() + "kom - " + cena_leka + "din. \n";

		}
		

		body += "Ukupna cena je: " + ukupnaCena + " dinara \n"
				+ "Rezrvaciju možete pokupiti do datuma: " + narudzbenice.get(0).getDatumNarudzbine() + "\n\n"
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
		
		return new ResponseEntity<List<NarudzbenicaDTO>>(narudzbenice,HttpStatus.OK);
		
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
    		 
    		//narudzbenice.add(new NarudzbenicaDTO(
    		//		tmp.getId(),
    		//		tmp.getApoteka().getNaziv(),
    		//		dateFormat.format(tmp.getRokPonude()),
    		//		lekovi));
    	}
    	
		return new ResponseEntity<List<NarudzbenicaDTO>>(narudzbenice, HttpStatus.OK);
	}
}
