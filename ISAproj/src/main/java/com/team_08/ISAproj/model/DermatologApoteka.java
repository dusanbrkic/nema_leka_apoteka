package com.team_08.ISAproj.model;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "DERMATOLOG_APOTEKA")
public class DermatologApoteka {
    @Id
    @SequenceGenerator(name = "DermatologApotekaSeqGen", sequenceName = "DermatologApotekaSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DermatologApotekaSeqGen")
    private Long id;

    @Column(name = "RADNO_VREME_POCETAK")
    private LocalTime radnoVremePocetak;
    @Column(name = "RADNO_VREME_KRAJ")
    private LocalTime radnoVremeKraj;

    @ElementCollection
    private Set<Date> slobodniTermini;

    //connections
    @ManyToOne(fetch = FetchType.EAGER)
    private Dermatolog dermatolog;
    @ManyToOne(fetch = FetchType.EAGER)
    private Apoteka apoteka;


    public DermatologApoteka(Dermatolog dermatolog, Apoteka apoteka, Set<Date> slobodniTermini,
                             LocalTime radnoVremePocetak, LocalTime radnoVremeKraj) {
        super();
        this.dermatolog = dermatolog;
        this.apoteka = apoteka;
        this.slobodniTermini = slobodniTermini;
        this.radnoVremePocetak = radnoVremePocetak;
        this.radnoVremeKraj = radnoVremeKraj;
    }

    public DermatologApoteka(Dermatolog dermatolog, Apoteka apoteka,
                             LocalTime radnoVremePocetak, LocalTime radnoVremeKraj) {
        super();
        this.dermatolog = dermatolog;
        this.apoteka = apoteka;
        this.slobodniTermini = new HashSet<Date>();
        this.radnoVremePocetak = radnoVremePocetak;
        this.radnoVremeKraj = radnoVremeKraj;
    }

    public DermatologApoteka() {

    }

    public Dermatolog getDermatolog() {
        return dermatolog;
    }

    public void setDermatolog(Dermatolog dermatolog) {
        this.dermatolog = dermatolog;
    }

    public Apoteka getApoteka() {
        return apoteka;
    }

    public void setApoteka(Apoteka apoteka) {
        this.apoteka = apoteka;
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
