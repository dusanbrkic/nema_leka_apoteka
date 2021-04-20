package com.team_08.ISAproj.model;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "FARMACEUT")
public class Farmaceut extends Korisnik {
	@Column(name = "PROSECNA_OCENA")
	private Double prosecnaOcena;
	
	// connections
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Odsustvo> odsustva;
	@OneToOne(mappedBy = "farmaceut", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private FarmaceutApoteka apoteka;
	@OneToMany(mappedBy = "farmaceut", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Savetovanje> savetovanja;
	

	// constructors
	public Farmaceut(double prosecnaOcena) {
		super();
		this.prosecnaOcena = prosecnaOcena;
	}

	public Farmaceut() {

	}


	// getters and setters
	public double getProsecnaOcena() {return prosecnaOcena;}
	public void setProsecnaOcena(double prosecnaOcena) {this.prosecnaOcena = prosecnaOcena;}

	public FarmaceutApoteka getApoteka() {
		return apoteka;
	}

	public void setApoteka(FarmaceutApoteka apoteka) {
		this.apoteka = apoteka;
	}

	public Set<Savetovanje> getSavetovanja() {
		return savetovanja;
	}

	public void setSavetovanja(Set<Savetovanje> savetovanja) {
		this.savetovanja = savetovanja;
	}

	public void setProsecnaOcena(Double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}

	public Set<Odsustvo> getOdsustvo() {
		return odsustva;
	}

	public void setOdsustvo(Set<Odsustvo> odsustvo) {
		this.odsustva = odsustvo;
	}
}
