package com.team_08.ISAproj.controller;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.team_08.ISAproj.service.ApotekaLekService;
import com.team_08.ISAproj.service.ApotekaService;
import com.team_08.ISAproj.service.KorisnikService;
import com.team_08.ISAproj.service.OcenaService;
import com.team_08.ISAproj.service.PacijentService;
import com.team_08.ISAproj.service.PregledService;
import com.team_08.ISAproj.service.RezervacijaService;
import com.team_08.ISAproj.service.ZdravstveniRadnikService;

import org.aspectj.weaver.patterns.HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.team_08.ISAproj.dto.ApotekaDTO;
import com.team_08.ISAproj.dto.CookieRoleDTO;
import com.team_08.ISAproj.dto.LekDTO;
import com.team_08.ISAproj.model.AdminApoteke;
import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Dermatolog;
import com.team_08.ISAproj.model.Farmaceut;
import com.team_08.ISAproj.model.Korisnik;
import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.OcenaApoteka;
import com.team_08.ISAproj.model.OcenaLek;
import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.model.ZdravstveniRadnik;
import com.team_08.ISAproj.model.enums.KorisnickaRola;

@RestController
@RequestMapping("/apoteke")
public class ApotekaController {

	@Autowired
	private ApotekaService apotekaService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private PregledService pregledService;
    @Autowired
    private ZdravstveniRadnikService zdravstveniRadnikService;
    @Autowired
    private PacijentService pacijentService;
    @Autowired
    private OcenaService ocenaService;
	@Autowired
	private RezervacijaService rezervacijaService;
	
	
	@PostMapping(value="getall" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ApotekaDTO>> getAllApoteke(
			@RequestBody Map<String, Object> body){

		String pretragaNaziv = (String) body.get("pretragaNaziv");
		String pretragaAdresa = (String) body.get("pretragaAdresa");
		boolean desc = (boolean) body.get("smer");
		Pacijent pacijent = pacijentService.fetchPacijentWithAlergijeByCookie((String) body.get("cookie"));
		
		int page = (int) body.get("page");
		int pageSize = (int) body.get("pageSize");
		
		double cenaOD = Double.valueOf((String) body.get("ocenaOD"));
		double cenaDO = Double.valueOf((String) body.get("ocenaDO"));

		Page<Apoteka> apoteke = apotekaService.getAllApotekePaged(page, pageSize, pretragaNaziv, pretragaAdresa, desc, cenaOD, cenaDO);
		
		if (apoteke == null)
			return new ResponseEntity<Page<ApotekaDTO>>(Page.empty(), HttpStatus.OK);

		Page<ApotekaDTO> apotekeDTO = apoteke.map(new Function<Apoteka, ApotekaDTO>() {
			@Override
			public ApotekaDTO apply(Apoteka a) {
				ApotekaDTO apotekaDTO = new ApotekaDTO(a);
				apotekaDTO.setBrojOcena(ocenaService.findOceneApotekeByID(a.getId()).size());
				
				if(pacijent != null) {
					if(rezervacijaService.findRezervacijaLekFromKorisnikByApoteka(pacijent.getId(), a.getId()).size() != 0) {
						apotekaDTO.setPravoOcene(true);
					}
					else if(pregledService.findPreglediFromKorisnikByApotekaID(pacijent.getId(), a.getId()).size() != 0) {
						apotekaDTO.setPravoOcene(true);
					}
				}
				return apotekaDTO;
			}
		});
		return new ResponseEntity<Page<ApotekaDTO>>(apotekeDTO, HttpStatus.OK);
	}
	
	@GetMapping("save")
	public ResponseEntity<Map<String, Object>> getApoteke(
			@RequestParam(required = false) String title,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "6") int size,
	        @RequestParam(defaultValue = "naziv") String sort,
	        @RequestParam(defaultValue = "opadajuce") String smer,
	        @RequestParam(defaultValue = "0") int fromGrade,
	        @RequestParam(defaultValue = "5") int toGrade,
	        @RequestParam("cookie") String cookie)
    {
		try {
			List<Order> orders = new ArrayList<Order>();
			Pacijent p = pacijentService.fetchPacijentWithAlergijeByCookie(cookie);
			
			if(smer.equals("opadajuce")) {
				Order order1 = new Order(Sort.Direction.DESC, sort);
				orders.add(order1);
			}
			else {
				Order order1 = new Order(Sort.Direction.ASC, sort);
				orders.add(order1);
			}
			
	    	Pageable paging = PageRequest.of(page, size, Sort.by(orders));
	    	
	    	Page<Apoteka> apoteke;
	    	if (title == null)
	    		apoteke = apotekaService.findAll(paging);
	        else
	        	apoteke = apotekaService.findByNazivContaining(title, paging); //todo
	
	    	List<ApotekaDTO> apotekeDTO = new ArrayList<ApotekaDTO>();
	    	
			for (Apoteka a : apoteke) {
				if(a.getProsecnaOcena() >= fromGrade && a.getProsecnaOcena() <= toGrade) {
					ApotekaDTO ad = new ApotekaDTO(a);

					ad.setBrojOcena(ocenaService.findOceneApotekeByID(a.getId()).size());
					
					if(rezervacijaService.findRezervacijaLekFromKorisnikByApoteka(p.getId(), a.getId()).size() != 0) {
						ad.setPravoOcene(true);
					}
					else if(pregledService.findPreglediFromKorisnikByApotekaID(p.getId(), a.getId()).size() != 0) {
						ad.setPravoOcene(true);
					}
					apotekeDTO.add(ad);
				}
			}
			
			Map<String, Object> response = new HashMap<>();
			response.put("apoteke", apotekeDTO);
			response.put("currentPage", apoteke.getNumber());
			response.put("totalItems", apoteke.getTotalElements());
			response.put("totalPages", apoteke.getTotalPages());
			
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping(value = "/saveApoteka")
	public ResponseEntity<ApotekaDTO> saveApoteka(@RequestBody ApotekaDTO aDTO){
		
		Korisnik k = korisnikService.findUserByToken(aDTO.getCookie());
		
        if(k == null) {
            return new ResponseEntity<ApotekaDTO>(HttpStatus.NOT_FOUND);
        }
        if(k instanceof AdminApoteke) {
        	AdminApoteke aa = (AdminApoteke) k;
        	Apoteka a = aa.getApoteka();
        	a.updateInfo(aDTO);
        	apotekaService.save(a);
        }
		return new ResponseEntity<ApotekaDTO>(aDTO,HttpStatus.OK);
		
		
		
		
	}
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApotekaDTO> getApoteka(@PathVariable("id") Long id) {
		Apoteka apoteka = apotekaService.findOne(id);
		
		if (apoteka == null) {
			return new ResponseEntity<ApotekaDTO>(HttpStatus.NOT_FOUND);
		}
		ApotekaDTO a = new ApotekaDTO(apoteka);
		return new ResponseEntity<ApotekaDTO>(a, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Apoteka> createApoteka(@RequestBody ApotekaDTO apotekaDTO) throws Exception {
		Apoteka apoteka = new Apoteka(apotekaDTO);
		apotekaService.create(apoteka);
		return new ResponseEntity<Apoteka>(apoteka, HttpStatus.CREATED);
	}
	//Za admina apoteke pronalazimo apoteku
	@GetMapping(value = "/getByAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApotekaDTO> getApoteka(@RequestParam("cookie") String cookie)
    {
		Korisnik k = korisnikService.findUserByToken(cookie);
		
        if(k == null) {
            return new ResponseEntity<ApotekaDTO>(HttpStatus.NOT_FOUND);
        }
        if(k instanceof AdminApoteke) {
        	AdminApoteke aa = (AdminApoteke) k;
        	System.out.println(aa.getApoteka().getNaziv());
        	ApotekaDTO aDTO = new ApotekaDTO(aa.getApoteka());
        	return new ResponseEntity<ApotekaDTO>(aDTO,HttpStatus.OK);
        }
		return new ResponseEntity<ApotekaDTO>(HttpStatus.NOT_FOUND);
    }
	@GetMapping(value = "/allApoteke")
	public ResponseEntity<List<String>> allApotekeName(@RequestParam String cookie){
		Korisnik k = korisnikService.findUserByToken(cookie);
		if(k == null) {
			return new ResponseEntity<List<String>>(HttpStatus.NOT_FOUND);
		}
		List<String> nazivi = new ArrayList<String>();
		for(Apoteka a: apotekaService.findAll()) {
			nazivi.add(a.getNaziv());
		}
		System.out.println(nazivi);
		return new ResponseEntity<List<String>>(nazivi,HttpStatus.OK);
	}
	
	@GetMapping(value = "/sveApotekeSaSlobodnimFarmaceutom")
	public ResponseEntity<List<ApotekaDTO>> sveApotekeSaSlobodnimFarmaceutom(@RequestParam("start") String startDate,
            															 	 @RequestParam("end") String endDate)
	{
		LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        
        List<ApotekaDTO> apotekeDTO = new ArrayList<ApotekaDTO>();
        DecimalFormat df = new DecimalFormat("###.##");
        boolean slobodan = false;
        for(Apoteka a: apotekaService.findAll()) {
        	
        	for(Farmaceut f : zdravstveniRadnikService.fetchFarmaceutsByApotekaId(a.getId())) {
                if (pregledService.findAllInDateRangeByZdravstveniRadnik(start, end, f.getCookieToken()).isEmpty()) {
                	if (zdravstveniRadnikService.checkRadnoVreme(start.toLocalTime(), end.toLocalTime(), f.getCookieToken(), a.getId())!=null)
                		if (zdravstveniRadnikService.fetchZdravstveniRadnikWithOdsustvaInDateRange(f.getCookieToken(), start, end)==null) {
                        	slobodan = true;
                		}
                }
        	}
        	if(slobodan) {
        		ApotekaDTO apotekaDTO = new ApotekaDTO(a);
        		apotekeDTO.add(apotekaDTO);
        	}
        	slobodan = false;
        }
        
        return new ResponseEntity<List<ApotekaDTO>>(apotekeDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/getOcena")
	public ResponseEntity<Map<String, Object>> getOcena(@RequestParam String cookie,
										 				@RequestParam Long id){
		
		Apoteka a = apotekaService.findOneByID(id);
		Pacijent p = pacijentService.fetchPacijentWithAlergijeByCookie(cookie);
		OcenaApoteka ocenaApoteka = ocenaService.findOcenaApotekeByPacijentID(a.getId(), p.getId());
		
		if(ocenaApoteka == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("ocena", ocenaApoteka.getOcena());
		
        return new ResponseEntity<Map<String, Object>>( response, HttpStatus.OK);
	}
	
	@GetMapping(value="/oceni")
	public ResponseEntity<Void> setOcena(@RequestParam String cookie,
										 @RequestParam Long id,
										 @RequestParam Integer ocena){
		Apoteka a = apotekaService.findOne(id);
		Pacijent p = pacijentService.fetchPacijentWithAlergijeByCookie(cookie);

		OcenaApoteka ocenaApoteka = ocenaService.findOcenaApotekeByPacijentID(a.getId(), p.getId());
		
		if(ocenaApoteka == null) {
			ocenaApoteka = new OcenaApoteka(a, ocena, LocalDateTime.now(), p);
		}
		else {
			ocenaApoteka.setDatum(LocalDateTime.now());
			ocenaApoteka.setOcena(ocena);
		}

		ocenaService.saveOcena(ocenaApoteka);

    	a.setProsecnaOcena(ocenaService.findProsecnaOcenaApotekeByID(a.getId()));
    	apotekaService.save(a);

        return new ResponseEntity<>(HttpStatus.OK);
	}
	@GetMapping(value="/prihodIzvestaj")
	public ResponseEntity<Map<Integer,Integer>> prihodiIzvestaj(@RequestParam("cookie") String cookie,
    		@RequestParam("vremenskiPeriod") String vremenskiPeriod,
    		@RequestParam(required = false) int godina,
    		@RequestParam("pocetak") LocalDateTime pocetak,
    		@RequestParam("kraj") LocalDateTime kraj){
		
    	Korisnik k = korisnikService.findUserByToken(cookie);
    	if(k == null) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	AdminApoteke a = (AdminApoteke) k;
    	Map<Integer,Integer> izvestaj = new HashMap<Integer,Integer>();	
    	
		return new ResponseEntity<Map<Integer,Integer>> (izvestaj, HttpStatus.OK);
	}
}
