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
    private Date datumNarudzbine;
    private String apotekaId;
    private String pacijent;
    private List<NarudzbenicaAdminDTO> narudzbenice = new ArrayList<NarudzbenicaAdminDTO>();
    private Set<NarudzbenicaLek> narudzbeniceLek = new HashSet<NarudzbenicaLek>();
    public NarudzbenicaDTO() {


    }

    public NarudzbenicaDTO(String naziv, String sifra, int kolicina, Date datumNarudzbine, String aID) {
        super();
        this.naziv = naziv;
        this.sifra = sifra;
        this.kolicina = kolicina;
        this.datumNarudzbine = datumNarudzbine;
        this.apotekaId = aID;
    }
    public NarudzbenicaDTO(String naziv, String sifra, int kolicina, Date datumNarudzbine, String aID, ArrayList<NarudzbenicaAdminDTO> narudzbine) {
        super();
        this.naziv = naziv;
        this.sifra = sifra;
        this.kolicina = kolicina;
        this.datumNarudzbine = datumNarudzbine;
        this.apotekaId = aID;
        this.narudzbenice = narudzbine;
    }
    
    public NarudzbenicaDTO(Narudzbenica n) {
    	this.datumNarudzbine = n.getRokPonude();
		this.narudzbeniceLek = n.getLekovi(); 
	}

	public Set<NarudzbenicaLek> getNarudzbeniceLek() {
		return narudzbeniceLek;
	}

	public void setNarudzbeniceLek(Set<NarudzbenicaLek> narudzbeniceLek) {
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

    public Date getDatumNarudzbine() {
        return datumNarudzbine;
    }

    public void setDatumNarudzbine(Date datumNarudzbine) {
        this.datumNarudzbine = datumNarudzbine;
    }

    public String getPacijent() {
        return pacijent;
    }

    public void setPacijent(String korisnik) {
        this.pacijent = korisnik;
    }
}
