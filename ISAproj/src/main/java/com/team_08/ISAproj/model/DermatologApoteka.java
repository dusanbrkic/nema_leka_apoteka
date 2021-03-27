package com.team_08.ISAproj.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "DERMATOLOG_APOTEKA")
public class DermatologApoteka {
	@Id
	@SequenceGenerator(name="DermatologApotekaSeqGen", sequenceName = "DermatologApotekaSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DermatologApotekaSeqGen")
	private Long id;

	@Column(name = "CENA")
	private Double cena;
	@Column(name = "RADNO_VREME_POCETAK")
	private Date radnoVremePocetak;
	@Column(name = "RADNO_VREME_KRAJ")
	private Date radnoVremeKraj;

	@ElementCollection
	private Set<Date> slobodniTermini;

	//connections
	@ManyToOne(fetch = FetchType.EAGER)
	private Dermatolog dermatolog;
	@ManyToOne(fetch = FetchType.EAGER)
	private Apoteka apoteka;


	public DermatologApoteka(Dermatolog dermatolog, Apoteka apoteka, Set<Date> slobodniTermini, double cena,
			Date radnoVremePocetak, Date radnoVremeKraj) {
		super();
		this.dermatolog = dermatolog;
		this.apoteka = apoteka;
		this.slobodniTermini = slobodniTermini;
		this.cena = cena;
		this.radnoVremePocetak = radnoVremePocetak;
		this.radnoVremeKraj = radnoVremeKraj;
	}

	public DermatologApoteka() {

	}

	public Dermatolog getDermatolog() {
		return dermatolog;
	}
	public void setDermatolog(Dermatolog dermatolog) {
		this.dermatolog = dermatolog;
	}
	public Apoteka getApoteka() {
		return apoteka;
	}
	public void setApoteka(Apoteka apoteka) {
		this.apoteka = apoteka;
	}
	public Set<Date> getSlobodniTermini() {
		return slobodniTermini;
	}
	public void setSlobodniTermini(Set<Date> slobodniTermini) {
		this.slobodniTermini = slobodniTermini;
	}
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public Date getRadnoVremePocetak() {
		return radnoVremePocetak;
	}
	public void setRadnoVremePocetak(Date radnoVremePocetak) {
		this.radnoVremePocetak = radnoVremePocetak;
	}
	public Date getRadnoVremeKraj() {
		return radnoVremeKraj;
	}
	public void setRadnoVremeKraj(Date radnoVremeKraj) {
		this.radnoVremeKraj = radnoVremeKraj;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
}
