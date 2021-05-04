package com.team_08.ISAproj.dto;

import java.time.LocalDateTime;
import java.util.Date;

import com.team_08.ISAproj.model.Farmaceut;

public class FarmaceutDTO {
	
	
	private String username;
	private String password;
	private String ime;
	private String prezime;
	private Date datumRodjenja;
	private String emailAdresa;
	private String cookie;
	private Boolean firstLogin;
	private String adresa;
	private String grad;
	private String drzava;
	private String brojTelefona;
	private LocalDateTime radnoVremePocetak;
	private LocalDateTime radnoVremeKraj;
	private Double prosecnaOcena;
	private Double cena;
	
	public FarmaceutDTO(Farmaceut f) {
		
		this.username = f.getUsername();
		this.password = f.getPassword();
		this.ime = f.getIme();
		this.prezime = f.getPrezime();
		this.cena = f.getCena();
		this.radnoVremeKraj = f.getRadnoVremeKraj();
		this.radnoVremePocetak = f.getRadnoVremePocetak();
		this.prosecnaOcena = f.getProsecnaOcena();
	}
	
	public Date getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(Date datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public String getEmailAdresa() {
		return emailAdresa;
	}

	public void setEmailAdresa(String emailAdresa) {
		this.emailAdresa = emailAdresa;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public Boolean getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(Boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}

	public String getBrojTelefona() {
		return brojTelefona;
	}

	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}

	public FarmaceutDTO() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public Double getCena() {
		return cena;
	}
	public void setCena(Double cena) {
		this.cena = cena;
	}
	public LocalDateTime getRadnoVremePocetak() {
		return radnoVremePocetak;
	}
	public void setRadnoVremePocetak(LocalDateTime radnoVremePocetak) {
		this.radnoVremePocetak = radnoVremePocetak;
	}
	public LocalDateTime getRadnoVremeKraj() {
		return radnoVremeKraj;
	}
	public void setRadnoVremeKraj(LocalDateTime radnoVremeKraj) {
		this.radnoVremeKraj = radnoVremeKraj;
	}
	public Double getProsecnaOcena() {
		return prosecnaOcena;
	}
	public void setProsecnaOcena(Double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}
	

}
