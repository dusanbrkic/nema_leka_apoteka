package com.team_08.ISAproj.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name = "OCENA_APOTEKA")
public class OcenaApoteka extends Ocena{
    //connections
	@ManyToOne(fetch = FetchType.EAGER)
    private Apoteka apoteka;


	public OcenaApoteka(Apoteka a, Integer o, LocalDateTime d, Pacijent p) {
		super(o, d, p);
		this.apoteka = a;
	}


	public Apoteka getApoteka() {
		return apoteka;
	}


	public void setApoteka(Apoteka apoteka) {
		this.apoteka = apoteka;
	}
	
	public OcenaApoteka() {
		super();
	}
}
