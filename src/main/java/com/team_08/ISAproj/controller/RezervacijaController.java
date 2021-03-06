package com.team_08.ISAproj.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.team_08.ISAproj.dto.PregledDTO;
import com.team_08.ISAproj.dto.RezervacijaLekDTO;
import com.team_08.ISAproj.exceptions.RezervacijaNeispravnaException;
import com.team_08.ISAproj.exceptions.LekNijeNaStanjuException;
import com.team_08.ISAproj.model.*;
import com.team_08.ISAproj.repository.FarmaceutRepository;
import com.team_08.ISAproj.repository.RezervacijaLekRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team_08.ISAproj.dto.NarudzbenicaDTO;
import com.team_08.ISAproj.dto.RezervacijaDTO;
import com.team_08.ISAproj.service.ApotekaLekService;
import com.team_08.ISAproj.service.ApotekaService;
import com.team_08.ISAproj.service.EmailService;
import com.team_08.ISAproj.service.KorisnikService;
import com.team_08.ISAproj.service.LekService;
import com.team_08.ISAproj.service.NarudzbenicaService;
import com.team_08.ISAproj.service.PregledService;
import com.team_08.ISAproj.service.RezervacijaService;

import net.bytebuddy.asm.Advice.Local;

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
	@Autowired
	private FarmaceutRepository farmaceutRepository;
	@Autowired
	private PregledService pregledService;

	@GetMapping(value = "/proveriRezervaciju")
	public ResponseEntity<List<RezervacijaLekDTO>> proveriRezervaciju(
			@RequestParam String cookie,
			@RequestParam Long idRezervacije) {
		Farmaceut f = farmaceutRepository.findOneByCookieTokenValue(cookie);

		if (f == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		Rezervacija r = rezervacijaService.fetchRezervacijaWithLekoviByIdAndApotekaIdBeforeRok(idRezervacije,
				f.getApoteka().getId(), LocalDateTime.now().plusDays(1));
		if (r == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		List<RezervacijaLekDTO> rezervacijeLekoviDTO = r.getLekovi().stream().map(new Function<RezervacijaLek, RezervacijaLekDTO>() {
			@Override
			public RezervacijaLekDTO apply(RezervacijaLek rezervacijaLek) {
				return new RezervacijaLekDTO(rezervacijaLek);
			}
		}).collect(Collectors.toList());

		return new ResponseEntity<>(rezervacijeLekoviDTO, HttpStatus.OK);
	}


		@GetMapping(value = "/izdajLekove")
	public ResponseEntity<String> izdajLekove(@RequestParam String cookie,
											  @RequestParam Long idRezervacije){
		Farmaceut f = farmaceutRepository.findOneByCookieTokenValue(cookie);
		Rezervacija r = null;
		try {
			r = rezervacijaService.obradiRezervacijuKonkurentno(idRezervacije,
					f.getApoteka().getId(), LocalDateTime.now().plusDays(1), false);
		} catch (RezervacijaNeispravnaException e) {
			return new ResponseEntity<>("Rezervacija neispravna", HttpStatus.BAD_REQUEST);
		}

		String body = "Po??tovani, " + r.getPacijent().getIme() + "\n"
				+ "Vasa rezervacija sa jedinstveni brojem '" + r.getId() + "' je upravo prihvacena i lekovi su Vam izdati.\n" +
				"Srdacan pozdrav.";

		String title = "Potvrda izdavanja lekova rezervacije (ID:" + r.getId() + ")";

		final String body_tmp = body;

		try
		{
			Rezervacija finalR = r;
			Thread t = new Thread() {
				public void run()
				{
					sendEmailService.sendEmail(finalR.getPacijent().getEmailAdresa(), body_tmp, title);
				}
			};
			t.start();
		}
		catch(Exception e) {
			return new ResponseEntity<>("Greska u slanju mail-a!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// rezervacija iz jedne apoteke
	@PostMapping(value="/pacijent")
	public ResponseEntity<List<RezervacijaDTO>> dodajRezervaciju(@RequestBody List<RezervacijaDTO> rezervacije) throws InterruptedException{

		ArrayList<Long> lekIDs = new ArrayList<Long>();
		ArrayList<Integer> kolicine = new ArrayList<Integer>();

		for(RezervacijaDTO r: rezervacije) {
			lekIDs.add(Long.parseLong(r.getSifraLeka()));
			kolicine.add(r.getKolicina());
		}

		// zakljucavamo kolicinu
		try {
				apotekaLekService.updateKolicinaSlobodnihLekovaKonkurentno(lekIDs, kolicine);
		} catch (LekNijeNaStanjuException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }


		Rezervacija n = new Rezervacija();
		n.setApoteka(apotekaService.findOne(Long.parseLong(rezervacije.get(0).getApotekaId())));
		n.setRokPonude(rezervacije.get(0).getDatumRezervacije());

		Pacijent pacijent = (Pacijent) korisnikService.findUserByToken(rezervacije.get(0).getPacijent());
		n.setPacijent(pacijent);
		rezervacijaService.saveRezervacija(n);

		String body = "Po??tovani, " + pacijent.getIme() + "\n"
					+ "Vasa rezervacija se sastoji iz:\n";

		double ukupnaCena = 0;


		for(RezervacijaDTO nDTO: rezervacije) {

			Lek l = lekService.findOneBySifra(nDTO.getSifraLeka());
			RezervacijaLek rl = new RezervacijaLek(nDTO.getKolicina(), n, l);

			ApotekaLek apotekaLek = apotekaLekService.findInApotekaLek(l.getId(), Long.parseLong(rezervacije.get(0).getApotekaId()));

			rl.setCena(apotekaLek.getCena());
			rezervacijaService.saveRezervacijaLek(rl);

			double cena_leka = nDTO.getKolicina()*apotekaLek.getCena();
			ukupnaCena += cena_leka;
			body +=	"	- " + l.getNaziv() + " x " + nDTO.getKolicina() + "kom - " + cena_leka + "din. \n";

		}


		body += "Ukupna cena je: " + ukupnaCena + " dinara \n"
				+ "Rezervaciju mo??ete pokupiti do datuma: " + rezervacije.get(0).getDatumRezervacije() + "\n\n"
				+ "Za sva dodatna pitanja obratite nam se na ovaj mejl.\n"
				+ "Srda??an pozdrav.";

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
			@RequestParam("sifra") Long sifra,
			@RequestParam("kolicina") int kolicina,
			@RequestParam("istekRezervacije") String datum,
			@RequestParam("cookie") String cookie
			) throws ParseException, InterruptedException
		{

			//Lek lek = null;

			Pacijent k = (Pacijent) korisnikService.findUserByToken(cookie);



			Long porudzbinaId = (long) 0;

			LocalDateTime dateTime = LocalDateTime.parse(datum);

			ArrayList<Long> lekIDs = new ArrayList<Long>();
			lekIDs.add(sifra);

			ArrayList<Integer> kolicine = new ArrayList<Integer>();
			kolicine.add(kolicina);


			// zakljucavamo kolicinu
			try {
					apotekaLekService.updateKolicinaSlobodnihLekovaKonkurentno(lekIDs, kolicine);
			} catch (LekNijeNaStanjuException e) {
	            return new ResponseEntity<>(HttpStatus.CONFLICT);
	        }

			Rezervacija n = new Rezervacija();
			n.setRokPonude(dateTime);

			ApotekaLek al = apotekaLekService.findApotekaLekByLekID(sifra);
			if(al == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			RezervacijaLek nl = new RezervacijaLek(kolicina, n, al.getLek());
			nl.setCena(al.getCena());
			n.addRezervacijaLek(nl);
			n.setApoteka(al.getApoteka());
			n.setPacijent(k);

			rezervacijaService.saveRezervacija(n);
			rezervacijaService.saveRezervacijaLek(nl);

			porudzbinaId = n.getId();

			String body = "Po??tovani, " + k.getIme() + "\n"
					+ "Rezervisali ste lek " + al.getLek().getNaziv() + " x " + kolicina + "kom" +"\n"
					+ "Ukupna cena je: " + al.getCena()*kolicina + " dinara \n"
					+ "Rezervisani lek mo??ete pokupiti do isteka rezervacije " + datum + "\n\n"
					+ "Za sva dodatna pitanja obratite nam se na ovaj mejl.\n"
					+ "Srda??an pozdrav.";

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

	    			if(rl.getRezervacija().getId().equals(tmp.getId())) {

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
	public ResponseEntity<Void> otkaziRezervaciju(@RequestParam String id_rezervacije) throws InterruptedException{

			Rezervacija r = rezervacijaService.findRezervacijaByID(Long.parseLong(id_rezervacije));
			if(r==null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			List<RezervacijaLek> rl = rezervacijaService.findRezervacijaLekByRezervacijaID(Long.parseLong(id_rezervacije));

			ArrayList<Long> lekIDs = new ArrayList<Long>();
			ArrayList<Integer> kolicine = new ArrayList<Integer>();

			for(RezervacijaLek tmp: rl) {

				lekIDs.add(tmp.getLek().getId());
				// dodajemo minus kako bi dodali kolicinu
				kolicine.add(-tmp.getKolicina());
			}

			// zakljucavamo kolicinu
			try {
					apotekaLekService.updateKolicinaSlobodnihLekovaKonkurentno(lekIDs, kolicine);
			} catch (LekNijeNaStanjuException e) {
	            return new ResponseEntity<>(HttpStatus.CONFLICT);
	        }

	    	rezervacijaService.removeRezervacija(r.getId());

	    	return new ResponseEntity<>(HttpStatus.OK);
	}
	//izvestaji za rezervacije
    @GetMapping(value = "/lekIzvestaj")
    public ResponseEntity<Map<Integer,Integer>> lekIzvestaj(@RequestParam("cookie") String cookie,
    		@RequestParam("vremenskiPeriod") String vremenskiPeriod,
    		@RequestParam(required = false) int godina){

    	Korisnik k = korisnikService.findUserByToken(cookie);
    	if(k == null) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	AdminApoteke a = (AdminApoteke) k;
    	Map<Integer,Integer> izvestaj = new HashMap<Integer,Integer>();

    	int suma;
    	if(vremenskiPeriod.equals("Mesecni")) {
    		List<Rezervacija> rezervacije = rezervacijaService.findAllRezervacijeFinishedYear(a.getApoteka().getId(),godina);
        	Map<LocalDateTime, List<Rezervacija>> data = rezervacije.stream().collect(Collectors.groupingBy(d -> d.getRokPonude().withDayOfMonth(1)));
        	System.out.println(data);

        	for(int i = 1; i<13;i++) {
        		izvestaj.put(i, 0);
        	}
        	for (LocalDateTime date : data.keySet()) {
        		suma = 0;
        		for(Rezervacija r: data.get(date)) {
        			for(RezervacijaLek rl: r.getLekovi()) {
        				suma+=rl.getKolicina();
        			}
        		}
        		izvestaj.put(date.toLocalDate().getMonth().getValue(), suma);
        	}
    	}
    	else if(vremenskiPeriod.equals("Kvartalni")) {
    		List<Rezervacija> rezervacije  = rezervacijaService.findAllRezervacijeFinishedYear(a.getApoteka().getId(),godina);
    		Map<Object, List<Rezervacija>> data = rezervacije.stream().collect(Collectors.groupingBy(d -> d.getRokPonude().get(IsoFields.QUARTER_OF_YEAR)));
    		//System.out.print(data);
    		for(int i = 1;i<5;i++) {
    			izvestaj.put(i,0);
    		}
        	for (Object date : data.keySet()) {
        		suma = 0;
        		for(Rezervacija r: data.get(date)) {
        			for(RezervacijaLek rl: r.getLekovi()) {
        				suma+=rl.getKolicina();
        			}
        		}
        		izvestaj.put((Integer) date, suma);
        	}
    	}
    	else if(vremenskiPeriod.equals("Godisnji")) {
    		for(int i = 0;i<5;i++) {
    			List<Rezervacija> rezervacije  = rezervacijaService.findAllRezervacijeFinishedYear(a.getApoteka().getId(),2018+i);
            		suma = 0;
            		for(Rezervacija r: rezervacije) {
            			for(RezervacijaLek rl: r.getLekovi()) {
            				suma+=rl.getKolicina();
            			}
            		}
    			izvestaj.put(2018+i,suma);
    		}
    	}
    	return new ResponseEntity<Map<Integer,Integer>> (izvestaj, HttpStatus.OK);
    }
	@GetMapping(value="/prihodIzvestaj")
	public ResponseEntity<Map<LocalDate,Double>> prihodiIzvestaj(@RequestParam("cookie") String cookie,
    		@RequestParam("vremenskiPeriod") String vremenskiPeriod,
    		@RequestParam("pocetak") String pocetak,
    		@RequestParam("kraj") String kraj){

    	Korisnik k = korisnikService.findUserByToken(cookie);
    	if(k == null) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	AdminApoteke a = (AdminApoteke) k;
    	Map<LocalDate,Double> izvestaj = new HashMap<LocalDate,Double>();
    	double suma;

    	LocalDateTime start = LocalDateTime.parse(pocetak, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    	LocalDateTime end = LocalDateTime.parse(kraj, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		List<Rezervacija> rezervacije = rezervacijaService.findAllRezervacijeFinishedDateRange(a.getApoteka().getId(),start,end);
    	Map<LocalDateTime, List<Rezervacija>> data = rezervacije.stream().collect(Collectors.groupingBy(d -> d.getRokPonude().withDayOfMonth(1)));
    	System.out.println(data);

//    	for(int i = 1; i<13;i++) {
//    		izvestaj.put(i, 0.0);
//    	}
    	for (LocalDateTime date : data.keySet()) {
    		suma = 0.0;
    		for(Rezervacija r: data.get(date)) {
    			for(RezervacijaLek rl: r.getLekovi()) {
    				suma+=rl.getKolicina()*rl.getCena();
    			}
    		}
    		izvestaj.put(date.toLocalDate(), suma);
    	}
    	List<Pregled> pregledi = pregledService.findAllFromApotekaFinishedDateRange(a.getApoteka().getId(),start,end);
    	Map<LocalDateTime, List<Pregled>> data1 = pregledi.stream().collect(Collectors.groupingBy(d -> d.getVreme().withDayOfMonth(1)));
    	for (LocalDateTime date : data1.keySet()) {
			suma = 0.0;
			for(Pregled p: data1.get(date)) {
				suma+= p.getCena();
			}
    		if(izvestaj.containsKey(date.toLocalDate())) {
    			izvestaj.put(date.toLocalDate(), izvestaj.get(date.toLocalDate())+suma);

    		}
    		else {
    			izvestaj.put(date.toLocalDate(),suma);
    		}
    	}
		return new ResponseEntity<Map<LocalDate,Double>> (new TreeMap<LocalDate,Double>(izvestaj), HttpStatus.OK);
		}
}
