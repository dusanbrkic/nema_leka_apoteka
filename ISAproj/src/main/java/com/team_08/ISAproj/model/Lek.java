package com.team_08.ISAproj.model;

import com.team_08.ISAproj.dto.LekDTO;
import com.team_08.ISAproj.model.enums.OblikLeka;
import com.team_08.ISAproj.model.enums.TipLeka;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "LEK")
public class Lek {
	@Id
	@SequenceGenerator(name="LekSeqGen", sequenceName = "LekSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LekSeqGen")
	private Long id;
	@Column(name = "SIFRA", nullable = false, unique = true)
	private String sifra;
	@Column(name = "NAZIV")
	private String naziv;
	@Column(name = "UPUTSTVO")
	private String uputstvo;
	@Column(name = "TIP_LEKA")
	private TipLeka tip;
	@Column(name = "OBLIK_LEKA")
	private OblikLeka oblikLeka;
	@Column(name = "SASTAV")
	private String sastav;
	@Column(name = "DODATNE_NAPOMENE")
	private String dodatneNapomene;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Lek> zamenskiLekovi;

	
	// constructors
	public Lek(String sifra, String naziv, String upustvo, TipLeka tip, OblikLeka oblikLeka, String sastav,
			Set<Lek> sifreZamenskihLekova, String dodatneNapomene) {
		super();
		this.sifra = sifra;
		this.naziv = naziv;
		this.uputstvo = upustvo;
		this.tip = tip;
		this.oblikLeka = oblikLeka;
		this.sastav = sastav;
		this.zamenskiLekovi = sifreZamenskihLekova;
		this.dodatneNapomene = dodatneNapomene;
	}

	public Lek() {
		this.zamenskiLekovi = new HashSet<Lek>();
	}

	public Lek(LekDTO lek) {
		this.sifra = lek.getSifra();
		this.naziv = lek.getNaziv();
		this.uputstvo = lek.getUputstvo();
		this.tip = lek.getTip();
		this.oblikLeka = lek.getOblikLeka();
		this.sastav = lek.getSastav();
		this.dodatneNapomene = lek.getDodatneNapomene();
		this.zamenskiLekovi = new HashSet<Lek>();
	}
	// getters and setters
	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getUpustvo() {
		return uputstvo;
	}

	public void setUpuctvo(String upuctvo) {
		this.uputstvo = upuctvo;
	}

	public TipLeka getTip() {
		return tip;
	}

	public void setTip(TipLeka tip) {
		this.tip = tip;
	}

	public OblikLeka getOblikLeka() {
		return oblikLeka;
	}

	public void setOblikLeka(OblikLeka oblikLeka) {
		this.oblikLeka = oblikLeka;
	}

	public String getSastav() {
		return sastav;
	}

	public void setSastav(String sastav) {
		this.sastav = sastav;
	}


	public String getUputstvo() {
		return uputstvo;
	}

	public void setUputstvo(String uputstvo) {
		this.uputstvo = uputstvo;
	}

	public Set<Lek> getZamenskiLekovi() {
		return zamenskiLekovi;
	}

	public void setZamenskiLekovi(Set<Lek> zamenskiLekovi) {
		this.zamenskiLekovi = zamenskiLekovi;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDodatneNapomene() {
		return dodatneNapomene;
	}

	public void setDodatneNapomene(String dodatneNapomene) {
		this.dodatneNapomene = dodatneNapomene;
	}

}
