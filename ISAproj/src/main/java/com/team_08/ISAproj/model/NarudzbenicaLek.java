package com.team_08.ISAproj.model;

public class NarudzbenicaLek {
	
	private int kolicina;
	
	//connections
	private Narudzbenica narudzbenica;
	private Lek lek;
	
	
	public NarudzbenicaLek(int kolicina, Narudzbenica narudzbenica, Lek lek) {
		super();
		this.kolicina = kolicina;
		this.narudzbenica = narudzbenica;
		this.lek = lek;
	}
	public int getKolicina() {
		return kolicina;
	}
	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}
	public Narudzbenica getNarudzbenica() {
		return narudzbenica;
	}
	public void setNarudzbenica(Narudzbenica narudzbenica) {
		this.narudzbenica = narudzbenica;
	}
	public Lek getLek() {
		return lek;
	}
	public void setLek(Lek lek) {
		this.lek = lek;
	}
	
	
	
}
