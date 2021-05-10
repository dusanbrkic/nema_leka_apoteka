package com.team_08.ISAproj.dto;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;

import com.team_08.ISAproj.model.Dermatolog;
import com.team_08.ISAproj.model.DermatologApoteka;

public class DermatologDTO {

    private String username;
    private String password;
    private String ime;
    private String prezime;
    private Double cena;
    private LocalDateTime radnoVremePocetak;
    private LocalDateTime radnoVremeKraj;
    private Double prosecnaOcena;

    public DermatologDTO() {


    }

    public DermatologDTO(DermatologApoteka da) {
        this.username = da.getDermatolog().getUsername();
        this.password = da.getDermatolog().getPassword();
        this.ime = da.getDermatolog().getIme();
        this.prezime = da.getDermatolog().getPrezime();
        this.radnoVremeKraj = da.getRadnoVremeKraj();
        this.radnoVremePocetak = da.getRadnoVremePocetak();
        this.prosecnaOcena = da.getDermatolog().getProsecnaOcena();
    }

    public DermatologDTO(Dermatolog d) {
        this.username = d.getUsername();
        this.password = d.getPassword();
        this.ime = d.getIme();
        this.prezime = d.getPrezime();
        this.prosecnaOcena = d.getProsecnaOcena();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public LocalDateTime getRadnoVremePocetak() {
        return radnoVremePocetak;
    }

    public void setRadnoVremePocetak(LocalDateTime radnoVremePocetak) {
        this.radnoVremePocetak = radnoVremePocetak;
    }

    public LocalDateTime getRadnoVremeKraj() {
        return radnoVremeKraj;
    }

    public void setRadnoVremeKraj(LocalDateTime radnoVremeKraj) {
        this.radnoVremeKraj = radnoVremeKraj;
    }

    public Double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(Double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }

}
