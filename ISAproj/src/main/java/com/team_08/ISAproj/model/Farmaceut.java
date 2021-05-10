package com.team_08.ISAproj.model;

import javax.persistence.*;

import com.team_08.ISAproj.dto.FarmaceutDTO;

import java.time.LocalDateTime;
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
    private LocalDateTime radnoVremePocetak;
    @Column(name = "RADNO_VREME_KRAJ")
    private LocalDateTime radnoVremeKraj;

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


    public void setApoteka(Apoteka apoteka) {
        this.apoteka = apoteka;
    }


}
