package com.team_08.ISAproj.app;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.Dermatolog;
import com.team_08.ISAproj.model.Farmaceut;
import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.ZdravstveniRadnik;
import com.team_08.ISAproj.service.ApotekaService;
import com.team_08.ISAproj.service.LekService;
import com.team_08.ISAproj.service.OcenaService;
import com.team_08.ISAproj.service.ZdravstveniRadnikService;

@EnableScheduling
@RestController
@EnableJpaRepositories("com.team_08.ISAproj.repository")
@EntityScan("com.team_08.ISAproj.model")
@SpringBootApplication(scanBasePackages={"com.team_08.ISAproj"})
public class ISAprojApplication {

    public static void main(String[] args) {
        SpringApplication.run(ISAprojApplication.class, args);
    }

    
	@Autowired
	private OcenaService ocenaService;
	@Autowired
	private ApotekaService apotekaService;
	@Autowired
	private LekService lekService;
	@Autowired
	private ZdravstveniRadnikService zdravstveniRadnikService;
	
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }
    
    @PostConstruct
    public void SetupGrades() {
    	
    	// postavi prosecne ocene apoteka
    	for(Apoteka a : apotekaService.findAll()) {
    		a.setProsecnaOcena(ocenaService.findProsecnaOcenaApotekeByID(a.getId()));
    		apotekaService.save(a);
    	}
    	
    	// postavi prosecne ocene lekova
    	for(Lek l : lekService.getAllLekovi()) {
    		l.setProsecnaOcena(ocenaService.findProsecnaOcenaLekaByID(l.getId()));
    		lekService.saveLek(l);
    	}
    	
    	// postavi prosecne ocene radnika
    	for(ZdravstveniRadnik z : zdravstveniRadnikService.findAll()) {
    		z.setProsecnaOcena(ocenaService.findProsecnaOcenaZdravstvenogRadnikaByID(z.getId()));
    		if(z instanceof Dermatolog) {
    			zdravstveniRadnikService.saveDermatolog((Dermatolog) z);
    		} else {
    			zdravstveniRadnikService.saveFarmaceut((Farmaceut) z);
    		}
    	}
    }

}
