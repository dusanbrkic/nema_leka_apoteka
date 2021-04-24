package com.team_08.ISAproj.model;

import com.team_08.ISAproj.dto.KorisnikDTO;
import com.team_08.ISAproj.model.enums.KorisnickaRola;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "PACIJENT")
public class Pacijent extends Korisnik{
	@Column(name = "POENI")
	private Integer poeni;
	@Column(name = "BR_PENALA")
	private Integer brPenala = 0;
	
	//connections
	@OneToMany(mappedBy = "pacijent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pregled> pregledi;
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Narudzbenica> narudzbenice;
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Apoteka> pretplaceneApoteke;
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Lek> alergije;
	@OneToMany(mappedBy = "pacijent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pregled> savetovanja;

	// constructors
	public Pacijent() {super();}
	public Pacijent(int poeni, int brPenala, Set<Pregled> pregledi, Set<Narudzbenica> narudzbenice,
					Set<Apoteka> pretplaceneApoteke, Set<Lek> alergije, Set<Pregled> savetovanja) {
		super();
		this.poeni = poeni;
		this.brPenala = brPenala;
		this.pregledi = pregledi;
		this.narudzbenice = narudzbenice;
		this.pretplaceneApoteke = pretplaceneApoteke;
		this.alergije = alergije;
		this.savetovanja = savetovanja;
	}
	public Pacijent(KorisnikDTO kDTO) {
		super();
		
	}
	public void UpdatePacijent(KorisnikDTO korisnik) {
        this.setEmailAdresa(korisnik.getEmailAdresa());
        this.setUsername(korisnik.getUsername());
        this.setPassword(korisnik.getPassword());
        this.setIme(korisnik.getIme());
        this.setPrezime(korisnik.getEmailAdresa());
        this.setAdresa(korisnik.getAdresa());
        this.setDatumRodjenja(korisnik.getDatumRodjenja());
        this.setGrad(korisnik.getGrad());
        this.setDrzava(korisnik.getDrzava());
        this.setBrojTelefona(korisnik.getBrojTelefona());;
	}
	// methods
	public void pretplatiSe(Apoteka a) {
		// to do
	}
	
	public void otkaziPretplatu(Apoteka a) {
		// to do
	}
	
	
	
	// getters and setters


	public int getPoeni() {
		return poeni;
	}

	public void setPoeni(int poeni) {
		this.poeni = poeni;
	}

	public int getBrPenala() {
		return brPenala;
	}

	public void setBrPenala(int brPenala) {
		this.brPenala = brPenala;
	}

	public Set<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(Set<Pregled> pregledi) {
		this.pregledi = pregledi;
	}

	public Set<Narudzbenica> getNarudzbenice() {
		return narudzbenice;
	}
	public void setNarudzbenice(Set<Narudzbenica> narudzbenice) {
		this.narudzbenice = narudzbenice;
	}
	public Set<Apoteka> getPretplaceneApoteke() {
		return pretplaceneApoteke;
	}

	public void setPretplaceneApoteke(Set<Apoteka> pretplaceneApoteke) {
		this.pretplaceneApoteke = pretplaceneApoteke;
	}

	public Set<Lek> getAlergije() {
		return alergije;
	}

	public void setAlergije(Set<Lek> alergije) {
		this.alergije = alergije;
	}

	public Set<Pregled> getSavetovanja() {
		return savetovanja;
	}

	public void setSavetovanja(Set<Pregled> savetovanja) {
		this.savetovanja = savetovanja;
	}
}
