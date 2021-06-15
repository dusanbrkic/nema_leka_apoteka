package com.team_08.ISAproj.service.testing;


import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.Pacijent;
import com.team_08.ISAproj.model.enums.OblikLeka;
import com.team_08.ISAproj.model.enums.TipLeka;
import com.team_08.ISAproj.repository.LekRepository;
import com.team_08.ISAproj.repository.PacijentRepository;
import com.team_08.ISAproj.service.LekService;
import com.team_08.ISAproj.service.PacijentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;

import static com.team_08.ISAproj.constants.PacijentConstants.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

//@WebMvcTest(ISAprojApplication.class)
//@SpringBootApplication(scanBasePackages={"com.team_08.ISAproj"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class serviceTests {
    
	@Mock
	private PacijentRepository pacijentRepositoryMock;
	@Mock
	private Pacijent pacijentMock;
	@Mock
	private LekRepository lekRepositoryMock;

	
	@InjectMocks
	private PacijentService pacijentService;
	@InjectMocks
	private LekService lekService;
	
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
}