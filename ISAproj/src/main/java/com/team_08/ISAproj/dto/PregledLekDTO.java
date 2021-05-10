package com.team_08.ISAproj.dto;

import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.Pregled;
import com.team_08.ISAproj.model.PregledLek;


public class PregledLekDTO {

    private Integer kolicina;
    private Integer trajanjeTerapije;

    private LekDTO lek;

    public PregledLekDTO(){}

    public PregledLekDTO(PregledLek p) {
        this.kolicina = p.getKolicina();
        this.trajanjeTerapije = p.getTrajanjeTerapije();
        this.lek = new LekDTO(p.getLek());
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public Integer getTrajanjeTerapije() {
        return trajanjeTerapije;
    }

    public void setTrajanjeTerapije(Integer trajanjeTerapije) {
        this.trajanjeTerapije = trajanjeTerapije;
    }

    public LekDTO getLek() {
        return lek;
    }

    public void setLek(LekDTO lek) {
        this.lek = lek;
    }
}
