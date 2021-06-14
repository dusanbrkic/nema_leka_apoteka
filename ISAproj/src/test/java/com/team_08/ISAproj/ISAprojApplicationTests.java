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

import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.model.ZdravstveniRadnik;
import com.team_08.ISAproj.service.ApotekaLekService;
import com.team_08.ISAproj.service.OdsustvoService;
import com.team_08.ISAproj.service.PregledService;
import com.team_08.ISAproj.service.RezervacijaService;
import com.team_08.ISAproj.service.ZdravstveniRadnikService;

@SpringBootTest
class ISAprojApplicationTests {

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
    @Autowired
    private OdsustvoService odsustvoService;
    
//    @RepeatedTest(10)
//    public void testRezervacijaLekova() throws Throwable {
//
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//        executor.submit(new Runnable() {
//
//            @Override
//            public void run() {
//                System.out.println("Startovan Thread 1");
//                try {
//                    LocalDateTime start = LocalDateTime.parse("2021-06-16T03:00:00.000Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
//                    LocalDateTime end = LocalDateTime.parse("2021-06-16T04:00:00.000Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
//                    Pregled p = new Pregled();
//                    ZdravstveniRadnik zdravstveniRadnik = (ZdravstveniRadnik) zdravstveniRadnikService.findOneById(12L);
//                    
//                    p.setVreme(start);
//                    p.setKraj(end);
//                    p.setPregledZakazan(false);
//                    p.setPregledObavljen(false);
//                    p.setZdravstveniRadnik(zdravstveniRadnik);
//                    
//                    pregledService.dodajSlobTerminKonk(p);
//                } catch (OptimisticLockException | InterruptedException | CookieNotValidException e) {
//                    fail();
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
//                try { Thread.sleep(100); } catch (InterruptedException e) { }
//
//                try {
//                    LocalDateTime start = LocalDateTime.parse("2021-06-16T03:00:00.000Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
//                    LocalDateTime end = LocalDateTime.parse("2021-06-16T04:00:00.000Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
//                    Pregled p = new Pregled();
//                    ZdravstveniRadnik zdravstveniRadnik = (ZdravstveniRadnik) zdravstveniRadnikService.findOneById(12L);
//                    
//                    p.setVreme(start);
//                    p.setKraj(end);
//                    p.setPregledZakazan(false);
//                    p.setPregledObavljen(false);
//                    p.setZdravstveniRadnik(zdravstveniRadnik);
//                    
//                	pregledService.dodajSlobTerminKonk(p);
//                    fail();
//                } catch (OptimisticLockException | InterruptedException | CookieNotValidException ignored) {}
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
    @RepeatedTest(10)
    public void testOdsustvoKonk() throws Throwable {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                odsustvoService.findOne(1L);// izvrsavanje transakcione metode traje oko 200 milisekundi

            }
        });
		Future<?> future2 = executor.submit(new Runnable() {
			
			@Override
			public void run() {
		        System.out.println("Startovan Thread 2");
		        try { Thread.sleep(150); } catch (InterruptedException e) { }// otprilike 150 milisekundi posle prvog threada krece da se izvrsava drugi
		        /*
		         * Drugi thread pokusava da izvrsi transakcionu metodu findOneById dok se prvo izvrsavanje iz prvog threada jos nije zavrsilo.
		         * Metoda je oznacena sa NO_WAIT, sto znaci da drugi thread nece cekati da prvi thread zavrsi sa izvrsavanjem metode vec ce odmah dobiti PessimisticLockingFailureException uz poruke u logu:
		         * [pool-1-thread-2] o.h.engine.jdbc.spi.SqlExceptionHelper : SQL Error: 0, SQLState: 55P03
		         * [pool-1-thread-2] o.h.engine.jdbc.spi.SqlExceptionHelper : ERROR: could not obtain lock on row in relation "product"
		         * Prema Postgres dokumentaciji https://www.postgresql.org/docs/9.3/errcodes-appendix.html, kod 55P03 oznacava lock_not_available
		         */
		        odsustvoService.findOne(1L);
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