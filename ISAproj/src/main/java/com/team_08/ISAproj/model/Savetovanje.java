package com.team_08.ISAproj.model;

import javax.persistence.*;
import java.sql.Date;
import java.time.Duration;
import java.util.Set;

@Entity(name = "SAVETOVANJE")
public class Savetovanje {
	@Id
	@SequenceGenerator(name="SavetovanjeSeqGen", sequenceName = "SavetovanjeSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SavetovanjeSeqGen")
	private Long id;
	@Column(name = "VREME")
	private Date vreme;
	@Column(name = "TRAJANJE")
	private Duration trajanje;
	@Column(name = "CENA")
	private double cena;
	@Column(name = "KRAJ")
	private Date kraj;
	@Column(name = "DIJAGNOZA")
	private String dijagnoza;
	@Column(name = "SAVETOVANJE_OBAVLJENO")
	private boolean savetovanjeObavljeno;
	
	// connections
	@ManyToOne(fetch = FetchType.EAGER)
	private Apoteka apoteka;
	@ManyToOne(fetch = FetchType.EAGER)
	private Farmaceut farmaceut;
	@ManyToOne(fetch = FetchType.EAGER)
	private Pacijent pacijent;
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Lek> preporuceniLekovi;


	// constructors
	public Savetovanje(Date vreme, Duration trajanje, double cena, Date kraj, String dijagnoza,
			boolean savetovanjeObavljeno, Apoteka apoteka, Farmaceut farmaceut, Pacijent pacijent,
			Set<Lek> preporuceniLekovi) {
		super();
		this.vreme = vreme;
		this.trajanje = trajanje;
		this.cena = cena;
		this.kraj = kraj;
		this.dijagnoza = dijagnoza;
		this.savetovanjeObavljeno = savetovanjeObavljeno;
		this.apoteka = apoteka;
		this.farmaceut = farmaceut;
		this.pacijent = pacijent;
		this.preporuceniLekovi = preporuceniLekovi;
	}

	public Savetovanje() {

	}


	// getters and setters
	public Date getVreme() {return vreme;}
	public void setVreme(Date vreme) {this.vreme = vreme;}
	public Duration getTrajanje() {return trajanje;}
	public void setTrajanje(Duration trajanje) {this.trajanje = trajanje;}
	public double getCena() {return cena;}
	public void setCena(double cena) {this.cena = cena;}
	public Date getKraj() {return kraj;}
	public void setKraj(Date kraj) {this.kraj = kraj;}
	public String getDijagnoza() {return dijagnoza;}
	public void setDijagnoza(String dijagnoza) {this.dijagnoza = dijagnoza;}
	public boolean isSavetovanjeObavljeno() {return savetovanjeObavljeno;}
	public void setSavetovanjeObavljeno(boolean savetovanjeObavljeno) {this.savetovanjeObavljeno = savetovanjeObavljeno;}
	public Apoteka getApoteka() {return apoteka;}
	public void setApoteka(Apoteka apoteka) {this.apoteka = apoteka;}
	public Farmaceut getFarmaceut() {return farmaceut;}
	public void setFarmaceut(Farmaceut farmaceut) {this.farmaceut = farmaceut;}
	public Pacijent getPacijent() {return pacijent;}
	public void setPacijent(Pacijent pacijent) {this.pacijent = pacijent;}
	public Set<Lek> getPreporuceniLekovi() {return preporuceniLekovi;}
	public void setPreporuceniLekovi(Set<Lek> preporuceniLekovi) {this.preporuceniLekovi = preporuceniLekovi;}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
}
