package com.team_08.ISAproj.concurrency;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.team_08.ISAproj.exceptions.LekNijeNaStanjuException;
import com.team_08.ISAproj.exceptions.RezervacijaNeispravnaException;
import com.team_08.ISAproj.model.*;
import com.team_08.ISAproj.service.ApotekaLekService;
import com.team_08.ISAproj.service.RezervacijaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.fail;

@SpringBootApplication(scanBasePackages={"com.team_08.ISAproj"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class DermatologFarmaceutTest {

    @Autowired
    private ApotekaLekService apotekaLekService;
    @Autowired
    private RezervacijaService rezervacijaService;

    @Test
    public void testRezervacijaLekova() throws Throwable {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                try {
                    // dodajemo 30 lekova 'strepsils' iz apoteke markovic, u apoteci se nalazi 43
                    apotekaLekService.updateKolicinaLekovaKonkurentno(new HashSet<PregledLek>(Arrays.asList(new PregledLek(30, null, null, new Lek(2L)))), 1L, true); // izvrsavanje transakcione metode traje oko 200 milisekundi
                    fail();
                } catch (LekNijeNaStanjuException e) {
                    fail(); // u apoteci ima dovoljno lekova
                }


            }
        });
        Future<?> future2 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                try { Thread.sleep(150); } catch (InterruptedException e) { }// otprilike 150 milisekundi posle prvog threada krece da se izvrsava drugi
                /*
                 * Drugi thread pokusava da izvrsi transakcionu metodu updateKolicinaLekovaKonkurentno dok se prvo izvrsavanje iz prvog threada jos nije zavrsilo.
                 * Dodajemo jos 30 lekova 'strepsils' iz apoteke markovic, u apoteci ce se nalaziti samo 3 posle prvog thread-a
                 */
                try {
                    apotekaLekService.updateKolicinaLekovaKonkurentno(new HashSet<PregledLek>(Arrays.asList(new PregledLek(30, null, null, new Lek(2L)))), 1L, true);
                    fail(); // ukoliko se ne baci LekNijeNaStanjuException test pada,
                            // to znaci da je thread koristio objekat iz baze kojem nije oduzeta odgovarajuca kolicina
                } catch (LekNijeNaStanjuException ignored) {}
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

    @Before
    public void setUp() throws Exception {
        ApotekaLek apotekaLek = apotekaLekService.findInApotekaLek(2L, 1L);
        apotekaLek.setKolicina(43);
        apotekaLekService.saveAL(apotekaLek);

        Rezervacija r = new Rezervacija();
        r.setId(10L);
        r.setRokPonude(LocalDateTime.now().plusDays(2));
        r.setRokPonuda(LocalDateTime.now().plusDays(2));
        r.setApoteka(new Apoteka(1L));
        r.setPreuzeto(false);
        r.setLekovi(new HashSet<RezervacijaLek>(Arrays.asList(new RezervacijaLek(10, r, new Lek(2L)))));
        rezervacijaService.saveRezervacija(r);
    }

    @Test
    public void testIzdavanjeLekova() throws Throwable {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                try {
                    // Obradjujemo rezervaciju
                    rezervacijaService.obradiRezervacijuKonkurentno(10L, 1L, LocalDateTime.now(), true); // izvrsavanje transakcione metode traje oko 200 milisekundi
                } catch (RezervacijaNeispravnaException e) {
                    fail(); // rezervacija mora biti ispravna
                }


            }
        });
        Future<?> future2 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                try { Thread.sleep(150); } catch (InterruptedException e) { }// otprilike 150 milisekundi posle prvog threada krece da se izvrsava drugi
                /*
                 * Drugi thread pokusava da izvrsi transakcionu metodu obradiRezervacijuKonkurentno dok se prvo izvrsavanje iz prvog threada jos nije zavrsilo.
                 * Pokusavamo da oznacimo da je rezervacija preuzeta
                 */
                try {
                    rezervacijaService.obradiRezervacijuKonkurentno(10L, 1L, LocalDateTime.now(), true); // izvrsavanje transakcione metode traje oko 200 milisekundi
                    fail(); // ukoliko se ne baci RezervacijaNeispravnaException test pada,
                    // to znaci da thread pronasao objekat iz baze, iako ga je prosla metoda vec obradila
                    // i oznacila rezervaciju da je izdata
                } catch (RezervacijaNeispravnaException ignored) {}
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

}