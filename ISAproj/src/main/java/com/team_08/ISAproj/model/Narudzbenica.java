package com.team_08.ISAproj.model;

import javax.persistence.*;
import java.sql.Date;
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
	@OneToMany(mappedBy = "narudzbenica", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<NarudzbenicaLek> lekovi;
	@ManyToOne(fetch = FetchType.EAGER)
	private Apoteka apoteka;


	public Narudzbenica(Date rokPonuda, Set<NarudzbenicaLek> lekovi) {
		super();
		this.rokPonude = rokPonuda;
		this.lekovi = lekovi;
	}

	public Narudzbenica() {

	}

	public Date getRokPonuda() {
		return rokPonude;
	}

	public void setRokPonuda(Date rokPonuda) {
		this.rokPonude = rokPonuda;
	}

	public Set<NarudzbenicaLek> getLekovi() {
		return lekovi;
	}

	public void setLekovi(Set<NarudzbenicaLek> lekovi) {
		this.lekovi = lekovi;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
