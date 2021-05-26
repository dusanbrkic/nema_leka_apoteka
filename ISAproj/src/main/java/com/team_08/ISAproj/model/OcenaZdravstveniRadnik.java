package com.team_08.ISAproj.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name = "OCENA_ZDRAVSTVENI_RADNIK")
public class OcenaZdravstveniRadnik extends Ocena{
    //connections
	@ManyToOne(fetch = FetchType.EAGER)
    private ZdravstveniRadnik zdravstveniRadnik;

	public OcenaZdravstveniRadnik(ZdravstveniRadnik z, Integer o, LocalDateTime d, Pacijent p) {
		super(o, d, p);
		this.zdravstveniRadnik = z;
	}

	public ZdravstveniRadnik getZdravstveniRadnik() {
		return zdravstveniRadnik;
	}

	public void setZdravstveniRadnik(ZdravstveniRadnik zdravstveniRadnik) {
		this.zdravstveniRadnik = zdravstveniRadnik;
	}
	
}
