package com.team_08.ISAproj;

import static junit.framework.TestCase.fail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.persistence.OptimisticLockException;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.team_08.ISAproj.app.ISAprojApplication;
import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.model.ZdravstveniRadnik;
import com.team_08.ISAproj.service.KorisnikService;
import com.team_08.ISAproj.service.PregledService;
import com.team_08.ISAproj.service.ZdravstveniRadnikService;

@SpringBootTest(classes = ISAprojApplication.class)
class KonkurentnoKreiranjeSavetovanjaPacijentTest {

	@Autowired
    private PregledService pregledService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private ZdravstveniRadnikService zdravstveniRadnikService;

    @RepeatedTest(20)
    public void testRezervacijaLekova() throws Throwable {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                try {
                    LocalDateTime start = LocalDateTime.parse("2021-06-16T03:00:00.000Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
                    LocalDateTime end = LocalDateTime.parse("2021-06-16T04:00:00.000Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
                    ZdravstveniRadnik zdravstveniRadnik = (ZdravstveniRadnik) zdravstveniRadnikService.findOneById(12L);
                    Pacijent pac = (Pacijent) korisnikService.findUserByToken("dule-dule");
                    
                    Pregled p = new Pregled();
                    p.setVreme(start);
                    p.setKraj(end);
                    p.setPregledZakazan(true);
                    p.setPregledObavljen(false);
                    p.setZdravstveniRadnik(zdravstveniRadnik);
                    p.setPacijent(pac);
                    
                	pregledService.savePregledAndCheckIfFarmacistsIsFreeConcurent(p, zdravstveniRadnik, start, end);
                } catch (OptimisticLockException | InterruptedException e) {
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
                    ZdravstveniRadnik zdravstveniRadnik = (ZdravstveniRadnik) zdravstveniRadnikService.findOneById(12L);
                    Pacijent pac = (Pacijent) korisnikService.findUserByToken("tesa-tesa");
                    
                    Pregled p = new Pregled();
                    p.setVreme(start);
                    p.setKraj(end);
                    p.setPregledZakazan(true);
                    p.setPregledObavljen(false);
                    p.setZdravstveniRadnik(zdravstveniRadnik);
                    p.setPacijent(pac);
                    
                	pregledService.savePregledAndCheckIfFarmacistsIsFreeConcurent(p, zdravstveniRadnik, start, end);
                	
                    fail();
                } catch (OptimisticLockException | InterruptedException ignored) {}
            }
        });
        try {
            future2.get();
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass());
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

}
