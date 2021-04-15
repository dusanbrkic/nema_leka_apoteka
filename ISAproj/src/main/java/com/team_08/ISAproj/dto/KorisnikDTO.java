package com.team_08.ISAproj.dto;

import java.util.Date;

import com.team_08.ISAproj.model.Korisnik;

public class KorisnikDTO {

	private String username;
	private String password;
	private String ime;
	private String prezime;
	private Date datumRodjenja;
	private String emailAdresa;
	private String cookie;
	private Boolean firstLogin;
	public KorisnikDTO() {
		
	}
	public KorisnikDTO(Korisnik k) {
		super();
		this.username = k.getUsername();
		this.password = k.getPassword();
		this.ime = k.getIme();
		this.prezime = k.getPrezime();
		this.datumRodjenja = k.getDatumRodjenja();
		this.emailAdresa = k.getEmailAdresa();
		this.cookie = k.getCookieToken();
		this.firstLogin = k.getFirstLogin();
	}
	public KorisnikDTO(String username, String password, String ime, String prezime, Date datumRodjenja,
			String emailAdresa, String cookie) {
		super();
		this.username = username;
		this.password = password;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.emailAdresa = emailAdresa;
		this.cookie = cookie;
	}
	
	public Boolean getFirstLogin() {
		return firstLogin;
	}
	public void setFirstLogin(Boolean firstLogin) {
		this.firstLogin = firstLogin;
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

}
