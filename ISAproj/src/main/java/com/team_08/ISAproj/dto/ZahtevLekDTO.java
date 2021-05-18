package com.team_08.ISAproj.dto;

import com.team_08.ISAproj.model.ZahtevLek;

public class ZahtevLekDTO {
	
	private String username;
	private String ime;
	private String prezime;
	private String lek;
	private String lekSifra;
	private Integer kolicina;
	public ZahtevLekDTO(ZahtevLek zl) {
		username = zl.getZdravstveniRadnik().getUsername();
		ime = zl.getZdravstveniRadnik().getIme();
		prezime = zl.getZdravstveniRadnik().getPrezime();
		lek = zl.getLek().getNaziv();
		lekSifra = zl.getLek().getSifra();
		kolicina = zl.getKolicina();
	}
	public String getLekSifra() {
		return lekSifra;
	}
	public void setLekSifra(String lekSifra) {
		this.lekSifra = lekSifra;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLek() {
		return lek;
	}

	public void setLek(String lek) {
		this.lek = lek;
	}

	public Integer getKolicina() {
		return kolicina;
	}

	public void setKolicina(Integer kolicina) {
		this.kolicina = kolicina;
	}

	public ZahtevLekDTO(String username, String lek, Integer kolicina) {
		super();
		this.username = username;
		this.lek = lek;
		this.kolicina = kolicina;
	}

	public ZahtevLekDTO() {
		super();
	}


	
}
