package com.team_08.ISAproj.model;

import javax.persistence.*;

import com.team_08.ISAproj.dto.FarmaceutDTO;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Set;

@Entity(name = "FARMACEUT")
public class Farmaceut extends ZdravstveniRadnik {

    // connections
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Apoteka apoteka;

    @ElementCollection
    private Set<Date> slobodniTermini;
    @Column(name = "RADNO_VREME_POCETAK")
    private LocalTime radnoVremePocetak;
    @Column(name = "RADNO_VREME_KRAJ")
    private LocalTime radnoVremeKraj;

    // constructors
    public Farmaceut() {

    }


    // getters and setters


    public Farmaceut(FarmaceutDTO farmaceutDTO) {

        this.setFirstLogin(true);

        this.setIme(farmaceutDTO.getIme());
        this.setPrezime(farmaceutDTO.getPrezime());
        this.setAdresa(farmaceutDTO.getAdresa());
        this.setEmailAdresa(farmaceutDTO.getEmailAdresa());
        this.setDatumRodjenja(farmaceutDTO.getDatumRodjenja());
        this.setAdresa(farmaceutDTO.getAdresa());
        this.setGrad(farmaceutDTO.getGrad());
        this.setDrzava(farmaceutDTO.getDrzava());
        this.setBrojTelefona(farmaceutDTO.getBrojTelefona());
        this.setProsecnaOcena(0.0);
        this.setUsername(farmaceutDTO.getUsername());
        this.setPassword(farmaceutDTO.getPassword());
        this.radnoVremePocetak = farmaceutDTO.getRadnoVremePocetak();
        this.radnoVremeKraj = farmaceutDTO.getRadnoVremeKraj();
    }


    public Set<Date> getSlobodniTermini() {
        return slobodniTermini;
    }


    public void setSlobodniTermini(Set<Date> slobodniTermini) {
        this.slobodniTermini = slobodniTermini;
    }




    public LocalTime getRadnoVremePocetak() {
        return radnoVremePocetak;
    }


    public void setRadnoVremePocetak(LocalTime radnoVremePocetak) {
        this.radnoVremePocetak = radnoVremePocetak;
    }


    public LocalTime getRadnoVremeKraj() {
        return radnoVremeKraj;
    }


    public void setRadnoVremeKraj(LocalTime radnoVremeKraj) {
        this.radnoVremeKraj = radnoVremeKraj;
    }


    public void setApoteka(Apoteka apoteka) {
        this.apoteka = apoteka;
    }

    public Apoteka getApoteka() {
        return apoteka;
    }
}
