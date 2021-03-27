package com.team_08.ISAproj.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "FARMACEUT_APOTEKA")
public class FarmaceutApoteka {
	@Id
	@SequenceGenerator(name="FarmaceutApotekaSeqGen", sequenceName = "FarmaceutApotekaSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FarmaceutApotekaSeqGen")
	private Long id;

	@ElementCollection
	private Set<Date> slobodniTermini;
	@Column(name = "CENA")
	private double cena;
	@Column(name = "RADNO_VREME_POCETAK")
	private Date radnoVremePocetak;
	@Column(name = "RADNO_VREME_KRAJ")
	private Date radnoVremeKraj;

	//connections
	@OneToOne(fetch = FetchType.EAGER)
	private Farmaceut farmaceut;
	@ManyToOne(fetch = FetchType.EAGER)
	private Apoteka apoteka;


	public Farmaceut getFarmaceut() {
		return farmaceut;
	}
	public FarmaceutApoteka() {
	}
	public FarmaceutApoteka(Farmaceut farmaceut, Apoteka apoteka, Set<Date> slobodniTermini, double cena,
			Date radnoVremePocetak, Date radnoVremeKraj) {
		super();
		this.farmaceut = farmaceut;
		this.apoteka = apoteka;
		this.slobodniTermini = slobodniTermini;
		this.cena = cena;
		this.radnoVremePocetak = radnoVremePocetak;
		this.radnoVremeKraj = radnoVremeKraj;
	}
	public void setFarmaceut(Farmaceut farmaceut) {
		this.farmaceut = farmaceut;
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
