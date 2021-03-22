package model.dao;

import java.util.ArrayList;
import java.util.Date;

public class FarmaceutApoteka {

	private Farmaceut farmaceut;
	private Apoteka apoteka;
	private ArrayList<Date> slobodniTermini;
	private double cena;
	private Date radnoVremePocetak;
	private Date radnoVremeKraj;
	public Farmaceut getFarmaceut() {
		return farmaceut;
	}
	public FarmaceutApoteka() {
	}
	public FarmaceutApoteka(Farmaceut farmaceut, Apoteka apoteka, ArrayList<Date> slobodniTermini, double cena,
			Date radnoVremePocetak, Date radnoVremeKraj) {
		super();
		this.farmaceut = farmaceut;
		this.apoteka = apoteka;
		this.slobodniTermini = slobodniTermini;
		this.cena = cena;
		this.radnoVremePocetak = radnoVremePocetak;
		this.radnoVremeKraj = radnoVremeKraj;
	}
	public void setFarmaceut(Farmaceut farmaceut) {
		this.farmaceut = farmaceut;
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
