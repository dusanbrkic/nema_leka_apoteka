package com.team_08.ISAproj.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.team_08.ISAproj.model.Narudzbenica;
import com.team_08.ISAproj.model.NarudzbenicaLek;

public class NarudzbenicaDTO {


    private String naziv;
    private String sifra;
    private int kolicina;
    private Date rokPonude;
    private String apotekaId;
    private String status;
    private boolean preuzet;
    private List<NarudzbenicaAdminDTO> narudzbenice = new ArrayList<NarudzbenicaAdminDTO>();
    private List<NarudzbenicaLek> narudzbeniceLek = new ArrayList<NarudzbenicaLek>();
    private List<NarudzbenicaAdminDTO> narudzbeniceLekDTO = new ArrayList<NarudzbenicaAdminDTO>();
    public NarudzbenicaDTO() {


    }

    public NarudzbenicaDTO(String naziv, String sifra, int kolicina, Date datumNarudzbine, String aID) {
        super();
        this.naziv = naziv;
        this.sifra = sifra;
        this.kolicina = kolicina;
        this.rokPonude = datumNarudzbine;
        this.apotekaId = aID;
    }
    public NarudzbenicaDTO(String naziv, String sifra, int kolicina, Date datumNarudzbine, String aID, ArrayList<NarudzbenicaAdminDTO> narudzbine) {
        super();
        this.naziv = naziv;
        this.sifra = sifra;
        this.kolicina = kolicina;
        this.rokPonude = datumNarudzbine;
        this.apotekaId = aID;
        this.narudzbenice = narudzbine;
    }
    
    public NarudzbenicaDTO(Narudzbenica n) {
    	this.rokPonude = n.getRokPonude();
    	this.preuzet =n.getPreuzet();
		//this.narudzbeniceLek = n.getLekovi(); 
	}

    public void dodajLekove(List<NarudzbenicaLek> narudzbeniceLek) {
    	for(NarudzbenicaLek nl: narudzbeniceLek) {
    		narudzbeniceLekDTO.add(new  NarudzbenicaAdminDTO(nl.getLek().getNaziv(),nl.getKolicina()));
    	}
    	
    }
	public List<NarudzbenicaAdminDTO> getNarudzbeniceLekDTO() {
		return narudzbeniceLekDTO;
	}

	public void setNarudzbeniceLekDTO(List<NarudzbenicaAdminDTO> narudzbeniceLekDTO) {
		this.narudzbeniceLekDTO = narudzbeniceLekDTO;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isPreuzet() {
		return preuzet;
	}

	public void setPreuzet(boolean preuzet) {
		this.preuzet = preuzet;
	}

	public List<NarudzbenicaLek> getNarudzbeniceLek() {
		return narudzbeniceLek;
	}

	public void setNarudzbeniceLek(List<NarudzbenicaLek> narudzbeniceLek) {
		this.narudzbeniceLek = narudzbeniceLek;
	}

	public List<NarudzbenicaAdminDTO> getNarudzbenice() {
		return narudzbenice;
	}

	public void setNarudzbenice(List<NarudzbenicaAdminDTO> narudzbenice) {
		this.narudzbenice = narudzbenice;
	}

	public String getApotekaId() {
        return apotekaId;
    }

    public void setApotekaId(String apotekaId) {
        this.apotekaId = apotekaId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

	public Date getRokPonude() {
		return rokPonude;
	}

	public void setRokPonude(Date rokPonude) {
		this.rokPonude = rokPonude;
	}

}
