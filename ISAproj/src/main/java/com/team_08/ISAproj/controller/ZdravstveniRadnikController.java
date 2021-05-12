package com.team_08.ISAproj.controller;

import com.team_08.ISAproj.dto.CookieRoleDTO;
import com.team_08.ISAproj.dto.DermatologDTO;
import com.team_08.ISAproj.dto.FarmaceutDTO;
import com.team_08.ISAproj.dto.KorisnikDTO;
import com.team_08.ISAproj.dto.LekDTO;
import com.team_08.ISAproj.model.*;
import com.team_08.ISAproj.model.enums.KorisnickaRola;
import com.team_08.ISAproj.service.ApotekaService;
import com.team_08.ISAproj.service.EmailService;
import com.team_08.ISAproj.service.KorisnikService;

import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;

import com.team_08.ISAproj.service.PregledService;
import com.team_08.ISAproj.service.ZdravstveniRadnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/zdravstveniradnik")
public class ZdravstveniRadnikController {
    @Autowired
    private ZdravstveniRadnikService zdravstveniRadnikService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private PregledService pregledService;
    @Autowired
    private ApotekaService apotekaService;

    @GetMapping(value = "/putOdsustvo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putOdsustvo(@RequestParam("start") String startDate,
                                              @RequestParam("end") String endDate,
                                              @RequestParam("cookie") String cookie) {
        LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        System.out.println(start);
        System.out.println(end);
        if (end.isBefore(start)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ZdravstveniRadnik z = zdravstveniRadnikService.fetchZdravstveniRadnikWithOdsustva(cookie);
        if (z != null) {
            if (!pregledService.findAllInDateRangeByZdravstveniRadnik(start, end, cookie).isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            z.getOdsustva().add(new Odsustvo(start, end));
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        korisnikService.saveUser(z);
        return new ResponseEntity<>("User successfully updated", HttpStatus.OK);
    }

    @GetMapping(value = "/fetchOdsustvaInDateRange", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Odsustvo>> fetchOdsustvaInDateRange(@RequestParam("cookie") String cookie,
                                                                  @RequestParam("start") String startDate,
                                                                  @RequestParam("end") String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        ZdravstveniRadnik z = zdravstveniRadnikService.fetchZdravstveniRadnikWithOdsustvaInDateRange(cookie, start, end);
        if (z != null) {
            return new ResponseEntity<>(z.getOdsustva(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new HashSet<>(), HttpStatus.OK);
    }

    // svi dermatolozi koji rade u apoteci
    @GetMapping(value = "/getAllDermatologApoteka", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getAllDermatologApotekaByAdmin(@RequestParam(required = false) String title,
                                                                              @RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "6") int size,
                                                                              @RequestParam String cookie) {

        Korisnik k = korisnikService.findUserByToken(cookie);
        System.out.println(k);
        if (k == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (k instanceof AdminApoteke) {
            AdminApoteke aa = (AdminApoteke) k;
            Pageable paging = PageRequest.of(page, size);
            Page<DermatologApoteka> dermatoloziApoteke = zdravstveniRadnikService.fetchDermatologsByApotekaId(aa.getApoteka().getId(), paging);
            List<DermatologDTO> listDermatologDTO = new ArrayList<DermatologDTO>();
            for (DermatologApoteka da : dermatoloziApoteke) {
                listDermatologDTO.add(new DermatologDTO(da));
            }
            Map<String, Object> response = new HashMap<>();
            response.put("dermatolozi", listDermatologDTO);
            response.put("currentPage", dermatoloziApoteke.getNumber());
            response.put("totalItems", dermatoloziApoteke.getTotalElements());
            response.put("totalPages", dermatoloziApoteke.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(value = "/getAllDermatologApotekaList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DermatologDTO>> getAllDermatologApotekaByAdminList(@RequestParam String cookie) {
        Korisnik k = korisnikService.findUserByToken(cookie);
        System.out.println(k);
        if (k == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (k instanceof AdminApoteke) {
            AdminApoteke aa = (AdminApoteke) k;
            List<DermatologApoteka> dermatoloziApoteke = zdravstveniRadnikService.fetchDermatologsByApotekaId(aa.getApoteka().getId());
            List<DermatologDTO> listDermatologDTO = new ArrayList<DermatologDTO>();
            for (DermatologApoteka da : dermatoloziApoteke) {
                listDermatologDTO.add(new DermatologDTO(da));
            }
            return new ResponseEntity<>(listDermatologDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/getAllFarmaceutsApoteka", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FarmaceutDTO>> getAllFarmaceutsApotekaByAdminList(@RequestParam String cookie) {

        Korisnik k = korisnikService.findUserByToken(cookie);
        if (k == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (k instanceof AdminApoteke) {
            AdminApoteke aa = (AdminApoteke) k;
            List<Farmaceut> farmaceutiApoteke = zdravstveniRadnikService.fetchFarmaceutsByApotekaId(aa.getApoteka().getId());
            List<FarmaceutDTO> listFarmaceutDTO = new ArrayList<FarmaceutDTO>();
            for (Farmaceut f : farmaceutiApoteke) {
                listFarmaceutDTO.add(new FarmaceutDTO(f));
            }
            System.out.println(listFarmaceutDTO);
            return new ResponseEntity<>(listFarmaceutDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    //dodavanje farmaceuta
    @PostMapping(value = "/addFarmaceut")
    public ResponseEntity<FarmaceutDTO> addFarmaceut(@RequestBody FarmaceutDTO farmaceutDTO) {

        Korisnik adminApoteke = korisnikService.findUserByToken(farmaceutDTO.getCookie());
        if (adminApoteke == null) {
            return new ResponseEntity<FarmaceutDTO>(HttpStatus.BAD_REQUEST);
        }

        if (adminApoteke instanceof AdminApoteke) {
            AdminApoteke aa = (AdminApoteke) adminApoteke;
            Korisnik k = korisnikService.findUserByEmail(farmaceutDTO.getEmailAdresa());
            if (k != null) {
                return new ResponseEntity<FarmaceutDTO>(HttpStatus.BAD_REQUEST);
            }
            if (korisnikService.findUser(farmaceutDTO.getUsername()) != null) {
                return new ResponseEntity<FarmaceutDTO>(HttpStatus.NOT_FOUND);
            }
            Farmaceut farmaceut = new Farmaceut(farmaceutDTO);
            String ck = CookieToken.createTokenValue(farmaceut.getUsername(), farmaceut.getPassword());
            farmaceut.setCookieTokenValue(ck);
            farmaceut.setApoteka(apotekaService.findOne(aa.getApoteka().getId()));
            zdravstveniRadnikService.saveFarmaceut(farmaceut);
            return new ResponseEntity<FarmaceutDTO>(farmaceutDTO, HttpStatus.OK);
        }
        return new ResponseEntity<FarmaceutDTO>(HttpStatus.BAD_REQUEST);
    }

    //brisanje farmaceuta
    @DeleteMapping(value = "/deleteFarmaceut")
    public ResponseEntity<Void> deleteFarmaceut(@RequestParam String username, @RequestParam String startDate, @RequestParam String cookie) {

        Farmaceut f = (Farmaceut) korisnikService.findUser(username);
        if (f == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        List<Pregled> temp = pregledService.proveraOdDatumaPregleda(start, username);
        System.out.println(temp);
        if (!pregledService.proveraOdDatumaPregleda(start, username).isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        AdminApoteke aa = (AdminApoteke) korisnikService.findUserByToken(cookie);
        if (aa == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        zdravstveniRadnikService.deleteFarmaceuta(f.getId());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    //svi dermatolozi koji ne rade u apoteci
    @GetMapping(value = "/allDermaNotInApoteka")
    public ResponseEntity<List<DermatologDTO>> getDermatologsNotInApoteka(@RequestParam String cookie) {

        Korisnik k = korisnikService.findUserByToken(cookie);
        if (k == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (k instanceof AdminApoteke) {
            AdminApoteke aa = (AdminApoteke) k;

            List<Dermatolog> dermatolozi = zdravstveniRadnikService.fetchDermatologsNotInApotekaList(aa.getApoteka().getId());
            List<DermatologDTO> dermatoloziDTO = new ArrayList<DermatologDTO>();
            for (Dermatolog d : dermatolozi) {
                dermatoloziDTO.add(new DermatologDTO(d));
            }
            return new ResponseEntity(dermatoloziDTO, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }
    // svi dermatolozi + pretraga i page
    @GetMapping(value = "/getAllDermatologApotekaPage")
    public ResponseEntity<Page<DermatologDTO>> getAllDermaPage(@RequestParam("cookie") String cookie,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortDesc") Boolean sortDesc,
            @RequestParam("pretraziIme") String pretraziIme,
            @RequestParam("pretraziPrezime") String pretraziPrezime,
            @RequestParam("ocena") Double ocena,
            @RequestParam("pocetak") String pocetak,
            @RequestParam("kraj") String kraj){
    	
    	AdminApoteke aa = (AdminApoteke) korisnikService.findUserByToken(cookie);
    	if(aa == null) {
    		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    	}
    	Page<DermatologApoteka> dermatoloziApoteke = null;

    	dermatoloziApoteke = zdravstveniRadnikService.findOneDermatologApotekaByIdSearchedSorted(page, size, sortBy, sortDesc, aa.getApoteka().getId(),pretraziIme,pretraziPrezime,ocena,pocetak,kraj);
    	System.out.println(dermatoloziApoteke);
    	if (dermatoloziApoteke == null)
            return new ResponseEntity<Page<DermatologDTO>>(Page.empty(), HttpStatus.OK);
    	
        Page<DermatologDTO> dermatoloziDTO = dermatoloziApoteke.map(new Function<DermatologApoteka, DermatologDTO>() {
            @Override
            public DermatologDTO apply(DermatologApoteka da) {
            	DermatologDTO dermaDTO = new DermatologDTO(da);
                return dermaDTO;
            }
        });
        return new ResponseEntity<Page<DermatologDTO>>(dermatoloziDTO, HttpStatus.OK);
    }
    //svi dermatolozi po nazivu apoteke page
    @GetMapping(value = "/getAllDermatologPage")
    public ResponseEntity<Page<DermatologDTO>> getAllDermaPacijent(@RequestParam("cookie") String cookie,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortDesc") Boolean sortDesc,
            @RequestParam("pretraziIme") String pretraziIme,
            @RequestParam("pretraziPrezime") String pretraziPrezime,
            @RequestParam("ocena") Double ocena,
            @RequestParam("pocetak") String pocetak,
            @RequestParam("kraj") String kraj,
            @RequestParam String apoteka){
    	
    	Pacijent p = (Pacijent) korisnikService.findUserByToken(cookie);
    	if(p == null) {
    		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    	}
    	Page<DermatologApoteka> dermatoloziApoteke = null;

    	dermatoloziApoteke = zdravstveniRadnikService.findAllDermatologApotekaByApotekaNazivSearched(page, size, sortBy, sortDesc, apoteka,pretraziIme,pretraziPrezime,ocena,pocetak,kraj);
    	System.out.println(dermatoloziApoteke);
    	if (dermatoloziApoteke == null)
            return new ResponseEntity<Page<DermatologDTO>>(Page.empty(), HttpStatus.OK);
    	
        Page<DermatologDTO> dermatoloziDTO = dermatoloziApoteke.map(new Function<DermatologApoteka, DermatologDTO>() {
            @Override
            public DermatologDTO apply(DermatologApoteka da) {
            	DermatologDTO dermaDTO = new DermatologDTO(da);
                return dermaDTO;
            }
        });
        return new ResponseEntity<Page<DermatologDTO>>(dermatoloziDTO, HttpStatus.OK);
    }
    // svi farmaceuti admin apoteke
    @GetMapping(value = "/getAllFarmaApotekaPage")
    public ResponseEntity<Page<FarmaceutDTO>> getAllFarmaPage(@RequestParam("cookie") String cookie,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortDesc") Boolean sortDesc,
            @RequestParam("pretraziIme") String pretraziIme,
            @RequestParam("pretraziPrezime") String pretraziPrezime,
            @RequestParam("ocena") Double ocena,
            @RequestParam("pocetak") String pocetak,
            @RequestParam("kraj") String kraj){
    	
    	AdminApoteke aa = (AdminApoteke) korisnikService.findUserByToken(cookie);
    	if(aa == null) {
    		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    	}
    	Page<Farmaceut> farmaceuti = null;

    	farmaceuti = zdravstveniRadnikService.findFarmaceutApotekaByIdSearchedSorted(page, size, sortBy, sortDesc, aa.getApoteka().getId(),pretraziIme,pretraziPrezime,ocena,pocetak,kraj);
    	System.out.println(farmaceuti);
    	if (farmaceuti == null)
            return new ResponseEntity<Page<FarmaceutDTO>>(Page.empty(), HttpStatus.OK);
    	
        Page<FarmaceutDTO> farmaceutDTO = farmaceuti.map(new Function<Farmaceut, FarmaceutDTO>() {
            @Override
            public FarmaceutDTO apply(Farmaceut f) {
            	FarmaceutDTO farmaDTO = new FarmaceutDTO(f);
                return farmaDTO;
            }
        });
        return new ResponseEntity<Page<FarmaceutDTO>>(farmaceutDTO, HttpStatus.OK);
    }
    //svi farmaceuti iz baze
    @GetMapping(value = "/getAllFarmaPage")
    public ResponseEntity<Page<FarmaceutDTO>> getAllFarmaPagePacijent(@RequestParam("cookie") String cookie,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortDesc") Boolean sortDesc,
            @RequestParam("pretraziIme") String pretraziIme,
            @RequestParam("pretraziPrezime") String pretraziPrezime,
            @RequestParam("ocena") Double ocena,
            @RequestParam("pocetak") String pocetak,
            @RequestParam("kraj") String kraj,
            @RequestParam String apoteka){
    	
    	Pacijent p = (Pacijent) korisnikService.findUserByToken(cookie);
    	if(p == null) {
    		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    	}
    	Page<Farmaceut> farmaceuti = null;
    	farmaceuti = zdravstveniRadnikService.findFarmaceutSearchedSorted(page, size, sortBy, sortDesc,apoteka,pretraziIme,pretraziPrezime,ocena,pocetak,kraj);
    	System.out.println(farmaceuti);
    	if (farmaceuti == null)
            return new ResponseEntity<Page<FarmaceutDTO>>(Page.empty(), HttpStatus.OK);
    	
        Page<FarmaceutDTO> farmaceutDTO = farmaceuti.map(new Function<Farmaceut, FarmaceutDTO>() {
            @Override
            public FarmaceutDTO apply(Farmaceut f) {
            	FarmaceutDTO farmaDTO = new FarmaceutDTO(f);
                return farmaDTO;
            }
        });
        return new ResponseEntity<Page<FarmaceutDTO>>(farmaceutDTO, HttpStatus.OK);
    }
    //termini rada Dermatologa
    @GetMapping(value = "/getDermaWorkingHours")
    public ResponseEntity<List<DermatologDTO>> getDermatologWorkingTimes(@RequestParam String username) {


        Korisnik k = korisnikService.findUser(username);
        if (k == null) {
            return new ResponseEntity<List<DermatologDTO>>(HttpStatus.NOT_FOUND);
        }
        if (k instanceof Dermatolog) {
            Dermatolog d = (Dermatolog) k;

            List<DermatologApoteka> dermatologSatiRada = zdravstveniRadnikService.fetchAllWorkingTimesAndPricesForDermatolog(k.getId());
            List<DermatologDTO> dermatoloziDTO = new ArrayList<DermatologDTO>();
            for (DermatologApoteka da : dermatologSatiRada) {
                dermatoloziDTO.add(new DermatologDTO(da));
            }
            return new ResponseEntity<List<DermatologDTO>>(dermatoloziDTO, HttpStatus.OK);
        }
        return new ResponseEntity<List<DermatologDTO>>(HttpStatus.BAD_REQUEST);

    }

    //dodavanje novog Dermatologa
    @GetMapping(value = "/addDermatologApoteke")
    public ResponseEntity<DermatologDTO> addDermatologApoteka(
            @RequestParam("start") String startDate,
            @RequestParam("end") String endDate,
            @RequestParam String cena,
            @RequestParam("cookie") String cookie,
            @RequestParam String username) {

        Korisnik k = korisnikService.findUserByToken(cookie);
        if (k == null) {
            return new ResponseEntity<DermatologDTO>(HttpStatus.NOT_FOUND);
        }
        if (k instanceof AdminApoteke) {
            AdminApoteke aa = (AdminApoteke) k;
            LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
            LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
            List<DermatologApoteka> proveraRadnogVremana = zdravstveniRadnikService.checkIfGivenWorkHoursAreOk(username, start, end);
            System.out.println(proveraRadnogVremana);
            if (!proveraRadnogVremana.isEmpty()) {
                return new ResponseEntity<DermatologDTO>(HttpStatus.BAD_REQUEST);
            }
            Dermatolog d = (Dermatolog) korisnikService.findUser(username);
            if (d == null) {
                return new ResponseEntity<DermatologDTO>(HttpStatus.NOT_FOUND);
            }
            DermatologApoteka da = new DermatologApoteka(d, aa.getApoteka(), start.toLocalTime(), end.toLocalTime());
            zdravstveniRadnikService.addDermatologApoteke(da);
            return new ResponseEntity<DermatologDTO>(HttpStatus.OK);
        }

        return new ResponseEntity<DermatologDTO>(HttpStatus.BAD_REQUEST);

    }

    //brisanje Dermatologa
    @DeleteMapping(value = "/deleteDermatolog")
    public ResponseEntity<Void> deleteDermatolog(@RequestParam String username, @RequestParam String startDate, @RequestParam String cookie) {

        Dermatolog d = (Dermatolog) korisnikService.findUser(username);
        if (d == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        List<Pregled> temp = pregledService.proveraOdDatumaPregleda(start, username);
        System.out.println(temp);
        if (!pregledService.proveraOdDatumaPregleda(start, username).isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        AdminApoteke aa = (AdminApoteke) korisnikService.findUserByToken(cookie);
        if (aa == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        zdravstveniRadnikService.deleteDermatologApoteke(d.getId(), aa.getApoteka().getId());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    //izmena dermatologa
    @GetMapping(value = "/changeDermatolog")
    public ResponseEntity<DermatologDTO> changeDermatolog(@RequestParam("start") String startDate,
                                                          @RequestParam("end") String endDate,
                                                          @RequestParam String cena,
                                                          @RequestParam("cookie") String cookie,
                                                          @RequestParam String username) {

        AdminApoteke aa = (AdminApoteke) korisnikService.findUserByToken(cookie);
        if (aa == null) {
            return new ResponseEntity<DermatologDTO>(HttpStatus.NOT_FOUND);
        }
        LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        List<DermatologApoteka> proveraRadnogVremana = zdravstveniRadnikService.checkIfGivenWorkHoursAreOk(username, aa.getApoteka().getId(), start, end);
        System.out.println(proveraRadnogVremana);
        if (!proveraRadnogVremana.isEmpty()) {
            return new ResponseEntity<DermatologDTO>(HttpStatus.BAD_REQUEST);
        }
        Dermatolog d = (Dermatolog) korisnikService.findUser(username);
        if (d == null) {
            return new ResponseEntity<DermatologDTO>(HttpStatus.NOT_FOUND);
        }
        DermatologApoteka da1 = zdravstveniRadnikService.findDermatologApoteka(d.getId(), aa.getApoteka().getId());
        da1.setRadnoVremeKraj(end.toLocalTime());
        da1.setRadnoVremePocetak(start.toLocalTime());
        zdravstveniRadnikService.addDermatologApoteke(da1);
        return new ResponseEntity<DermatologDTO>(HttpStatus.OK);
    }

    //izmena farmaceuta
    @GetMapping(value = "/changeFarmaceut")
    public ResponseEntity<FarmaceutDTO> changeFarmaceut(@RequestParam("start") String startDate,
                                                        @RequestParam("end") String endDate,
                                                        @RequestParam("cookie") String cookie,
                                                        @RequestParam String username) {
        AdminApoteke aa = (AdminApoteke) korisnikService.findUserByToken(cookie);
        if (aa == null) {
            return new ResponseEntity<FarmaceutDTO>(HttpStatus.NOT_FOUND);
        }
        LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

        Farmaceut f = (Farmaceut) korisnikService.findUser(username);
        if (f == null) {
            return new ResponseEntity<FarmaceutDTO>(HttpStatus.NOT_FOUND);
        }
        f.setRadnoVremePocetak(start.toLocalTime());
        f.setRadnoVremeKraj(end.toLocalTime());
        zdravstveniRadnikService.saveFarmaceut(f);
        return new ResponseEntity<FarmaceutDTO>(HttpStatus.OK);
    }
}
