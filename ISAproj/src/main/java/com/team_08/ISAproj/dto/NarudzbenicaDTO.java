package com.team_08.ISAproj.dto;

import java.util.Date;

public class NarudzbenicaDTO {
	
	
	private String naziv;
	private String sifra;
	private int kolicina;
	private Date datumNarudzbine;
	private String apotekaId;
	private String pacijent;
	
	public NarudzbenicaDTO() {
		
		
	}

	public NarudzbenicaDTO(String naziv, String sifra, int kolicina, Date datumNarudzbine, String aID) {
		super();
		this.naziv = naziv;
		this.sifra = sifra;
		this.kolicina = kolicina;
		this.datumNarudzbine = datumNarudzbine;
		this.apotekaId = aID;
	}

	public String getApotekaId() {
		return apotekaId;
	}

	public void setApotekaId(String apotekaId) {
		this.apotekaId = apotekaId;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}

	public Date getDatumNarudzbine() {
		return datumNarudzbine;
	}

	public void setDatumNarudzbine(Date datumNarudzbine) {
		this.datumNarudzbine = datumNarudzbine;
	}

	public String getPacijent() {
		return pacijent;
	}

	public void setPacijent(String korisnik) {
		this.pacijent = korisnik;
	}
}
