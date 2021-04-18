package com.team_08.ISAproj.model;

import javax.persistence.*;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "SAVETOVANJE")
public class Savetovanje {
	@Id
	@SequenceGenerator(name="SavetovanjeSeqGen", sequenceName = "SavetovanjeSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SavetovanjeSeqGen")
	private Long id;
	@Column(name = "VREME")
	private LocalDateTime vreme;
	@Column(name = "TRAJANJE")
	private Long trajanje;
	@Column(name = "KRAJ")
	private LocalDateTime kraj;
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
	public Savetovanje(LocalDateTime vreme, Long trajanje, LocalDateTime kraj, String dijagnoza,
			boolean savetovanjeObavljeno, Apoteka apoteka, Farmaceut farmaceut, Pacijent pacijent,
			Set<Lek> preporuceniLekovi) {
		super();
		this.vreme = vreme;
		this.trajanje = trajanje;
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
	public LocalDateTime getVreme() {return vreme;}
	public void setVreme(LocalDateTime vreme) {this.vreme = vreme;}
	public Long getTrajanje() {return trajanje;}
	public void setTrajanje(Long trajanje) {this.trajanje = trajanje;}
	public LocalDateTime getKraj() {return kraj;}
	public void setKraj(LocalDateTime kraj) {this.kraj = kraj;}
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
