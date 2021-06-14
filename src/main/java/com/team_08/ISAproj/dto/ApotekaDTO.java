package com.team_08.ISAproj.dto;

import javax.persistence.Column;

import com.team_08.ISAproj.model.Apoteka;

public class ApotekaDTO {

    private Long id;
    private String naziv;
    private String adresa;
    private Double prosecnaOcena;
    private String opis;
    private Double cenaPregleda;
    private Double cenaSavetovanja;
    private int brojOcena;
    private boolean pravoOcene = false;
    private String cookie;
    private Double longitude;
    private Double latitude;
    
    public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public int getBrojOcena() {
		return brojOcena;
	}

	public void setBrojOcena(int brojOcena) {
		this.brojOcena = brojOcena;
	}

	public boolean isPravoOcene() {
		return pravoOcene;
	}

	public void setPravoOcene(boolean pravoOcene) {
		this.pravoOcene = pravoOcene;
	}

	public ApotekaDTO() {

    }


    public ApotekaDTO(Long id, String naziv, String adresa, Double prosecnaOcena, String opis, Double cenaPregleda, Double cenaSavetovanja,Double longitude, Double latitude) {
        super();
        this.id = id;
        this.naziv = naziv;
        this.adresa = adresa;
        this.prosecnaOcena = prosecnaOcena;
        this.opis = opis;
        this.cenaSavetovanja = cenaSavetovanja;
        this.cenaPregleda = cenaPregleda;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public ApotekaDTO(Apoteka a) {
        this.id = a.getId();
        this.naziv = a.getNaziv();
        this.adresa = a.getAdresa();
        this.prosecnaOcena = a.getProsecnaOcena();
        this.opis = a.getOpis();
        this.cenaPregleda = a.getCenaPregleda();
        this.cenaSavetovanja = a.getCenaSavetovanja();
        this.longitude = a.getLongitude();
        this.latitude = a.getLatitude();
    }

    public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getCenaPregleda() {
		return cenaPregleda;
	}

	public void setCenaPregleda(Double cenaPregleda) {
		this.cenaPregleda = cenaPregleda;
	}

	public Double getCenaSavetovanja() {
		return cenaSavetovanja;
	}

	public void setCenaSavetovanja(Double cenaSavetovanja) {
		this.cenaSavetovanja = cenaSavetovanja;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(Double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

}
