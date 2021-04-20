package com.team_08.ISAproj.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name="DERMATOLOG")
public class Dermatolog extends Korisnik {

    @Column(name = "PROSECNA_OCENA")
    private Double prosecnaOcena;

    // connections
    @OneToMany(mappedBy = "dermatolog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DermatologApoteka> apoteke;
    @OneToMany(mappedBy = "dermatolog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Pregled> pregledi;

    public Dermatolog() {

    }

    public double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }

    public Set<DermatologApoteka> getApoteke() {
        return apoteke;
    }

    public void setApoteke(Set<DermatologApoteka> apoteke) {
        this.apoteke = apoteke;
    }

    public Set<Pregled> getPregledi() {
        return pregledi;
    }

    public void setPregledi(Set<Pregled> pregledi) {
        this.pregledi = pregledi;
    }
}
