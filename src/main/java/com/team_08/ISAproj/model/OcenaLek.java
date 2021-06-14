package com.team_08.ISAproj.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name = "OCENA_LEK")
public class OcenaLek extends Ocena{
    //connections
	@ManyToOne(fetch = FetchType.EAGER)
    private Lek lek;

	public Lek getLek() {
		return lek;
	}

	public void setLek(Lek lek) {
		this.lek = lek;
	}

	public OcenaLek(Lek lek, Integer o, LocalDateTime d, Pacijent p) {
		super(o, d, p);
		this.lek = lek;
	}
	
	public OcenaLek() {
		super();
	}
}
