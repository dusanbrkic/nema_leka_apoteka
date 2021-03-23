package com.team_08.ISAproj.model;

import java.util.ArrayList;

public class Aplikacija {

	// connections
	private ArrayList<Korisnik> korisnici;
	private ArrayList<Apoteka> apoteke;
	private ArrayList<Lek> lekovi;
	
	public Aplikacija(ArrayList<Korisnik> korisnici, ArrayList<Apoteka> apoteke, ArrayList<Lek> lekovi) {
		super();
		this.korisnici = korisnici;
		this.apoteke = apoteke;
		this.lekovi = lekovi;
	}
	
	
	
	

	boolean registrujKorisnika(Korisnik k) {
		// to do
		return false;
	}
	
	Korisnik pronadjiKorisnika(String username) {
		// to do
		return null;
	}
	
	Lek pronadjiLek(String sifraLeka) {
		// to do
		return null;
	}
	
	ArrayList<Korisnik> pretraziKorisnika(String imeprezime){
		// to do
		return null;
	}
}
