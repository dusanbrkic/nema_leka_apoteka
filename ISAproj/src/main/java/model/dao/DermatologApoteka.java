package model.dao;

import java.util.ArrayList;
import java.util.Date;

public class DermatologApoteka {

	
	private Dermatolog dermatolog;
	private Apoteka apoteka;
	private ArrayList<Date> slobodniTermini;
	private double cena;
	private Date radnoVremePocetak;
	private Date radnoVremeKraj;
	
	
	public DermatologApoteka(Dermatolog dermatolog, Apoteka apoteka, ArrayList<Date> slobodniTermini, double cena,
			Date radnoVremePocetak, Date radnoVremeKraj) {
		super();
		this.dermatolog = dermatolog;
		this.apoteka = apoteka;
		this.slobodniTermini = slobodniTermini;
		this.cena = cena;
		this.radnoVremePocetak = radnoVremePocetak;
		this.radnoVremeKraj = radnoVremeKraj;
	}
	
	public Dermatolog getDermatolog() {
		return dermatolog;
	}
	public void setDermatolog(Dermatolog dermatolog) {
		this.dermatolog = dermatolog;
	}
	public Apoteka getApoteka() {
		return apoteka;
	}
	public void setApoteka(Apoteka apoteka) {
		this.apoteka = apoteka;
	}
	public ArrayList<Date> getSlobodniTermini() {
		return slobodniTermini;
	}
	public void setSlobodniTermini(ArrayList<Date> slobodniTermini) {
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

}
