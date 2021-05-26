package com.team_08.ISAproj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team_08.ISAproj.dto.PromocijaDTO;
import com.team_08.ISAproj.model.AdminApoteke;
import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Pretplata;
import com.team_08.ISAproj.model.Promocija;
import com.team_08.ISAproj.service.ApotekaLekService;
import com.team_08.ISAproj.service.ApotekaService;
import com.team_08.ISAproj.service.EmailService;
import com.team_08.ISAproj.service.KorisnikService;
import com.team_08.ISAproj.service.LekService;
import com.team_08.ISAproj.service.NarudzbenicaService;
import com.team_08.ISAproj.service.PacijentService;
import com.team_08.ISAproj.service.PromocijaService;

@RestController
@RequestMapping("/promocije")
public class PromocijeController {
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
    @Autowired 
    private PacijentService pacijentService;
    @Autowired
    private PromocijaService promocijaService;
    
    @PostMapping(value = "/addPromocija")
    public ResponseEntity<Void> addPromocija(@RequestBody List<PromocijaDTO> promocije){
    	
    	AdminApoteke aa = (AdminApoteke) korisnikService.findUserByToken(promocije.get(0).getCookie());
    	if(aa == null) {
    		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    	}
    	String title = "Obavestenje o novoj promociji - " + aa.getApoteka().getNaziv();
    	String poruka = promocije.get(0).getTekstPromocije() + "\n";
    	poruka = poruka + "Na akciji su lekovi: \n";
    	Promocija p = new Promocija();
    	p.setKrajVazenja(promocije.get(0).getKrajVazenja());
    	p.setPocetakVazenja(promocije.get(0).getPocetakVazenja());
    	p.setTekstPromocije(promocije.get(0).getTekstPromocije());
    	promocijaService.savePromocija(p);
    	for (PromocijaDTO pDTO: promocije) {
    		ApotekaLek  al= apotekaLekService.findOneBySifra(pDTO.getSifra(), aa.getApoteka().getId());
    		al.setPromotivnaCena(pDTO.getPromotivnaCena());
    		al.setCena(pDTO.getCena());
    		al.setIstekVazenjaCene(pDTO.getKrajVazenja());
    		al.setPocetakVazenjaCene(pDTO.getPocetakVazenja());
    		apotekaLekService.saveAL(al);
    		poruka = poruka + al.getLek().getNaziv() + " Nova cena:" + al.getPromotivnaCena()+ " Stara cena: " + al.getCena() + "\n";
    	}
    	List<Pretplata> pretplate = pacijentService.findPretplateApoteka(aa.getApoteka().getId());
    	
    	for (Pretplata pretplata: pretplate) {
    		String body = "Poštovani, " + pretplata.getPacijent().getIme() + " obavestavamo vas da postoji nova promocija u " + aa.getApoteka().getNaziv() + ". \n" + "Trajace od " + promocije.get(0).getPocetakVazenja() + " do " + promocije.get(0).getKrajVazenja() + ". \n"+ poruka;
    		try
    		{
    			Thread t = new Thread() {
    				public void run()
    				{
    					sendEmailService.sendEmail(pretplata.getPacijent().getEmailAdresa(), body, title);
    				}
    			};
    			t.start();
    		}
    		catch(Exception e) {
    			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    		}
    	}
    	return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
