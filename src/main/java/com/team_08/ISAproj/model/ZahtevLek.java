package com.team_08.ISAproj.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "ZAHTEV_LEK")
public class ZahtevLek {
	
    @Id
    @SequenceGenerator(name = "ZahtevLekSeqGen", sequenceName = "ZahtevLekSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ZahtevLekSeqGen")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Apoteka apoteka;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Lek lek;
    @Column(name = "KOLICINA")
	private Integer kolicina;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private ZdravstveniRadnik zdravstveniRadnik;

	
	public ZahtevLek(Apoteka apoteka, Lek lek, Integer kolicina, ZdravstveniRadnik zdravstveniRadnik) {
		super();
		this.apoteka = apoteka;
		this.lek = lek;
		this.kolicina = kolicina;
		this.zdravstveniRadnik = zdravstveniRadnik;
	}
	public ZahtevLek() {
		
	}
	public Apoteka getApoteka() {
		return apoteka;
	}

	public void setApoteka(Apoteka apoteka) {
		this.apoteka = apoteka;
	}

	public Lek getLek() {
		return lek;
	}

	public void setLek(Lek lek) {
		this.lek = lek;
	}

	public Integer getKolicina() {
		return kolicina;
	}

	public void setKolicina(Integer kolicina) {
		this.kolicina = kolicina;
	}

	public ZdravstveniRadnik getZdravstveniRadnik() {
		return zdravstveniRadnik;
	}

	public void setZdravstveniRadnik(ZdravstveniRadnik zdravstveniRadnik) {
		this.zdravstveniRadnik = zdravstveniRadnik;
	}
	
	
	
	
	
	
	
	
}
