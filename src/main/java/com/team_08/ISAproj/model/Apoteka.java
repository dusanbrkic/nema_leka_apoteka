package com.team_08.ISAproj.model;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team_08.ISAproj.dto.ApotekaDTO;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "APOTEKA")
public class Apoteka {
    @Id
    @SequenceGenerator(name = "ApotekaSeqGen", sequenceName = "ApotekaSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ApotekaSeqGen")
    private Long id;
    @Column(name = "NAZIV")
    private String naziv;
    @Column(name = "ADRESA")
    private String adresa;
    @Column(name = "LATITUDE")
    private Double latitude;
    @Column(name = "LONGITUDE")
    private Double longitude;
    @Column(name = "PROSECNA_OCENA")
    private Double prosecnaOcena;
    @Column(name = "CENA_PREGLEDA")
    private Double cenaPregleda;
    @Column(name = "CENA_SAVETOVANJA")
    private Double cenaSavetovanja;
    @Column(name = "OPIS")
    private String opis;
    
    // connections
    @OneToMany(mappedBy = "apoteka", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Pretplata> pretplate = new HashSet<Pretplata>();
    @OneToMany(mappedBy = "apoteka", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DermatologApoteka> dermatolozi = new HashSet<DermatologApoteka>();
    @OneToMany(mappedBy = "apoteka", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Farmaceut> farmaceuti = new HashSet<Farmaceut>();
    @OneToMany(mappedBy = "apoteka", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Pregled> pregledi = new HashSet<Pregled>();
    @OneToMany(mappedBy = "apoteka", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Pregled> savetovanja = new HashSet<Pregled>();
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Pacijent> pretplaceniKorisnici = new HashSet<Pacijent>();
    @OneToMany(mappedBy = "apoteka", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ApotekaLek> lekovi = new HashSet<ApotekaLek>();
    @OneToMany(mappedBy = "apoteka", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Narudzbenica> narudzbenice = new HashSet<Narudzbenica>();
    @OneToMany(mappedBy = "apoteka", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Promocija> promocije = new HashSet<Promocija>();
    @OneToMany(mappedBy = "apoteka", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AdminApoteke> admini = new HashSet<AdminApoteke>();
    @OneToMany(mappedBy = "apoteka", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ZahtevLek> zahtevLekovi = new HashSet<ZahtevLek>();
    //getters and setters

    public Long getId() {
        return id;
    }

    public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Set<ZahtevLek> getZahtevLekovi() {
		return zahtevLekovi;
	}

	public void setZahtevLekovi(Set<ZahtevLek> zahtevLekovi) {
		this.zahtevLekovi = zahtevLekovi;
	}

	public Set<Pretplata> getPretplate() {
		return pretplate;
	}

	public void setPretplate(Set<Pretplata> pretplate) {
		this.pretplate = pretplate;
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

    public void setProsecnaOcena(Double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
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

    public double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Set<DermatologApoteka> getDermatolozi() {
        return dermatolozi;
    }

    public void setDermatolozi(Set<DermatologApoteka> dermatolozi) {
        this.dermatolozi = dermatolozi;
    }

    public Set<Farmaceut> getFarmaceuti() {
        return farmaceuti;
    }

    public void setFarmaceuti(Set<Farmaceut> farmaceuti) {
        this.farmaceuti = farmaceuti;
    }

    public Set<Pregled> getPregledi() {
        return pregledi;
    }

    public void setPregledi(Set<Pregled> pregledi) {
        this.pregledi = pregledi;
    }

    public Set<Pregled> getSavetovanja() {
        return savetovanja;
    }

    public void setSavetovanja(Set<Pregled> savetovanja) {
        this.savetovanja = savetovanja;
    }

    public Set<Pacijent> getPretplaceniKorisnici() {
        return pretplaceniKorisnici;
    }

    public void setPretplaceniKorisnici(Set<Pacijent> pretplaceniKorisnici) {
        this.pretplaceniKorisnici = pretplaceniKorisnici;
    }

    public Set<ApotekaLek> getLekovi() {
        return lekovi;
    }

    public void setLekovi(Set<ApotekaLek> lekovi) {
        this.lekovi = lekovi;
    }

    public Set<Narudzbenica> getNarudzbenice() {
        return narudzbenice;
    }

    public void setNarudzbenice(Set<Narudzbenica> narudzbenice) {
        this.narudzbenice = narudzbenice;
    }

    public Set<Promocija> getPromocije() {
        return promocije;
    }

    public void setPromocije(Set<Promocija> promocije) {
        this.promocije = promocije;
    }

    public Set<AdminApoteke> getAdmini() {
        return admini;
    }

    public void setAdmini(Set<AdminApoteke> admini) {
        this.admini = admini;
    }

    public Apoteka(Long id, String naziv, String adresa, double prosecnaOcena, String opis) {
        this.id = id;
        this.naziv = naziv;
        this.adresa = adresa;
        this.prosecnaOcena = prosecnaOcena;
        this.opis = opis;
    }

    public Apoteka(Long id) {
        this.id = id;
    }

    public Apoteka(ApotekaDTO apotekaDTO) {
        this.naziv = apotekaDTO.getNaziv();
        this.adresa = apotekaDTO.getAdresa();
        this.opis = apotekaDTO.getOpis();
        this.prosecnaOcena = apotekaDTO.getProsecnaOcena();
        this.cenaPregleda = apotekaDTO.getCenaPregleda();
        this.cenaSavetovanja = apotekaDTO.getCenaSavetovanja();
        this.longitude = apotekaDTO.getLongitude();
        this.latitude = apotekaDTO.getLatitude();
    }

    public Apoteka() {
        // TODO Auto-generated constructor stub
    }

    public Apoteka(long l) {
		this.id = l;
	}

	@Override
    public String toString() {
        return "Apoteka [id=" + id + ", naziv=" + naziv + ", adresa=" + adresa + ", prosecnaOcena=" + prosecnaOcena
                + ", opis=" + opis + ", dermatolozi=" + dermatolozi + ", farmaceuti=" + farmaceuti + "]";
    }

	public void updateInfo(ApotekaDTO apotekaDTO) {
        this.naziv = apotekaDTO.getNaziv();
        this.adresa = apotekaDTO.getAdresa();
        this.opis = apotekaDTO.getOpis();
        this.prosecnaOcena = apotekaDTO.getProsecnaOcena();
        this.cenaPregleda = apotekaDTO.getCenaPregleda();
        this.cenaSavetovanja = apotekaDTO.getCenaSavetovanja();
        this.longitude = apotekaDTO.getLongitude();
        this.latitude = apotekaDTO.getLatitude();
	}

}