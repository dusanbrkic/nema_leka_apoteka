package com.team_08.ISAproj.model;

import javax.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "NARUDZBENICA")
public class Narudzbenica {
    @Id
    @SequenceGenerator(name = "NarudzbenicaSeqGen", sequenceName = "NarudzbenicaSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NarudzbenicaSeqGen")
    private Long id;
    @Column(name = "ROK_PONUDE")
    private Date rokPonude;

    // connections
    @OneToMany(mappedBy = "narudzbenica", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<NarudzbenicaLek> lekovi = new HashSet<NarudzbenicaLek>();

    public void addNarudzbenicaLek(NarudzbenicaLek nl) {
        this.lekovi.add(nl);
    }

    public Set<NarudzbenicaLek> getLekovi() {
        return lekovi;
    }

    public void setLekovi(Set<NarudzbenicaLek> lekovi) {
        this.lekovi = lekovi;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    private Apoteka apoteka;
    
    @Column(name = "PREUZET")
    private boolean preuzet;
    public Narudzbenica(Date rokPonuda) {
        super();
        this.rokPonude = rokPonuda;
        this.lekovi = new HashSet<NarudzbenicaLek>();
    }
    
    public boolean getPreuzet() {
		return preuzet;
	}

	public void setPreuzet(boolean preuzet) {
		this.preuzet = preuzet;
	}

	public Narudzbenica() {
        this.lekovi = new HashSet<NarudzbenicaLek>();
    }

    public Date getRokPonuda() {
        return rokPonude;
    }

    public void setRokPonuda(Date rokPonuda) {
        this.rokPonude = rokPonuda;
    }


    public Date getRokPonude() {
        return rokPonude;
    }

    public void setRokPonude(Date rokPonude) {
        this.rokPonude = rokPonude;
    }

    public Apoteka getApoteka() {
        return apoteka;
    }

    public void setApoteka(Apoteka apoteka) {
        this.apoteka = apoteka;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
