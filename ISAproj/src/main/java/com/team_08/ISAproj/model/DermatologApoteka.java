package com.team_08.ISAproj.model;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
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
	private LocalDateTime radnoVremePocetak;
	@Column(name = "RADNO_VREME_KRAJ")
	private LocalDateTime radnoVremeKraj;

	@ElementCollection
	private Set<Date> slobodniTermini;

	//connections
	@ManyToOne(fetch = FetchType.EAGER)
	private Dermatolog dermatolog;
	@ManyToOne(fetch = FetchType.EAGER)
	private Apoteka apoteka;


	public DermatologApoteka(Dermatolog dermatolog, Apoteka apoteka, Set<Date> slobodniTermini, double cena,
			LocalDateTime radnoVremePocetak, LocalDateTime radnoVremeKraj) {
		super();
		this.dermatolog = dermatolog;
		this.apoteka = apoteka;
		this.slobodniTermini = slobodniTermini;
		this.cena = cena;
		this.radnoVremePocetak = radnoVremePocetak;
		this.radnoVremeKraj = radnoVremeKraj;
	}
	public DermatologApoteka(Dermatolog dermatolog, Apoteka apoteka, double cena,
			LocalDateTime radnoVremePocetak, LocalDateTime radnoVremeKraj) {
		super();
		this.dermatolog = dermatolog;
		this.apoteka = apoteka;
		this.slobodniTermini = new HashSet<Date>();
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

	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
}
