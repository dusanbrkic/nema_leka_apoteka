package model.dao;

import java.util.ArrayList;

public class Dermatolog extends Korisnik {

	
	private double prosecnaOcena;
	private DermatologApoteka apoteke;
	
	
	public Dermatolog(double prosecnaOcena, DermatologApoteka apoteke) {
		super();
		this.prosecnaOcena = prosecnaOcena;
		this.apoteke = apoteke;
	}

	public double getProsecnaOcena() {
		return prosecnaOcena;
	}

	public void setProsecnaOcena(double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}

	public DermatologApoteka getApoteke() {
		return apoteke;
	}

	public void setApoteke(DermatologApoteka apoteke) {
		this.apoteke = apoteke;
	}
}
