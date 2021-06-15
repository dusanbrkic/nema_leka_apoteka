package com.team_08.ISAproj.controller.testing;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.team_08.ISAproj.app.ISAprojApplication;
import com.team_08.ISAproj.controller.LekController;

//@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
@SpringBootApplication(scanBasePackages={"com.team_08.ISAproj"})
@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class controllerTests {

	private static final String URL_LEK_CONTROLLER = "/lekovi";
	private static final String URL_KORISNIK_CONTROLLER = "/korisnici";
	private static final String URL_REZERVACIJA_CONTROLLER = "/rezervacije";
	private static final String URL_PREGLEDI_CONTROLLER = "/pregledi";

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


	//testovi za dermatologa/farmaceuta
	@Test
	public void test1ProveriRezervaciju() throws Exception{
		mockMvc.perform(get(URL_REZERVACIJA_CONTROLLER + "/proveriRezervaciju?cookie=dzon-dzon&idRezervacije=1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$[0].lek.sifra").value(1))
				.andExpect(jsonPath("$[1].lek.sifra").value(2))
		;
	}

	@Test
	public void test2IzdajLek() throws Exception{
		mockMvc.perform(get(URL_REZERVACIJA_CONTROLLER + "/izdajLekove?cookie=dzon-dzon&idRezervacije=1"))
				.andExpect(status().isOk());
	}

	@Test
	public void testGetPreglediForCalendar() throws Exception{
		mockMvc.perform(get(URL_PREGLEDI_CONTROLLER +
				"/getPreglediByZdravstveniRadnik?cookie=dzon-dzon&start=2021-06-27T22:00:00.000Z&end=2021-08-08T22:00:00.000Z"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$[0].id").value(20));
	}

}
