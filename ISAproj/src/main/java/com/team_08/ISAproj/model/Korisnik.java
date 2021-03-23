package com.team_08.ISAproj.model;

import java.util.Date;

public class Korisnik {

	
	private String username;
	private String password;
	private String ime;
	private String prezime;
	private Date datumRodjenja;
	private String emailAdresa;
	
	
	// constructors
	public Korisnik() {
		super();
	}
	public Korisnik(String username, String password, String ime, String prezime, Date datumRodjenja,
			String emailAdresa) {
		super();
		this.username = username;
		this.password = password;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.emailAdresa = emailAdresa;
	}
	
	
	// getters and setters
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	public String getIme() {return ime;}
	public void setIme(String ime) {this.ime = ime;}
	public String getPrezime() {return prezime;}
	public void setPrezime(String prezime) {this.prezime = prezime;}
	public Date getDatumRodjenja() {return datumRodjenja;}
	public void setDatumRodjenja(Date datumRodjenja) {this.datumRodjenja = datumRodjenja;}
	public String getEmailAdresa() {return emailAdresa;}
	public void setEmailAdresa(String emailAdresa) {this.emailAdresa = emailAdresa;}
	
}
