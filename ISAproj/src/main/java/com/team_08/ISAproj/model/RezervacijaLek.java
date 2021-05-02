package com.team_08.ISAproj.model;

import javax.persistence.*;

@Entity(name = "REZERVACIJA_LEK")
public class RezervacijaLek {
	@Id
	@SequenceGenerator(name="RezervacijaLekSeqGen", sequenceName = "RezervacijaLekSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RezervacijaLekSeqGen")
	private Long id;
	@Column(name = "KOLICINA")
	private Integer kolicina;
	
	//connections

	@ManyToOne(fetch = FetchType.EAGER)
	private Lek lek;

	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
	private Rezervacija rezervacija;
	
	public RezervacijaLek(int kolicina, Rezervacija n, Lek lek) {
		super();
		this.kolicina = kolicina;
		this.lek = lek;
		this.rezervacija = n;
	}
	public RezervacijaLek(int kolicina, Lek lek) {
		this.kolicina = kolicina;
		this.lek = lek;
	}
	public RezervacijaLek() {

	}

	public int getKolicina() {
		return kolicina;
	}
	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}

	public Lek getLek() {
		return lek;
	}
	public void setLek(Lek lek) {
		this.lek = lek;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	public Rezervacija getRezervacija() {
		return rezervacija;
	}
	public void setRezervacija(Rezervacija rezervacija) {
		this.rezervacija = rezervacija;
	}
	public void setKolicina(Integer kolicina) {
		this.kolicina = kolicina;
	}
}
