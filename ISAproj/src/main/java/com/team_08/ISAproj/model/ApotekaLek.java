package com.team_08.ISAproj.model;

import java.sql.Date;

public class ApotekaLek {

	private int kolicina;
	private double cena;
	private Date istekVazenjaCene;
	private double staraCena;
	
	// connections
	private Apoteka apoteka;
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
	
}
