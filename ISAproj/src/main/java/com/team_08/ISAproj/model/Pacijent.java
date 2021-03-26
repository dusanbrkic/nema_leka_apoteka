package com.team_08.ISAproj.model;

import com.team_08.ISAproj.model.enums.KorisnickaRola;

import java.util.ArrayList;
import java.util.Date;

public class Pacijent extends Korisnik{
	
	private int poeni;
	private int brPenala = 0;
	
	//connections
	private ArrayList<Pregled> pregledi;
	private ArrayList<Lek> rezervisaniLekovi;
	private ArrayList<Apoteka> pretplaceneApoteke;
	private ArrayList<Lek> alergije;
	private ArrayList<Savetovanje> savetovanja;

	// constructors
	public Pacijent() {super();}
	public Pacijent(int poeni, int brPenala, ArrayList<Pregled> pregledi, ArrayList<Lek> rezervisaniLekovi,
			ArrayList<Apoteka> pretplaceneApoteke, ArrayList<Lek> alergije, ArrayList<Savetovanje> savetovanja) {
		super();
		this.poeni = poeni;
		this.brPenala = brPenala;
		this.pregledi = pregledi;
		this.rezervisaniLekovi = rezervisaniLekovi;
		this.pretplaceneApoteke = pretplaceneApoteke;
		this.alergije = alergije;
		this.savetovanja = savetovanja;
	}
	public Pacijent(String username, String password, String ime, String prezime, Date datumRodjenja,
			String emailAdresa) {
		super(username, password, ime, prezime, datumRodjenja, emailAdresa, KorisnickaRola.PACIJENT);
	}

	
	// methods
	public void pretplatiSe(Apoteka a) {
		// to do
	}
	
	public void otkaziPretplatu(Apoteka a) {
		// to do
	}
	
	
	
	// getters and setters
	public int 	getPoeni() {return poeni;}
	public void setPoeni(int poeni) {this.poeni = poeni;}
	public int 	getBrPenala() {return brPenala;}
	public void setBrPenala(int brPenala) {this.brPenala = brPenala;}
	public ArrayList<Pregled> getPregledi() {return pregledi;}
	public void setPregledi(ArrayList<Pregled> pregledi) {this.pregledi = pregledi;}
	public ArrayList<Lek> getRezervisaniLekovi() {return rezervisaniLekovi;}
	public void setRezervisaniLekovi(ArrayList<Lek> rezervisaniLekovi) {this.rezervisaniLekovi = rezervisaniLekovi;}
	public ArrayList<Apoteka> getPretplaceneApoteke() {return pretplaceneApoteke;}
	public void setPretplaceneApoteke(ArrayList<Apoteka> pretplaceneApoteke) {this.pretplaceneApoteke = pretplaceneApoteke;}
	public ArrayList<Lek> getAlergije() {return alergije;}
	public void setAlergije(ArrayList<Lek> alergije) {this.alergije = alergije;}
	public ArrayList<Savetovanje> getSavetovanja() {return savetovanja;}
	public void setSavetovanja(ArrayList<Savetovanje> savetovanja) {this.savetovanja = savetovanja;}
	
}
