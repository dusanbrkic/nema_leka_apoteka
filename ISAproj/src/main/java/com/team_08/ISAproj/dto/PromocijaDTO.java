package com.team_08.ISAproj.dto;

import java.sql.Date;

import javax.persistence.Column;

import com.team_08.ISAproj.model.Apoteka;

public class PromocijaDTO {
	
	private String apotekaId;
	private Date pocetakVazenja;
	private Date krajVazenja;
    private Double cena;
    private Date istekVazenjaCene;
    private Double staraCena;
	private String cookie;
	private String tekstPromocije;
	private String sifra;
	private String naziv;
	public PromocijaDTO() {
		
	}

	public String getSifra() {
		return sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getTekstPromocije() {
		return tekstPromocije;
	}

	public void setTekstPromocije(String tekstPromocije) {
		this.tekstPromocije = tekstPromocije;
	}

	public String getApotekaId() {
		return apotekaId;
	}

	public void setApotekaId(String apotekaId) {
		this.apotekaId = apotekaId;
	}

	public Date getPocetakVazenja() {
		return pocetakVazenja;
	}

	public void setPocetakVazenja(Date pocetakVazenja) {
		this.pocetakVazenja = pocetakVazenja;
	}

	public Date getKrajVazenja() {
		return krajVazenja;
	}

	public void setKrajVazenja(Date krajVazenja) {
		this.krajVazenja = krajVazenja;
	}

	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	public Date getIstekVazenjaCene() {
		return istekVazenjaCene;
	}

	public void setIstekVazenjaCene(Date istekVazenjaCene) {
		this.istekVazenjaCene = istekVazenjaCene;
	}

	public Double getStaraCena() {
		return staraCena;
	}

	public void setStaraCena(Double staraCena) {
		this.staraCena = staraCena;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	
	
}
