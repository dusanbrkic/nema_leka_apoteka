package com.team_08.ISAproj.dto;

import com.team_08.ISAproj.model.Korisnik;
import com.team_08.ISAproj.model.Pacijent;

import java.time.LocalDateTime;
import java.util.Date;

public class PacijentDTO {
    private String ime;
    private String prezime;
    private Date datumRodjenja;
    private String emailAdresa;
    private String adresa;
    private String grad;
    private String drzava;
    private String brojTelefona;

    public PacijentDTO() {
    }

    public PacijentDTO(Korisnik k) {
        this.ime = k.getIme();
        this.prezime = k.getPrezime();
        this.datumRodjenja = k.getDatumRodjenja();
        this.emailAdresa = k.getEmailAdresa();
        this.adresa = k.getAdresa();
        this.grad = k.getGrad();
        this.drzava = k.getDrzava();
        this.brojTelefona = k.getBrojTelefona();
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getEmailAdresa() {
        return emailAdresa;
    }

    public void setEmailAdresa(String emailAdresa) {
        this.emailAdresa = emailAdresa;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }
}
