package com.team_08.ISAproj.model;

import java.sql.Date;
import java.util.ArrayList;

public class Narudzbenica {

	private Date rokPonuda;
	
	// connections
	private ArrayList<NarudzbenicaLek> lekovi;

	public Narudzbenica(Date rokPonuda, ArrayList<NarudzbenicaLek> lekovi) {
		super();
		this.rokPonuda = rokPonuda;
		this.lekovi = lekovi;
	}

	public Date getRokPonuda() {
		return rokPonuda;
	}

	public void setRokPonuda(Date rokPonuda) {
		this.rokPonuda = rokPonuda;
	}

	public ArrayList<NarudzbenicaLek> getLekovi() {
		return lekovi;
	}

	public void setLekovi(ArrayList<NarudzbenicaLek> lekovi) {
		this.lekovi = lekovi;
	}
	
	
}
