package com.team_08.ISAproj.model;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "APOTEKA_LEK")
public class ApotekaLek {

	@Id
	@SequenceGenerator(name="ApotekaLekSeqGen", sequenceName = "ApotekaLekSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ApotekaLekSeqGen")
	private Long id;
	@Column(name = "KOLICINA")
	private int kolicina;
	@Column(name = "CENA")
	private double cena;
	@Column(name = "ISTEK_VAZENJA_CENE")
	private Date istekVazenjaCene;
	@Column(name = "STARA_CENA")
	private double staraCena;
	
	// connections
	@ManyToOne(fetch = FetchType.EAGER)
	private Apoteka apoteka;
	@ManyToOne(fetch = FetchType.EAGER)
	private Lek lek;


	// constructors
	public ApotekaLek(int kolicina, double cena, Date istekVazenjaCene, double staraCena, Apoteka apoteka, Lek lek) {
		super();
		this.kolicina = kolicina;
		this.cena = cena;
		this.istekVazenjaCene = istekVazenjaCene;
		this.staraCena = staraCena;
		this.apoteka = apoteka;
		this.lek = lek;
	}

	public ApotekaLek() {

	}


	// getters and setters
	public int getKolicina() {return kolicina;}
	public void setKolicina(int kolicina) {this.kolicina = kolicina;}
	public double getCena() {return cena;}
	public void setCena(double cena) {this.cena = cena;}
	public Date getIstekVazenjaCene() {return istekVazenjaCene;}
	public void setIstekVazenjaCene(Date istekVazenjaCene) {this.istekVazenjaCene = istekVazenjaCene;}
	public double getStaraCena() {return staraCena;}
	public void setStaraCena(double staraCena) {this.staraCena = staraCena;}
	public Apoteka getApoteka() {return apoteka;}
	public void setApoteka(Apoteka apoteka) {this.apoteka = apoteka;}
	public Lek getLek() {return lek;}
	public void setLek(Lek lek) {this.lek = lek;}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
