package com.team_08.ISAproj.model;

import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;

public class Savetovanje {
	
	private Date vreme;
	private Duration trajanje;
	private double cena;
	private Date kraj;
	private String dijagnoza;
	private boolean savetovanjeObavljeno;
	
	// connections
	private Apoteka apoteka;
	private Farmaceut farmaceut;
	private Pacijent pacijent;
	private ArrayList<Lek> preporuceniLekovi;
	
	// constructors
	public Savetovanje(Date vreme, Duration trajanje, double cena, Date kraj, String dijagnoza,
			boolean savetovanjeObavljeno, Apoteka apoteka, Farmaceut farmaceut, Pacijent pacijent,
			ArrayList<Lek> preporuceniLekovi) {
		super();
		this.vreme = vreme;
		this.trajanje = trajanje;
		this.cena = cena;
		this.kraj = kraj;
		this.dijagnoza = dijagnoza;
		this.savetovanjeObavljeno = savetovanjeObavljeno;
		this.apoteka = apoteka;
		this.farmaceut = farmaceut;
		this.pacijent = pacijent;
		this.preporuceniLekovi = preporuceniLekovi;
	}

	
	
	// getters and setters
	public Date getVreme() {return vreme;}
	public void setVreme(Date vreme) {this.vreme = vreme;}
	public Duration getTrajanje() {return trajanje;}
	public void setTrajanje(Duration trajanje) {this.trajanje = trajanje;}
	public double getCena() {return cena;}
	public void setCena(double cena) {this.cena = cena;}
	public Date getKraj() {return kraj;}
	public void setKraj(Date kraj) {this.kraj = kraj;}
	public String getDijagnoza() {return dijagnoza;}
	public void setDijagnoza(String dijagnoza) {this.dijagnoza = dijagnoza;}
	public boolean isSavetovanjeObavljeno() {return savetovanjeObavljeno;}
	public void setSavetovanjeObavljeno(boolean savetovanjeObavljeno) {this.savetovanjeObavljeno = savetovanjeObavljeno;}
	public Apoteka getApoteka() {return apoteka;}
	public void setApoteka(Apoteka apoteka) {this.apoteka = apoteka;}
	public Farmaceut getFarmaceut() {return farmaceut;}
	public void setFarmaceut(Farmaceut farmaceut) {this.farmaceut = farmaceut;}
	public Pacijent getPacijent() {return pacijent;}
	public void setPacijent(Pacijent pacijent) {this.pacijent = pacijent;}
	public ArrayList<Lek> getPreporuceniLekovi() {return preporuceniLekovi;}
	public void setPreporuceniLekovi(ArrayList<Lek> preporuceniLekovi) {this.preporuceniLekovi = preporuceniLekovi;}
	
}
