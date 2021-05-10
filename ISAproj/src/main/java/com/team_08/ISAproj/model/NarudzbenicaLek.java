package com.team_08.ISAproj.model;

import javax.persistence.*;

@Entity(name = "NARUDZBENICA_LEK")
public class NarudzbenicaLek {
    @Id
    @SequenceGenerator(name = "NarudzbenicaLekSeqGen", sequenceName = "NarudzbenicaLekSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NarudzbenicaLekSeqGen")
    private Long id;
    @Column(name = "KOLICINA")
    private Integer kolicina;

    //connections

    @ManyToOne(fetch = FetchType.EAGER)
    private Lek lek;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Narudzbenica narudzbenica;

    public NarudzbenicaLek(int kolicina, Narudzbenica n, Lek lek) {
        super();
        this.kolicina = kolicina;
        this.lek = lek;
        this.narudzbenica = n;
    }

    public NarudzbenicaLek(int kolicina, Lek lek) {
        this.kolicina = kolicina;
        this.lek = lek;
    }

    public NarudzbenicaLek() {

    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public Lek getLek() {
        return lek;
    }

    public void setLek(Lek lek) {
        this.lek = lek;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Narudzbenica getNarudzbenica() {
        return narudzbenica;
    }

    public void setNarudzbenica(Narudzbenica narudzbenica) {
        this.narudzbenica = narudzbenica;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }
}
