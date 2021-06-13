package com.team_08.ISAproj.dto;

import java.util.ArrayList;
import java.time.LocalDateTime;

import javax.persistence.Column;

import com.team_08.ISAproj.model.ApotekaLek;
import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.enums.OblikLeka;
import com.team_08.ISAproj.model.enums.TipLeka;

public class LekDTO {
    private String sifra;
    private String naziv;
    private String uputstvo;
    private TipLeka tip;
    private OblikLeka oblikLeka;
    private String sastav;
    private String dodatneNapomene;
    private Integer kolicina;
    private Double cena;
    private LocalDateTime istekVazenjaCene;
    private LocalDateTime pocetakVazenjaCene;
    private Double promotivnaCena;
    private String cookie;
    private Double prosecnaOcena;
    private int brojOcena;
    private boolean pravoOcene = false;

    public boolean isPravoOcene() {
		return pravoOcene;
	}

	public void setPravoOcene(boolean pravoOcene) {
		this.pravoOcene = pravoOcene;
	}

	public int getBrojOcena() {
		return brojOcena;
	}

	public void setBrojOcena(int brojOcena) {
		this.brojOcena = brojOcena;
	}

	public Double getProsecnaOcena() {
		return prosecnaOcena;
	}

	public void setProsecnaOcena(Double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}

	private boolean alergija = false;

    public boolean isAlergija() {
		return alergija;
	}

	public void setAlergija(boolean alergija) {
		this.alergija = alergija;
	}

	public LekDTO() {

    }

    public LekDTO(Lek lek) {
        this.sifra = lek.getSifra();
        this.naziv = lek.getNaziv();
        this.uputstvo = lek.getUpustvo();
        this.tip = lek.getTip();
        this.oblikLeka = lek.getOblikLeka();
        this.sastav = lek.getSastav();
        this.dodatneNapomene = lek.getDodatneNapomene();
        this.prosecnaOcena = lek.getProsecnaOcena();
    }

    public LekDTO(ApotekaLek al) {
        Lek lek = al.getLek();
        this.sifra = lek.getSifra();
        this.naziv = lek.getNaziv();
        this.uputstvo = lek.getUpustvo();
        this.tip = lek.getTip();
        this.oblikLeka = lek.getOblikLeka();
        this.sastav = lek.getSastav();
        this.dodatneNapomene = lek.getDodatneNapomene();
        this.cena = al.getCena();
        this.kolicina = al.getKolicina();
        this.promotivnaCena = al.getPromotivnaCena();
        this.istekVazenjaCene = al.getIstekVazenjaCene();
        this.pocetakVazenjaCene = al.getPocetakVazenjaCene();
        this.prosecnaOcena = al.getLek().getProsecnaOcena();
    }

	public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }



    public LocalDateTime getIstekVazenjaCene() {
		return istekVazenjaCene;
	}

	public void setIstekVazenjaCene(LocalDateTime istekVazenjaCene) {
		this.istekVazenjaCene = istekVazenjaCene;
	}

	public LocalDateTime getPocetakVazenjaCene() {
		return pocetakVazenjaCene;
	}

	public void setPocetakVazenjaCene(LocalDateTime pocetakVazenjaCene) {
		this.pocetakVazenjaCene = pocetakVazenjaCene;
	}

	public Double getPromotivnaCena() {
		return promotivnaCena;
	}

	public void setPromotivnaCena(Double promotivnaCena) {
		this.promotivnaCena = promotivnaCena;
	}

	public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getUputstvo() {
        return uputstvo;
    }

    public void setUputstvo(String uputstvo) {
        this.uputstvo = uputstvo;
    }

    public TipLeka getTip() {
        return tip;
    }

    public void setTip(TipLeka tip) {
        this.tip = tip;
    }

    public OblikLeka getOblikLeka() {
        return oblikLeka;
    }

    public void setOblikLeka(OblikLeka oblikLeka) {
        this.oblikLeka = oblikLeka;
    }

    public String getSastav() {
        return sastav;
    }

    public void setSastav(String sastav) {
        this.sastav = sastav;
    }

    public String getDodatneNapomene() {
        return dodatneNapomene;
    }

    public void setDodatneNapomene(String dodatneNapomene) {
        this.dodatneNapomene = dodatneNapomene;
    }
}
