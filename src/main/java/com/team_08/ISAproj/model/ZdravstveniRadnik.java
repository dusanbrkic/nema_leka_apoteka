package com.team_08.ISAproj.model;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "ZDRAVSTVENI_RADNIK")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ZdravstveniRadnik extends Korisnik {

    @Column(name = "PROSECNA_OCENA")
    private Double prosecnaOcena;

    // connections
    @OneToMany(mappedBy = "zdravstveniRadnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Odsustvo> odsustva;
    
    @OneToMany(mappedBy = "zdravstveniRadnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Pregled> pregledi;

    public Double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(Double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }

    public Set<Odsustvo> getOdsustva() {
        return odsustva;
    }

    public void setOdsustva(Set<Odsustvo> odsustva) {
        this.odsustva = odsustva;
    }

    public Set<Pregled> getPregledi() {
        return pregledi;
    }

    public void setPregledi(Set<Pregled> pregledi) {
        this.pregledi = pregledi;
    }
}
