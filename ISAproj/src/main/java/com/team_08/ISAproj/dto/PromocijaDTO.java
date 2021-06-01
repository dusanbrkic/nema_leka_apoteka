package com.team_08.ISAproj.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;

import com.team_08.ISAproj.model.Apoteka;

public class PromocijaDTO {
	
	private String apotekaId;
	private LocalDateTime pocetakVazenja;
	private LocalDateTime krajVazenja;
    private Double cena;
    private LocalDateTime istekVazenjaCene;
    private Double promotivnaCena;
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

	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}



	public LocalDateTime getPocetakVazenja() {
		return pocetakVazenja;
	}

	public void setPocetakVazenja(LocalDateTime pocetakVazenja) {
		this.pocetakVazenja = pocetakVazenja;
	}

	public LocalDateTime getKrajVazenja() {
		return krajVazenja;
	}

	public void setKrajVazenja(LocalDateTime krajVazenja) {
		this.krajVazenja = krajVazenja;
	}

	public LocalDateTime getIstekVazenjaCene() {
		return istekVazenjaCene;
	}

	public void setIstekVazenjaCene(LocalDateTime istekVazenjaCene) {
		this.istekVazenjaCene = istekVazenjaCene;
	}

	public Double getPromotivnaCena() {
		return promotivnaCena;
	}

	public void setPromotivnaCena(Double promotivnaCena) {
		this.promotivnaCena = promotivnaCena;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	
	
}
