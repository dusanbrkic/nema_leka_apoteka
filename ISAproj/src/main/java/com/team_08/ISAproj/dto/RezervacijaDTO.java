package com.team_08.ISAproj.dto;

import java.util.ArrayList;
import java.util.Date;

import com.team_08.ISAproj.model.Narudzbenica;

public class RezervacijaDTO {
	
	
	private Long id;
	private String lekovi;
	private String datumNarudzbine;
	private String apotekaId;
	private String pacijent;
	private String test = "RADII";
	
	public RezervacijaDTO(Long id,String lekovi, String datumNarudzbine, String apotekaId) {
		super();
		this.id = id;
		this.lekovi = lekovi;
		this.datumNarudzbine = datumNarudzbine;
		this.apotekaId = apotekaId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getApotekaId() {
		return apotekaId;
	}

	public void setApotekaId(String apotekaId) {
		this.apotekaId = apotekaId;
	}

	public String getPacijent() {
		return pacijent;
	}

	public void setPacijent(String pacijent) {
		this.pacijent = pacijent;
	}
	

}
