package com.team_08.ISAproj.dto;

import javax.persistence.Column;

import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.enums.OblikLeka;
import com.team_08.ISAproj.model.enums.TipLeka;

public class LekDTO {
	private String sifra;
	private String naziv;
	private String uputstvo;
	private TipLeka tip;
	private OblikLeka oblikLeka;
	private String sastav;
	private String dodatneNapomene;
	
	public LekDTO() {
		
	}
	public LekDTO(Lek lek) {
		this.sifra = lek.getSifra();
		this.naziv = lek.getNaziv();
		this.uputstvo = lek.getUpuctvo();
		this.tip = lek.getTip();
		this.oblikLeka = lek.getOblikLeka();
		this.sastav = lek.getSastav();
		this.dodatneNapomene = lek.getDodatneNapomene();
	}
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
	public String getUputstvo() {
		return uputstvo;
	}
	public void setUputstvo(String uputstvo) {
		this.uputstvo = uputstvo;
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
	public String getDodatneNapomene() {
		return dodatneNapomene;
	}
	public void setDodatneNapomene(String dodatneNapomene) {
		this.dodatneNapomene = dodatneNapomene;
	}
}
