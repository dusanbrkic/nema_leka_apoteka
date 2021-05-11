package com.team_08.ISAproj.model;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "PROMOCIJA")
public class Promocija {

	@Id
    @SequenceGenerator(name = "PromocijaSeqGen", sequenceName = "PromocijaSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PromocijaSeqGen")
    private Long id;
    @Column(name = "KRAJ_VAZENJA")
    private Date krajVazenja;
    @Column(name = "POCETAK_VAZENJA")
    private Date pocetakVazenja;
    @Column(name ="TEKST_PROMOCIJE")
    private String tekstPromocije;
    //conections
    @ManyToOne(fetch = FetchType.EAGER)
    private Apoteka apoteka;


    public Promocija(Date krajVazenja, Date pocetakVazenja) {
        super();
        this.krajVazenja = krajVazenja;
        this.pocetakVazenja = pocetakVazenja;
    }
    public String getTekstPromocije() {
		return tekstPromocije;
	}

	public void setTekstPromocije(String tekstPromocije) {
		this.tekstPromocije = tekstPromocije;
	}

	public Apoteka getApoteka() {
		return apoteka;
	}

	public void setApoteka(Apoteka apoteka) {
		this.apoteka = apoteka;
	}

    public Promocija() {

    }

    public Date getKrajVazenja() {
        return krajVazenja;
    }

    public void setKrajVazenja(Date krajVazenja) {
        this.krajVazenja = krajVazenja;
    }

    public Date getPocetakVazenja() {
        return pocetakVazenja;
    }

    public void setPocetakVazenja(Date pocetakVazenja) {
        this.pocetakVazenja = pocetakVazenja;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
