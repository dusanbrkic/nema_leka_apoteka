package com.team_08.ISAproj.controller.testing;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.team_08.ISAproj.app.ISAprojApplication;

//@WebMvcTest(ISAprojApplication.class)
@SpringBootApplication(scanBasePackages={"com.team_08.ISAproj"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class controllerTests {

	private static final String URL_LEK_CONTROLLER = "/lekovi";
	private static final String URL_KORISNIK_CONTROLLER = "/korisnici";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

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
		.andExpect(jsonPath("$.firstLogin").value(null));
	}
}
