package com.team_08.ISAproj.model;

import javax.persistence.*;

@Entity(name = "PREGLED_LEK")
public class PregledLek {
    @Id
    @SequenceGenerator(name = "PregledLekSeqGen", sequenceName = "PregledLekSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PregledLekSeqGen")
    private Long id;
    @Column(name = "KOLICINA")
    private Integer kolicina;
    @Column(name = "TRAJANJE_TERAPIJE")
    private Integer trajanjeTerapije;


    //connections
    @ManyToOne(fetch = FetchType.EAGER)
    private Lek lek;

    @ManyToOne(fetch = FetchType.EAGER)
    private Pregled pregled;

    public PregledLek(Integer kolicina, Integer trajanjeTerapije, Pregled pregled, Lek l) {
        this.kolicina = kolicina;
        this.trajanjeTerapije = trajanjeTerapije;
        this.pregled = pregled;
        this.lek = l;
    }

    public PregledLek() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public Lek getLek() {
        return lek;
    }

    public void setLek(Lek lek) {
        this.lek = lek;
    }

    public Integer getTrajanjeTerapije() {
        return trajanjeTerapije;
    }

    public void setTrajanjeTerapije(Integer trajanjeTerapije) {
        this.trajanjeTerapije = trajanjeTerapije;
    }

    public Pregled getPregled() {
        return pregled;
    }

    public void setPregled(Pregled pregled) {
        this.pregled = pregled;
    }
}
