package com.team_08.ISAproj.model;

import com.team_08.ISAproj.model.enums.KorisnickaRola;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "PACIJENT")
public class Pacijent extends Korisnik{
	@Column(name = "POENI")
	private int poeni;
	@Column(name = "BR_PENALA")
	private int brPenala = 0;
	
	//connections
	@OneToMany(mappedBy = "pacijent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pregled> pregledi;
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Lek> rezervisaniLekovi;
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Apoteka> pretplaceneApoteke;
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Lek> alergije;
	@OneToMany(mappedBy = "pacijent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Savetovanje> savetovanja;

	// constructors
	public Pacijent() {super();}
	public Pacijent(int poeni, int brPenala, Set<Pregled> pregledi, Set<Lek> rezervisaniLekovi,
					Set<Apoteka> pretplaceneApoteke, Set<Lek> alergije, Set<Savetovanje> savetovanja) {
		super();
		this.poeni = poeni;
		this.brPenala = brPenala;
		this.pregledi = pregledi;
		this.rezervisaniLekovi = rezervisaniLekovi;
		this.pretplaceneApoteke = pretplaceneApoteke;
		this.alergije = alergije;
		this.savetovanja = savetovanja;
	}
	public Pacijent(String username, String password, String ime, String prezime, Date datumRodjenja,
			String emailAdresa) {
		super(username, password, ime, prezime, datumRodjenja, emailAdresa);
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

	public Set<Lek> getRezervisaniLekovi() {
		return rezervisaniLekovi;
	}

	public void setRezervisaniLekovi(Set<Lek> rezervisaniLekovi) {
		this.rezervisaniLekovi = rezervisaniLekovi;
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

	public Set<Savetovanje> getSavetovanja() {
		return savetovanja;
	}

	public void setSavetovanja(Set<Savetovanje> savetovanja) {
		this.savetovanja = savetovanja;
	}
}
