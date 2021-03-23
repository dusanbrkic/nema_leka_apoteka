package com.team_08.ISAproj.model;

import java.util.ArrayList;


public class Apoteka {
	
	private Long id;
	private String naziv;
	private String adresa;
	private double prosecnaOcena;
	private String opis;
	
	// connections
	private ArrayList<DermatologApoteka> dermatolozi;
	private ArrayList<FarmaceutApoteka> farmaceuti;
	private ArrayList<Pregled> pregledi;
	private ArrayList<Savetovanje> savetovanja;
	private ArrayList<Pacijent> pretplaceniKorisnici;
	private ArrayList<ApotekaLek> apotekaLek;
	private ArrayList<Narudzbenica> narudzbenice;
	private ArrayList<Promocija> promocije;
	private ArrayList<AdminApoteke> admini;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public double getProsecnaOcena() {
		return prosecnaOcena;
	}
	public void setProsecnaOcena(double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public ArrayList<DermatologApoteka> getDermatolozi() {
		return dermatolozi;
	}
	public void setDermatolozi(ArrayList<DermatologApoteka> dermatolozi) {
		this.dermatolozi = dermatolozi;
	}
	public ArrayList<FarmaceutApoteka> getFarmaceuti() {
		return farmaceuti;
	}
	public void setFarmaceuti(ArrayList<FarmaceutApoteka> farmaceuti) {
		this.farmaceuti = farmaceuti;
	}
	public Apoteka(String naziv, String adresa, double prosecnaOcena, String opis,
			ArrayList<DermatologApoteka> dermatolozi, ArrayList<FarmaceutApoteka> farmaceuti) {
		super();
		this.naziv = naziv;
		this.adresa = adresa;
		this.prosecnaOcena = prosecnaOcena;
		this.opis = opis;
		this.dermatolozi = dermatolozi;
		this.farmaceuti = farmaceuti;
	}
	public Apoteka() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Apoteka [id=" + id + ", naziv=" + naziv + ", adresa=" + adresa + ", prosecnaOcena=" + prosecnaOcena
				+ ", opis=" + opis + ", dermatolozi=" + dermatolozi + ", farmaceuti=" + farmaceuti + "]";
	}
	
}
