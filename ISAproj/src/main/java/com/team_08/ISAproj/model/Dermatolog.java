package com.team_08.ISAproj.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "DERMATOLOG")
public class Dermatolog extends ZdravstveniRadnik {

    //connections
    @OneToMany(mappedBy = "dermatolog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DermatologApoteka> apoteke;

    public Dermatolog() {

    }

    public Set<DermatologApoteka> getApoteke() {
        return apoteke;
    }

    public void setApoteke(Set<DermatologApoteka> apoteke) {
        this.apoteke = apoteke;
    }
}
