package com.team_08.ISAproj.service.testing;


import com.team_08.ISAproj.exceptions.CookieNotValidException;
import com.team_08.ISAproj.model.*;
import com.team_08.ISAproj.model.enums.OblikLeka;
import com.team_08.ISAproj.model.enums.TipLeka;
import com.team_08.ISAproj.repository.*;
import com.team_08.ISAproj.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.team_08.ISAproj.constants.PacijentConstants.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

//@WebMvcTest(ISAprojApplication.class)
@SpringBootApplication(scanBasePackages={"com.team_08.ISAproj"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class serviceTests {
	//klase
	@Mock
	private Pacijent pacijentMock;
	@Mock
	private ApotekaLek apotekaLekMock;
	@Mock
	private Rezervacija rezervacijaMock;
	@Mock
	private Set<Odsustvo> odsustvaMock;
	@Mock
	private List<Pregled> preglediMock;

	//repos
	@Mock
	private PregledRepository pregledRepositoryMock;
	@Mock
	private OdsustvoRepository odsustvoRepositoryMock;
	@Mock
	private PacijentRepository pacijentRepositoryMock;
	@Mock
	private LekRepository lekRepositoryMock;
    @Mock
	private ApotekaLekRepository apotekaLekRepositoryMock;
    @Mock
	private RezervacijaRepository rezervacijaRepositoryMock;

    //servisi
	@InjectMocks
	private PregledService pregledService;
	@InjectMocks
	private OdsustvoService odsustvoService;
	@InjectMocks
	private PacijentService pacijentService;
	@InjectMocks
	private LekService lekService;
	@InjectMocks
	private ApotekaLekService apotekaLekService;
	@InjectMocks
	private RezervacijaService rezervacijaService;

	// ajducki testovi
	@Test 
	public void testFindOnePacijentById() {
		when(pacijentRepositoryMock.findOneById(DB_PACIJENT_ID)).thenReturn(pacijentMock);
		
		// akcija nad servisom
		Pacijent dbPacijent = pacijentService.findOneById(DB_PACIJENT_ID);
		
		assertEquals(pacijentMock, dbPacijent);
		
        verify(pacijentRepositoryMock, times(1)).findOneById(DB_PACIJENT_ID);
        verifyNoMoreInteractions(pacijentRepositoryMock);
	}
	@Test 
	public void testFindAllLekoviByApotedaIdPage() {

		PageRequest pageRequest = PageRequest.of(1, PAGE_SIZE);
		when(lekRepositoryMock.findAllLekoviByApotedaIdPage(DB_APOTEKA_ID, "brufen", pageRequest)).thenReturn(
				new PageImpl<Lek>(Arrays.asList(new Lek("123", "Brufen 200mg", "koristiti jednom dnevno",
						TipLeka.ANTIBIOTIK, OblikLeka.KAPSULA, "sastav", new HashSet<Lek>(), "napomene")).subList(0, 1), pageRequest, 1));
	
		// akcija nad servisom
		Page<Lek> lekovi = lekService.findAllApoteka(pageRequest, DB_APOTEKA_ID, "brufen");
		
		//assertThat(lekovi.hasSize(1));
		
		verify(lekRepositoryMock, times(1)).findAllLekoviByApotedaIdPage(DB_APOTEKA_ID, "brufen", pageRequest);
        verifyNoMoreInteractions(lekRepositoryMock);
	}

	@Test(expected = DataIntegrityViolationException.class)
	@Transactional
	@Rollback(true)
	public void testSaveLekWithNullID() {
		Lek lek = new Lek();
		lek.setNaziv("Brufen");
		lek.setUpuctvo("Koristiti jednom dnevno");
		lek.setTip(TipLeka.ANTIBIOTIK);
		lek.setOblikLeka(OblikLeka.KAPSULA);
		lek.setSastav("200mg");
		lek.setDodatneNapomene("nema");
		// nismo naveli sifru koja je po modelu obavezna
		  
		when(lekRepositoryMock.save(lek)).thenThrow(DataIntegrityViolationException.class);
		
		// akcija nad servisom
		lekService.saveLek(lek);
		
		// verifikacija interakcije sa mock objektima  
		verify(lekRepositoryMock, times(1)).save(lek);
        verifyNoMoreInteractions(lekRepositoryMock);
	}
	//nikolini testovi
	@Test
	public void testFindAllByApotekaId() {
		//apotekaLekService.findOneByApoteka(DB_APOTEKA_ID);
		when(apotekaLekRepositoryMock.findApotekaLekById(1L, DB_APOTEKA_ID)).thenReturn(apotekaLekMock);

		ApotekaLek dbApotekaLek = apotekaLekService.findInApotekaLek(1L, DB_APOTEKA_ID);

		assertEquals(apotekaLekMock, dbApotekaLek);
		verify(apotekaLekRepositoryMock, times(1)).findApotekaLekById(1L, DB_APOTEKA_ID);
		verifyNoMoreInteractions(apotekaLekRepositoryMock);
	}

	//brcini testovi
	@Test
	@Transactional
	public void testFetchRezervacije(){
		LocalDateTime tommorow = LocalDateTime.now().plusDays(1);

		when(rezervacijaRepositoryMock.fetchRezervacijaWithLekoviByIdAndApotekaIdBeforeRok(
				1L, 1L, tommorow)).thenReturn(rezervacijaMock);

		Rezervacija rezervacija = rezervacijaService.fetchRezervacijaWithLekoviByIdAndApotekaIdBeforeRok(
				1L, 1L, tommorow);

		assertEquals(rezervacija, rezervacijaMock);

		verify(rezervacijaRepositoryMock, times(1)).fetchRezervacijaWithLekoviByIdAndApotekaIdBeforeRok(
				1L, 1L, tommorow);
		verifyNoMoreInteractions(rezervacijaRepositoryMock);
	}

	@Test
	public void testFetchOdsustva(){
		LocalDateTime start = LocalDateTime.now();
		LocalDateTime end = LocalDateTime.now().plusDays(7);

		when(odsustvoRepositoryMock.fetchOdsustvaByZdravstveniRadnikCookieInDateRange("dusan-dusan", start, end))
				.thenReturn(odsustvaMock);

		Set<Odsustvo> odsustva = odsustvoService.fetchOdsustvaByZdravstveniRadnikCookieInDateRange("dusan-dusan", start, end);

		assertEquals(odsustva, odsustvaMock);

		verify(odsustvoRepositoryMock, times(1)).fetchOdsustvaByZdravstveniRadnikCookieInDateRange(
				"dusan-dusan", start, end);
		verifyNoMoreInteractions(odsustvoRepositoryMock);
	}

	@Test
	public void testFetchPregledi(){
		LocalDateTime start = LocalDateTime.now().minusDays(30);
		LocalDateTime end = LocalDateTime.now().plusDays(7);

		when(pregledRepositoryMock.fetchAllWithPreporuceniLekoviInDateRangeByZdravstveniRadnik("dusan-dusan", start, end))
				.thenReturn(preglediMock);

		List<Pregled> pregledi=null;
		try {
			pregledi = pregledService.fetchAllWithPreporuceniLekoviInDateRangeByZdravstveniRadnik("dusan-dusan", start, end);
		} catch (CookieNotValidException e) {
			e.printStackTrace();
		}

		assertEquals(pregledi, preglediMock);

		verify(pregledRepositoryMock, times(1)).fetchAllWithPreporuceniLekoviInDateRangeByZdravstveniRadnik(
				"dusan-dusan", start, end);
		verifyNoMoreInteractions(pregledRepositoryMock);
	}
}