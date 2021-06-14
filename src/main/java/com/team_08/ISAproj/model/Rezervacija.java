package com.team_08.ISAproj.model;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "REZERVACIJA")
public class Rezervacija {
	@Id
	@SequenceGenerator(name="RezervacijaSeqGen", sequenceName = "RezervacijaSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RezervacijaSeqGen")
	private Long id;
	@Column(name = "ROK_PONUDE")
	private LocalDateTime rokPonude;
	@Column(name = "DATUM_PONUDE")
	private LocalDateTime datumPonude;
	
	// connections
	@OneToMany(mappedBy = "rezervacija", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<RezervacijaLek> lekovi = new HashSet<RezervacijaLek>();
	
	public void addRezervacijaLek(RezervacijaLek nl) {
		this.lekovi.add(nl);
	}
	
	public Set<RezervacijaLek> getLekovi() {
		return lekovi;
	}

	public void setLekovi(Set<RezervacijaLek> lekovi) {
		this.lekovi = lekovi;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	private Apoteka apoteka;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Pacijent pacijent;
	
	@Column(name = "PREUZETO")
	private boolean preuzeto = false;
	
	@Column(name = "ISTEKLO")
	private boolean isteklo = false;


	public boolean isIsteklo() {
		return isteklo;
	}

	public void setIsteklo(boolean isteklo) {
		this.isteklo = isteklo;
	}

	public boolean isPreuzeto() {
		return preuzeto;
	}

	public void setPreuzeto(boolean preuzeto) {
		this.preuzeto = preuzeto;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Rezervacija(LocalDateTime rokPonuda) {
		super();
		this.rokPonude = rokPonuda;
		this.lekovi = new HashSet<RezervacijaLek>();
	}

	public Rezervacija() {
		this.lekovi = new HashSet<RezervacijaLek>();
	}

	public LocalDateTime getRokPonuda() {
		return rokPonude;
	}

	public void setRokPonuda(LocalDateTime rokPonuda) {
		this.rokPonude = rokPonuda;
	}


	public LocalDateTime getRokPonude() {
		return rokPonude;
	}

	public void setRokPonude(LocalDateTime rokPonude) {
		this.rokPonude = rokPonude;
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

	public LocalDateTime getDatumPonude() {
		return datumPonude;
	}

	public void setDatumPonude(LocalDateTime datumPonude) {
		this.datumPonude = datumPonude;
	}
}