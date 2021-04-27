package com.team_08.ISAproj.model;

import org.hibernate.annotations.Formula;

import com.team_08.ISAproj.dto.PregledDTO;

import javax.persistence.*;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "PREGLED")
public class Pregled {
	@Id
	@SequenceGenerator(name="PregledSeqGen", sequenceName = "PregledSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PregledSeqGen")
	private Long id;
	@Column(name = "VREME")
	private LocalDateTime vreme;
	@Column(name = "TRAJANJE")
	private Long trajanje;
	@Column(name = "KRAJ")
	private LocalDateTime kraj;
	@Column(name = "DIJAGNOZA")
	private String dijagnoza;
	@Column(name = "PREGLED_OBAVLJEN")
	private boolean pregledObavljen;
	@Column(name = "PREGLED_ZAKAZAN")
	private boolean pregledZakazan;
	
	// connections
	@ManyToOne(fetch = FetchType.EAGER)
	private ZdravstveniRadnik zdravstveniRadnik;
	@ManyToOne(fetch = FetchType.EAGER)
	private Pacijent pacijent;
	@ManyToOne(fetch = FetchType.EAGER)
	private Apoteka apoteka;
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Lek> preporuceniLekovi;
	
	// constructors
	public Pregled(LocalDateTime vreme, Long trajanje, LocalDateTime kraj, String dijagnoza, boolean pregledObavljen,
			boolean pregledZakazan, ZdravstveniRadnik zdravstveniRadnik, Pacijent pacijent, Apoteka apoteka, Set<Lek> preporuceniLekovi) {
		super();
		this.vreme = vreme;
		this.trajanje = trajanje;
		this.kraj = kraj;
		this.dijagnoza = dijagnoza;
		this.pregledObavljen = pregledObavljen;
		this.zdravstveniRadnik = zdravstveniRadnik;
		this.pacijent = pacijent;
		this.apoteka = apoteka;
		this.preporuceniLekovi = preporuceniLekovi;
		this.pregledZakazan = pregledZakazan;
	}

	public Pregled() {
	}

	public Pregled(PregledDTO pDTO) {
		this.vreme = pDTO.getStart();
		this.kraj = pDTO.getEnd();
		this.pregledObavljen = false;
		this.pregledZakazan = false;
	}
	// getters and setters

	public boolean isPregledZakazan() {
		return pregledZakazan;
	}

	public void setPregledZakazan(boolean pregledZakazan) {
		this.pregledZakazan = pregledZakazan;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getVreme() {return vreme;}
	public void setVreme(LocalDateTime vreme) {this.vreme = vreme;}
	public Long getTrajanje() {return trajanje;}
	public void setTrajanje(Long trajanje) {this.trajanje = trajanje;}
	public LocalDateTime getKraj() {return kraj;}
	public void setKraj(LocalDateTime kraj) {this.kraj = kraj;}
	public String getDijagnoza() {return dijagnoza;}
	public void setDijagnoza(String dijagnoza) {this.dijagnoza = dijagnoza;}
	public boolean isPregledObavljen() {return pregledObavljen;}
	public void setPregledObavljen(boolean pregledObavljen) {this.pregledObavljen = pregledObavljen;}
	public ZdravstveniRadnik getDermatolog() {return zdravstveniRadnik;}
	public void setDermatolog(ZdravstveniRadnik zdravstveniRadnik) {this.zdravstveniRadnik = zdravstveniRadnik;}
	public Pacijent getPacijent() {return pacijent;}
	public void setPacijent(Pacijent pacijent) {this.pacijent = pacijent;}
	public Apoteka getApoteka() {return apoteka;}
	public void setApoteka(Apoteka apoteka) {this.apoteka = apoteka;}
	public Set<Lek> getPreporuceniLekovi() {return preporuceniLekovi;}
	public void setPreporuceniLekovi(Set<Lek> preporuceniLekovi) {this.preporuceniLekovi = preporuceniLekovi;}
	
}
