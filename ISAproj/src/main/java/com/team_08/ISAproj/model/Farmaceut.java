package com.team_08.ISAproj.model;

import java.util.ArrayList;

public class Farmaceut extends Korisnik {

	private double prosecnaOcena;
	
	// connections
	private FarmaceutApoteka apoteke;
	private ArrayList<Pacijent> pregledaniPacijenti;
	private ArrayList<Savetovanje> savetovanja;
	

	// constructors
	public Farmaceut(double prosecnaOcena) {
		super();
		this.prosecnaOcena = prosecnaOcena;
	}
	
	
	// getters and setters
	public double getProsecnaOcena() {return prosecnaOcena;}
	public void setProsecnaOcena(double prosecnaOcena) {this.prosecnaOcena = prosecnaOcena;}
}
