package com.team_08.ISAproj.model;

import javax.persistence.*;

import com.team_08.ISAproj.dto.LekDTO;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity(name = "APOTEKA_LEK")
public class ApotekaLek {

    @Id
    @SequenceGenerator(name = "ApotekaLekSeqGen", sequenceName = "ApotekaLekSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ApotekaLekSeqGen")
    private Long id;
    @Column(name = "KOLICINA")
    private Integer kolicina;
    @Column(name = "CENA")
    private Double cena;
    @Column(name = "POCETAK_VAZENJA_CENE")
    private LocalDateTime pocetakVazenjaCene;
    @Column(name = "ISTEK_VAZENJA_CENE")
    private LocalDateTime istekVazenjaCene;
    @Column(name = "PROMOTIVNA_CENA")
    private Double promotivnaCena;

    // connections
    @ManyToOne(fetch = FetchType.EAGER)
    private Apoteka apoteka;
    @ManyToOne(fetch = FetchType.EAGER)
    private Lek lek;


    // constructors
    public ApotekaLek(int kolicina, double cena, LocalDateTime istekVazenjaCene, double promotivnaCena, Apoteka apoteka, Lek lek) {
        super();
        this.kolicina = kolicina;
        this.cena = cena;
        this.istekVazenjaCene = istekVazenjaCene;
        this.promotivnaCena = promotivnaCena;
        this.apoteka = apoteka;
        this.lek = lek;
    }

    public ApotekaLek() {

    }

    public ApotekaLek(LekDTO lekDTO, Apoteka apoteka, Lek lek) {
        this.kolicina = lekDTO.getKolicina();
        this.cena = lekDTO.getCena();
        this.istekVazenjaCene = lekDTO.getIstekVazenjaCene();
        this.promotivnaCena = lekDTO.getPromotivnaCena();
        this.apoteka = apoteka;
        this.lek = lek;
    }

	public void setKolicina(Integer kolicina) {
		this.kolicina = kolicina;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}


	// getters and setters
    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }


    public LocalDateTime getPocetakVazenjaCene() {
		return pocetakVazenjaCene;
	}

	public void setPocetakVazenjaCene(LocalDateTime pocetakVazenjaCene) {
		this.pocetakVazenjaCene = pocetakVazenjaCene;
	}

	public LocalDateTime getIstekVazenjaCene() {
		return istekVazenjaCene;
	}

	public void setIstekVazenjaCene(LocalDateTime istekVazenjaCene) {
		this.istekVazenjaCene = istekVazenjaCene;
	}

	public Double getPromotivnaCena() {
		return promotivnaCena;
	}

	public void setPromotivnaCena(Double promotivnaCena) {
		this.promotivnaCena = promotivnaCena;
	}

	public Apoteka getApoteka() {
        return apoteka;
    }

    public void setApoteka(Apoteka apoteka) {
        this.apoteka = apoteka;
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

    public void update(LekDTO lekDTO) {
        this.kolicina = lekDTO.getKolicina();
        this.cena = lekDTO.getCena();
        this.istekVazenjaCene = lekDTO.getIstekVazenjaCene();
        this.promotivnaCena = lekDTO.getPromotivnaCena();
    }
}
