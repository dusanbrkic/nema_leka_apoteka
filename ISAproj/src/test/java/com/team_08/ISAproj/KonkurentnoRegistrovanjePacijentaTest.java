package com.team_08.ISAproj;

import static junit.framework.TestCase.fail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.persistence.OptimisticLockException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.model.ZdravstveniRadnik;
import com.team_08.ISAproj.service.KorisnikService;
import com.team_08.ISAproj.service.PregledService;
import com.team_08.ISAproj.service.ZdravstveniRadnikService;

@SpringBootTest
class KonkurentnoRegistrovanjePacijentaTest {

    @Autowired
    private KorisnikService korisnikService;

    @Test
    public void testRezervacijaLekova() throws Throwable {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                try {

                    Pacijent pacijent = new Pacijent();
                	Random rand = new Random();
                    String verificationCode = "";
                    for(int i = 0 ; i < 7 ; i++)
                    {
                        verificationCode += String.valueOf(rand.nextInt(10));
                    }
                    pacijent.setCookieTokenValue(verificationCode);
                    pacijent.setEmailAdresa("steva@gmail.com");
                    pacijent.setUsername("steva");
                    pacijent.setPassword("sifra");
                    
                    korisnikService.savePacijentKonkurentno(pacijent);

                } catch (OptimisticLockException e) {
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
                    Pacijent pacijent = new Pacijent();
                	Random rand = new Random();
                    String verificationCode = "";
                    for(int i = 0 ; i < 7 ; i++)
                    {
                        verificationCode += String.valueOf(rand.nextInt(10));
                    }
                    pacijent.setCookieTokenValue(verificationCode);
                    pacijent.setEmailAdresa("steva@gmail.com");
                    pacijent.setUsername("steva");
                    pacijent.setPassword("sifra");
                    
                    korisnikService.savePacijentKonkurentno(pacijent);
                	
                    fail();
                } catch (OptimisticLockException e) {}
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
