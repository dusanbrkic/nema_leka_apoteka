package com.team_08.ISAproj.model;

import java.sql.Date;

public class Promocija {

	private Date krajVazenja;
	private Date pocetakVazenja;
	public Promocija(Date krajVazenja, Date pocetakVazenja) {
		super();
		this.krajVazenja = krajVazenja;
		this.pocetakVazenja = pocetakVazenja;
	}
	public Date getKrajVazenja() {
		return krajVazenja;
	}
	public void setKrajVazenja(Date krajVazenja) {
		this.krajVazenja = krajVazenja;
	}
	public Date getPocetakVazenja() {
		return pocetakVazenja;
	}
	public void setPocetakVazenja(Date pocetakVazenja) {
		this.pocetakVazenja = pocetakVazenja;
	}
	
	
}
