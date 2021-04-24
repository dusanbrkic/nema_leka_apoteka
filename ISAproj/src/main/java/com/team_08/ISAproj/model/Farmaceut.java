package com.team_08.ISAproj.model;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "FARMACEUT")
public class Farmaceut extends ZdravstveniRadnik {
	
	// connections
	@OneToOne(mappedBy = "farmaceut", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private FarmaceutApoteka apoteka;
	

	// constructors
	public Farmaceut() {

	}


	// getters and setters

	public FarmaceutApoteka getApoteka() {
		return apoteka;
	}

	public void setApoteka(FarmaceutApoteka apoteka) {
		this.apoteka = apoteka;
	}
}
