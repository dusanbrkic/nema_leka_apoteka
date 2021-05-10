package com.team_08.ISAproj.dto;

import com.team_08.ISAproj.model.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class PregledDTO {
    private Long id;

    private LocalDateTime start;
    private LocalDateTime end;
    private String dijagnoza;
    private boolean pregledObavljen;
    private boolean pregledZakazan;
    private Long trajanje;
    private String apotekaId;
    private String username;
    private Double cena;
    // connections
    private PacijentDTO pacijent;
    private ApotekaDTO apoteka;
    private Set<PregledLekDTO> preporuceniLekovi;

    public PregledDTO() {
    }

    public PregledDTO(Pregled pregled) {
        this.start = pregled.getVreme();
        this.id = pregled.getId();
        this.pregledZakazan = pregled.isPregledZakazan();
        this.end = pregled.getKraj();
        this.dijagnoza = pregled.getDijagnoza();
        this.pregledObavljen = pregled.isPregledObavljen();
        this.pacijent = new PacijentDTO(pregled.getPacijent());
        this.apoteka = new ApotekaDTO(pregled.getApoteka());
        this.trajanje = pregled.getTrajanje();
        this.cena = pregled.getCena();
        this.preporuceniLekovi = new HashSet<PregledLekDTO>();
    }
    
    public PregledDTO(Long id, LocalDateTime start, LocalDateTime end, Double cena, String username) {
    	this.start = start;
        this.id = id;
        this.end = end;
        this.cena = cena;
        this.username = username;
    }

    public void loadLekovi(Pregled pregled) {
        for (PregledLek p : pregled.getPreporuceniLekovi())
            this.preporuceniLekovi.add(new PregledLekDTO(p));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public LocalDateTime getStart() {
        return start;
    }
    public String getApotekaId() {
		return apotekaId;
	}
	public void setApotekaId(String apotekaId) {
		this.apotekaId = apotekaId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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

    public boolean isPregledObavljen() {
        return pregledObavljen;
    }

    public void setPregledObavljen(boolean pregledObavljen) {
        this.pregledObavljen = pregledObavljen;
    }

    public PacijentDTO getPacijent() {
        return pacijent;
    }

    public void setPacijent(PacijentDTO pacijent) {
        this.pacijent = pacijent;
    }

    public ApotekaDTO getApoteka() {
        return apoteka;
    }

    public void setApoteka(ApotekaDTO apoteka) {
        this.apoteka = apoteka;
    }

    public Set<PregledLekDTO> getPreporuceniLekovi() {
        return preporuceniLekovi;
    }

    public void setPreporuceniLekovi(Set<PregledLekDTO> preporuceniLekovi) {
        this.preporuceniLekovi = preporuceniLekovi;
    }

    public Long getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(Long trajanje) {
        this.trajanje = trajanje;
    }

    public boolean isPregledZakazan() {
        return pregledZakazan;
    }

    public void setPregledZakazan(boolean pregledZakazan) {
        this.pregledZakazan = pregledZakazan;
    }
}
