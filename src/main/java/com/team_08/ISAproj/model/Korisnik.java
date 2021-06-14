package com.team_08.ISAproj.model;

import javax.persistence.*;

import com.team_08.ISAproj.dto.KorisnikDTO;

import java.util.Date;

@Entity(name = "KORISNIK")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Korisnik {

    @Id
    @SequenceGenerator(name = "KorisnikSeqGen", sequenceName = "KorisnikSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KorisnikSeqGen")
    private Long id;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @Column(name = "IME")
    private String ime;
    @Column(name = "PREZIME")
    private String prezime;
    @Column(name = "DATUM_RODJENJA")
    private Date datumRodjenja;
    @Column(name = "EMAIL_ADRESA", nullable = false)
    private String emailAdresa;
    @Column(name = "TOKEN")
    private String cookieTokenValue;
    @Column(name = "FIRST_LOGIN")
    private Boolean firstLogin;
    @Column(name = "ADRESA")
    private String adresa;
    @Column(name = "GRAD")
    private String grad;
    @Column(name = "DRZAVA")
    private String drzava;
    @Column(name = "BROJ_TELEFONA")
    private String brojTelefona;


    // constructors
    public Korisnik() {
        super();
    }

    public Korisnik(Long id, String username, String password, String ime, String prezime,
                    Date datumRodjenja, String emailAdresa, String cookieTokenValue,
                    Boolean firstLogin, String adresa, String grad, String drzava, String brojTelefona) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.emailAdresa = emailAdresa;
        this.cookieTokenValue = cookieTokenValue;
        this.firstLogin = firstLogin;
        this.adresa = adresa;
        this.grad = grad;
        this.drzava = drzava;
        this.brojTelefona = brojTelefona;
    }

    public Korisnik(KorisnikDTO kDTO) {
        this.username = kDTO.getUsername();
        this.password = kDTO.getPassword();
        this.ime = kDTO.getIme();
        this.prezime = kDTO.getPrezime();
        this.datumRodjenja = kDTO.getDatumRodjenja();
        this.emailAdresa = kDTO.getAdresa();
        this.cookieTokenValue = kDTO.getCookie();
        this.firstLogin = false;
        this.adresa = kDTO.getAdresa();
        this.grad = kDTO.getGrad();
        this.drzava = kDTO.getDrzava();
        this.brojTelefona = kDTO.getBrojTelefona();

    }

    // getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getEmailAdresa() {
        return emailAdresa;
    }

    public void setEmailAdresa(String emailAdresa) {
        this.emailAdresa = emailAdresa;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public String getCookieToken() {
        return cookieTokenValue;
    }

    public String getCookieTokenValue() {
        return cookieTokenValue;
    }

    public void setCookieTokenValue(String cookieTokenValue) {
        this.cookieTokenValue = cookieTokenValue;
    }

    public Boolean getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(Boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public void korisnikDTO(String cookieToken) {
        this.cookieTokenValue = cookieToken;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void updateUser(KorisnikDTO kDTO) {
        this.ime = kDTO.getIme();
        this.prezime = kDTO.getPrezime();
        this.datumRodjenja = kDTO.getDatumRodjenja();
        this.adresa = kDTO.getAdresa();
        this.grad = kDTO.getGrad();
        this.drzava = kDTO.getDrzava();
        this.brojTelefona = kDTO.getBrojTelefona();
    }
}
