package com.team_08.ISAproj.controller.testing;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import com.team_08.ISAproj.app.ISAprojApplication;
import com.team_08.ISAproj.controller.LekController;
import com.team_08.ISAproj.dto.ApotekaDTO;
import com.team_08.ISAproj.model.Apoteka;
import com.team_08.ISAproj.model.Korisnik;
import com.team_08.ISAproj.model.Pregled;

//@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
@SpringBootApplication(scanBasePackages={"com.team_08.ISAproj"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class controllerTests {

	private static final String URL_LEK_CONTROLLER = "/lekovi";
	private static final String URL_KORISNIK_CONTROLLER = "/korisnici";
	private static final String URL_APOTEKE_CONTROLLER = "/apoteke";
	private static final String URL_PREGLED_CONTROLLER = "/pregledi";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype());

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@Rollback(true)
	public void testOceniLek() throws Exception {
		mockMvc.perform(get(URL_LEK_CONTROLLER + "/oceni?cookie=dule-dule&id=4&ocena=3"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testLoginKorisnika() throws Exception {
		mockMvc.perform(get(URL_KORISNIK_CONTROLLER + "/loginUser?username=dule&password=dule")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.cookie").value("dule-dule"))
		.andExpect(jsonPath("$.rola").value("PACIJENT"))
		.andExpect(jsonPath("$.firstLogin").value(false));
	}
	
	@Test
	public void testGetApotekeNames() throws Exception {
		mockMvc.perform(get(URL_APOTEKE_CONTROLLER + "/allApoteke?cookie=token")).andExpect(status().isOk())
		.andExpect(content().string("[\"Apoteka Markovic\",\"Apoteka Djincic\",\"Apoteka Lazarevic\",\"Apoteka Zmaj\",\"Apoteka Antic\"]"));
	}
	
	@Test
	public void testGetOcena() throws Exception {
		mockMvc.perform(get(URL_APOTEKE_CONTROLLER + "/getOcena?cookie=token&id=1")).andExpect(status().isOk())
		.andExpect(content().string("{\"ocena\":3}"));
	}
	
	@Test
	@Rollback(true)
	public void testUpdatePregledBezPacijenta() throws Exception {
		mockMvc.perform(post(URL_PREGLED_CONTROLLER + "/updatePregledBezPacijenta?pregledId=1&cookie=dusan-dusan&pacijentId=2"))
		.andExpect(status().isOk());
	}
	
	@Test
	@Rollback(true)
	public void testCreateSavetovanjePacijenta() throws Exception {
		String start = "2021-06-17T03:00:00.000Z";
		String end = "2021-06-17T04:00:00.000Z";
		mockMvc.perform(get(URL_PREGLED_CONTROLLER + "/createSavetovanje?start="+
		start+"&end=" + end + "&cookie=token&idFarmaceuta=12&idApoteke=1"))
		.andExpect(status().isOk());
	}
}
