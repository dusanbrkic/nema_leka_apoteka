package com.team_08.ISAproj;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.persistence.OptimisticLockException;

import com.team_08.ISAproj.controller.PregledController;
import com.team_08.ISAproj.dto.PregledDTO;
import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.exceptions.LekNijeNaStanjuException;
import com.team_08.ISAproj.exceptions.SlobodanTerminException;
import com.team_08.ISAproj.model.*;
import com.team_08.ISAproj.service.ApotekaLekService;
import com.team_08.ISAproj.service.ApotekaService;
import com.team_08.ISAproj.service.PregledService;
import com.team_08.ISAproj.service.RezervacijaService;
import com.team_08.ISAproj.service.ZdravstveniRadnikService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.fail;

@SpringBootApplication(scanBasePackages={"com.team_08.ISAproj"})
@SpringBootTest
public class AdminApotekeTest {

    @Autowired
    private ApotekaLekService apotekaLekService;
    @Autowired
    private RezervacijaService rezervacijaService;
    @Autowired 
    private PregledService pregledRepository;
    @Autowired
    private PregledService pregledService;
    @Autowired
    private ZdravstveniRadnikService zdravstveniRadnikService;
    
    
    @Test
    public void testRezervacijaLekova() throws Throwable {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                try {
                    LocalDateTime start = LocalDateTime.parse("2021-06-16T03:00:00.000Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
                    LocalDateTime end = LocalDateTime.parse("2021-06-16T04:00:00.000Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
                    Pregled p = new Pregled();
                    ZdravstveniRadnik zdravstveniRadnik = (ZdravstveniRadnik) zdravstveniRadnikService.findOneById(12L);
                    
                    p.setVreme(start);
                    p.setKraj(end);
                    p.setPregledZakazan(false);
                    p.setPregledObavljen(false);
                    p.setZdravstveniRadnik(zdravstveniRadnik);
                    
                    pregledService.dodajSlobTerminKonk(p);
                } catch (OptimisticLockException | InterruptedException | CookieNotValidException e) {
                    fail();
                }


            }
        });
        Future<?> future2 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                try { Thread.sleep(100); } catch (InterruptedException e) { }

                try {
                    LocalDateTime start = LocalDateTime.parse("2021-06-16T03:00:00.000Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
                    LocalDateTime end = LocalDateTime.parse("2021-06-16T04:00:00.000Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
                    Pregled p = new Pregled();
                    ZdravstveniRadnik zdravstveniRadnik = (ZdravstveniRadnik) zdravstveniRadnikService.findOneById(12L);
                    
                    p.setVreme(start);
                    p.setKraj(end);
                    p.setPregledZakazan(false);
                    p.setPregledObavljen(false);
                    p.setZdravstveniRadnik(zdravstveniRadnik);
                    
                	pregledService.dodajSlobTerminKonk(p);
                	
                    fail();
                } catch (OptimisticLockException | InterruptedException | CookieNotValidException ignored) {}
            }
        });
        try {
            future2.get(); // podize ExecutionException za bilo koji izuzetak iz drugog child threada
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas PessimisticLockingFailureException
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
    
 //   public void slobodniTerminiTest() throws Throwable {

//        ExecutorService executor = Executors.newFixedThreadPool(2);
//        executor.submit(new Runnable() {
//
//            @Override
//            public void run() {
//                System.out.println("Startovan Thread 1");
//                try {
//                    // dodajemo 30 lekova 'strepsils' iz apoteke markovic, u apoteci se nalazi 43
//                	LocalDateTime ldt = LocalDateTime.parse("2021-06-20T13:00:00.000Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
//                	pregledController.addSlobodanTermin(new PregledDTO(1L, LocalDateTime.parse("2021-06-20T13:00:00.000Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")), LocalDateTime.parse("2021-06-20T15:00:00.000Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")), "dusan")); // izvrsavanje transakcione metode traje oko 200 milisekundi
//                } catch (SlobodanTerminException e) {
//                    fail(); // u apoteci ima dovoljno lekova
//                }
//
//
//            }
//        });
//        Future<?> future2 = executor.submit(new Runnable() {
//
//            @Override
//            public void run() {
//                System.out.println("Startovan Thread 2");
//                try { Thread.sleep(150); } catch (InterruptedException e) { }// otprilike 150 milisekundi posle prvog threada krece da se izvrsava drugi
//                /*
//                 * Drugi thread pokusava da izvrsi transakcionu metodu updateKolicinaLekovaKonkurentno dok se prvo izvrsavanje iz prvog threada jos nije zavrsilo.
//                 * Dodajemo jos 30 lekova 'strepsils' iz apoteke markovic, u apoteci ce se nalaziti samo 3 posle prvog thread-a
//                 */
//                try {
//                	pregledController.addSlobodanTermin(new PregledDTO(1L, LocalDateTime.parse("2021-06-20T13:00:00.000Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")), LocalDateTime.parse("2021-06-20T15:00:00.000Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")), "dusan"));
//                    fail(); // ukoliko se ne baci LekNijeNaStanjuException test pada,
//                            // to znaci da je thread koristio objekat iz baze kojem nije oduzeta odgovarajuca kolicina
//                } catch (SlobodanTerminException ignored) {}
//            }
//        });
//        try {
//            future2.get(); // podize ExecutionException za bilo koji izuzetak iz drugog child threada
//        } catch (ExecutionException e) {
//            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas PessimisticLockingFailureException
//            throw e.getCause();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        executor.shutdown();

//    }

//    @Before
//    public void setUp() throws Exception {
//    	Dermatolog d = new Dermatolog();
//    	Apoteka a = new Apoteka(1L);
//    	d.setUsername("dusan");
//    	DermatologApoteka da = new DermatologApoteka(d, a, LocalDateTime.parse("2021-06-20T10:00:00.000Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toLocalTime(),LocalDateTime.parse("2021-06-20T18:00:00.000Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toLocalTime());
//    	
//    	zdravstveniRadnikService.saveDermatolog(d);
//    }

//    @Test
//    public void testIzdavanjeLekova() throws Throwable {
//
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//        executor.submit(new Runnable() {
//
//            @Override
//            public void run() {
//                System.out.println("Startovan Thread 1");
//                try {
//                    // Obradjujemo rezervaciju
//                    rezervacijaService.obradiRezervacijuKonkurentno(10L, 1L, LocalDateTime.now(), true); // izvrsavanje transakcione metode traje oko 200 milisekundi
//                } catch (RezervacijaNeispravnaException e) {
//                    fail(); // rezervacija mora biti ispravna
//                }
//
//
//            }
//        });
//        Future<?> future2 = executor.submit(new Runnable() {
//
//            @Override
//            public void run() {
//                System.out.println("Startovan Thread 2");
//                try { Thread.sleep(150); } catch (InterruptedException e) { }// otprilike 150 milisekundi posle prvog threada krece da se izvrsava drugi
//                /*
//                 * Drugi thread pokusava da izvrsi transakcionu metodu obradiRezervacijuKonkurentno dok se prvo izvrsavanje iz prvog threada jos nije zavrsilo.
//                 * Pokusavamo da oznacimo da je rezervacija preuzeta
//                 */
//                try {
//                    rezervacijaService.obradiRezervacijuKonkurentno(10L, 1L, LocalDateTime.now(), true); // izvrsavanje transakcione metode traje oko 200 milisekundi
//                    fail(); // ukoliko se ne baci RezervacijaNeispravnaException test pada,
//                    // to znaci da thread pronasao objekat iz baze, iako ga je prosla metoda vec obradila
//                    // i oznacila rezervaciju da je izdata
//                } catch (RezervacijaNeispravnaException ignored) {}
//            }
//        });
//        try {
//            future2.get(); // podize ExecutionException za bilo koji izuzetak iz drugog child threada
//        } catch (ExecutionException e) {
//            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas PessimisticLockingFailureException
//            throw e.getCause();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        executor.shutdown();
//
//    }

}