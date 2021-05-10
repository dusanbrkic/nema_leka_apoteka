package com.team_08.ISAproj.dto;

import java.util.ArrayList;
import java.util.Date;

import com.team_08.ISAproj.model.Narudzbenica;

public class RezervacijaDTO {


    private Long id;
    private String sifraLeka;
    private int kolicina;
    private Date datumRezervacije;
    private String apotekaId;
    private String pacijent;
    private boolean preuzeto;

    public boolean isPreuzeto() {
        return preuzeto;
    }

    public void setPreuzeto(boolean preuzeto) {
        this.preuzeto = preuzeto;
    }

    public RezervacijaDTO(Long id, String sifraLeka, Date datumRezervacije, String apotekaId, boolean preuzeto) {
        super();
        this.id = id;
        this.sifraLeka = sifraLeka;
        this.datumRezervacije = datumRezervacije;
        this.apotekaId = apotekaId;
        this.preuzeto = preuzeto;
    }

    public RezervacijaDTO(String sifraLeka, int kolicina, Date datumRezervacije, String apotekaId, String pacijent) {
        this.sifraLeka = sifraLeka;
        this.kolicina = kolicina;
        this.datumRezervacije = datumRezervacije;
        this.apotekaId = apotekaId;
        this.pacijent = pacijent;
    }

    public String getSifraLeka() {
        return sifraLeka;
    }

    public void setSifraLeka(String sifraLeka) {
        this.sifraLeka = sifraLeka;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public Date getDatumRezervacije() {
        return datumRezervacije;
    }

    public void setDatumRezervacije(Date datumRezervacije) {
        this.datumRezervacije = datumRezervacije;
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
