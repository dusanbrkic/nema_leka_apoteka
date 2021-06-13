package com.team_08.ISAproj.dto;

import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.RezervacijaLek;

public class RezervacijaLekDTO {
    private double cena;
    private int kolicina;
    private LekDTO lek;

    public RezervacijaLekDTO(){}

    public RezervacijaLekDTO(RezervacijaLek rezervacijaLek){
        this.kolicina = rezervacijaLek.getKolicina();
        this.lek = new LekDTO(rezervacijaLek.getLek());
        this.cena = rezervacijaLek.getCena();
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public LekDTO getLek() {
        return lek;
    }

    public void setLek(LekDTO lek) {
        this.lek = lek;
    }
}
