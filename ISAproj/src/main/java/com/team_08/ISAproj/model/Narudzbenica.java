package com.team_08.ISAproj.model;

import javax.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity(name = "NARUDZBENICA")
public class Narudzbenica {
	@Id
	@SequenceGenerator(name="NarudzbenicaSeqGen", sequenceName = "NarudzbenicaSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NarudzbenicaSeqGen")
	private Long id;
	@Column(name = "ROK_PONUDE")
	private Date rokPonude;
	
	// connections
	//@OneToMany(mappedBy = "narudzbenica", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//private Set<NarudzbenicaLek> lekovi;
	
	@Column(name = "KOLICINA")
	private Integer kolicina;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Lek lek;
	
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Apoteka apoteka;


	public Narudzbenica(Date rokPonuda, Lek lek, int kolicina) {
		super();
		this.rokPonude = rokPonuda;
		this.lek = lek;
		this.kolicina = kolicina;
	}

	public Narudzbenica() {

	}

	public Date getRokPonuda() {
		return rokPonude;
	}

	public void setRokPonuda(Date rokPonuda) {
		this.rokPonude = rokPonuda;
	}


	public Date getRokPonude() {
		return rokPonude;
	}

	public void setRokPonude(Date rokPonude) {
		this.rokPonude = rokPonude;
	}

	public Integer getKolicina() {
		return kolicina;
	}

	public void setKolicina(Integer kolicina) {
		this.kolicina = kolicina;
	}

	public Lek getLek() {
		return lek;
	}

	public void setLek(Lek lek) {
		this.lek = lek;
	}

	public Apoteka getApoteka() {
		return apoteka;
	}

	public void setApoteka(Apoteka apoteka) {
		this.apoteka = apoteka;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
