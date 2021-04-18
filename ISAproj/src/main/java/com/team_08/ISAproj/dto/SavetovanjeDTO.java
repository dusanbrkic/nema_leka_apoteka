package com.team_08.ISAproj.dto;

import com.team_08.ISAproj.model.Lek;
import com.team_08.ISAproj.model.Savetovanje;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class SavetovanjeDTO {
    private LocalDateTime start;
    private LocalDateTime end;
    private String dijagnoza;
    private boolean savetovanjeObavljeno;
    private Long trajanje;

    // connections
    private PregledanKorisnikDTO pacijent;
    private ApotekaDTO apoteka;
    private Set<LekDTO> preporuceniLekovi;

    public SavetovanjeDTO(Savetovanje savetovanje) {
        this.start = savetovanje.getVreme();
        this.end = savetovanje.getKraj();
        this.dijagnoza = savetovanje.getDijagnoza();
        this.savetovanjeObavljeno = savetovanje.isSavetovanjeObavljeno();
        this.pacijent = new PregledanKorisnikDTO(savetovanje.getPacijent());
        this.apoteka = new ApotekaDTO(savetovanje.getApoteka());
        this.trajanje = savetovanje.getTrajanje();
        this.preporuceniLekovi = new HashSet<LekDTO>();
        for (Lek l : savetovanje.getPreporuceniLekovi())
            this.preporuceniLekovi.add(new LekDTO(l));
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getDijagnoza() {
        return dijagnoza;
    }

    public void setDijagnoza(String dijagnoza) {
        this.dijagnoza = dijagnoza;
    }

    public boolean isSavetovanjeObavljeno() {
        return savetovanjeObavljeno;
    }

    public void setSavetovanjeObavljeno(boolean savetovanjeObavljeno) {
        this.savetovanjeObavljeno = savetovanjeObavljeno;
    }

    public Long getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(Long trajanje) {
        this.trajanje = trajanje;
    }

    public PregledanKorisnikDTO getPacijent() {
        return pacijent;
    }

    public void setPacijent(PregledanKorisnikDTO pacijent) {
        this.pacijent = pacijent;
    }

    public ApotekaDTO getApoteka() {
        return apoteka;
    }

    public void setApoteka(ApotekaDTO apoteka) {
        this.apoteka = apoteka;
    }

    public Set<LekDTO> getPreporuceniLekovi() {
        return preporuceniLekovi;
    }

    public void setPreporuceniLekovi(Set<LekDTO> preporuceniLekovi) {
        this.preporuceniLekovi = preporuceniLekovi;
    }
}
