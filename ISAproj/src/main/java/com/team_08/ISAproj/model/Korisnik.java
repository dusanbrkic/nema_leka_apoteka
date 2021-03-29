package com.team_08.ISAproj.model;

import javax.persistence.*;

import com.team_08.ISAproj.dto.KorisnikDTO;

import java.util.Date;

@Entity(name = "KORISNIK")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Korisnik {

	@Id
	@SequenceGenerator(name="KorisnikSeqGen", sequenceName = "KorisnikSeq", initialValue = 1, allocationSize = 1)
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



	// constructors
	public Korisnik() {
		super();
	}
	public Korisnik(String username, String password, String ime, String prezime, Date datumRodjenja,
			String emailAdresa) {
		super();
		this.username = username;
		this.password = password;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.emailAdresa = emailAdresa;
	}
	
	
	// getters and setters
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	public String getIme() {return ime;}
	public void setIme(String ime) {this.ime = ime;}
	public String getPrezime() {return prezime;}
	public void setPrezime(String prezime) {this.prezime = prezime;}
	public Date getDatumRodjenja() {return datumRodjenja;}
	public void setDatumRodjenja(Date datumRodjenja) {this.datumRodjenja = datumRodjenja;}
	public String getEmailAdresa() {return emailAdresa;}
	public void setEmailAdresa(String emailAdresa) {this.emailAdresa = emailAdresa;}

	public String getCookieToken() {
		return cookieTokenValue;
	}

	public void setCookieToken(String cookieToken) {
		this.cookieTokenValue = cookieToken;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	
	public void updateUser(KorisnikDTO kDTO) {
		this.username = kDTO.getUsername();
		this.password = kDTO.getPassword();
		this.ime = kDTO.getIme();
		this.prezime = kDTO.getPrezime();
		this.datumRodjenja = kDTO.getDatumRodjenja();
		this.emailAdresa = kDTO.getEmailAdresa();
	}
}
