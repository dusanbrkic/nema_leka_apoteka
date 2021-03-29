package com.team_08.ISAproj.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.team_08.ISAproj.model.Apoteka;

public class ApotekaDTO {

	private Long id;
	private String naziv;
	private String adresa;
	private Double prosecnaOcena;
	private String opis;
	
	public ApotekaDTO() {
	
		
	}
	
	public ApotekaDTO(Long id, String naziv, String adresa, Double prosecnaOcena, String opis) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.prosecnaOcena = prosecnaOcena;
		this.opis = opis;
	}
	public ApotekaDTO(Apoteka a) {
		this.id = a.getId();
		this.naziv = a.getNaziv();
		this.adresa = a.getAdresa();
		this.prosecnaOcena = a.getProsecnaOcena();
		this.opis = a.getOpis();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public Double getProsecnaOcena() {
		return prosecnaOcena;
	}
	public void setProsecnaOcena(Double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	
}
