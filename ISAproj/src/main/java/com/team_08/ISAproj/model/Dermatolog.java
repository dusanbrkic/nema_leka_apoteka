package com.team_08.ISAproj.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name="DERMATOLOG")
public class Dermatolog extends Korisnik {

    @Column(name = "PROSECNA_OCENA")
    private double prosecnaOcena;

    // connections
    @OneToMany(mappedBy = "dermatolog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DermatologApoteka> apoteke;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Pacijent> pregledaniPacijenti;
    @OneToMany(mappedBy = "dermatolog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Pregled> pregledi;


    public Dermatolog(String username, String password, String ime, String prezime, Date datumRodjenja, String emailAdresa, double prosecnaOcena) {
        super(username, password, ime, prezime, datumRodjenja, emailAdresa);
        this.prosecnaOcena = prosecnaOcena;
    }

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

    public Set<Pacijent> getPregledaniPacijenti() {
        return pregledaniPacijenti;
    }

    public void setPregledaniPacijenti(Set<Pacijent> pregledaniPacijenti) {
        this.pregledaniPacijenti = pregledaniPacijenti;
    }

    public Set<Pregled> getPregledi() {
        return pregledi;
    }

    public void setPregledi(Set<Pregled> pregledi) {
        this.pregledi = pregledi;
    }
}
