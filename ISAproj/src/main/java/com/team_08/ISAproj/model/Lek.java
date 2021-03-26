package com.team_08.ISAproj.model;

import com.team_08.ISAproj.model.enums.OblikLeka;
import com.team_08.ISAproj.model.enums.TipLeka;

import java.util.ArrayList;

public class Lek {
	private String sifra;
	private String naziv;
	private String upuctvo;
	private TipLeka tip;
	private OblikLeka oblikLeka;
	private String sastav;
	private ArrayList<String> sifreZamenskihLekova;
	private String dodatneNapomene;
	
	// constructors
	public Lek(String sifra, String naziv, String upuctvo, TipLeka tip, OblikLeka oblikLeka, String sastav,
			ArrayList<String> sifreZamenskihLekova, String dodatneNapomene) {
		super();
		this.sifra = sifra;
		this.naziv = naziv;
		this.upuctvo = upuctvo;
		this.tip = tip;
		this.oblikLeka = oblikLeka;
		this.sastav = sastav;
		this.sifreZamenskihLekova = sifreZamenskihLekova;
		this.dodatneNapomene = dodatneNapomene;
	}

	
	// getters and setters
	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getUpuctvo() {
		return upuctvo;
	}

	public void setUpuctvo(String upuctvo) {
		this.upuctvo = upuctvo;
	}

	public TipLeka getTip() {
		return tip;
	}

	public void setTip(TipLeka tip) {
		this.tip = tip;
	}

	public OblikLeka getOblikLeka() {
		return oblikLeka;
	}

	public void setOblikLeka(OblikLeka oblikLeka) {
		this.oblikLeka = oblikLeka;
	}

	public String getSastav() {
		return sastav;
	}

	public void setSastav(String sastav) {
		this.sastav = sastav;
	}

	public ArrayList<String> getSifreZamenskihLekova() {
		return sifreZamenskihLekova;
	}

	public void setSifreZamenskihLekova(ArrayList<String> sifreZamenskihLekova) {
		this.sifreZamenskihLekova = sifreZamenskihLekova;
	}

	public String getDodatneNapomene() {
		return dodatneNapomene;
	}

	public void setDodatneNapomene(String dodatneNapomene) {
		this.dodatneNapomene = dodatneNapomene;
	}

}
