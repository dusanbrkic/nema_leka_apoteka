package com.team_08.ISAproj.model;

import org.hibernate.annotations.Formula;

import com.team_08.ISAproj.dto.PregledDTO;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "PREGLED")
public class Pregled {
    @Id
    @SequenceGenerator(name = "PregledSeqGen", sequenceName = "PregledSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PregledSeqGen")
    private Long id;
    @Column(name = "VREME")
    private LocalDateTime vreme;
    @Column(name = "TRAJANJE")
    private Long trajanje;
    @Column(name = "PACIJENT_SE_NIJE_POJAVIO")
    private Boolean pacijentSeNijePojavio;
    @Column(name = "KRAJ")
    private LocalDateTime kraj;
    @Column(name = "DIJAGNOZA")
    private String dijagnoza;
    @Column(name = "PREGLED_OBAVLJEN")
    private boolean pregledObavljen;
    @Column(name = "PREGLED_ZAKAZAN")
    private boolean pregledZakazan;
    @Column(name = "CENA")
    private Double cena;

    
    @Version
    private int version;
    
    
    
    // connections
    @ManyToOne(fetch = FetchType.EAGER)
    private ZdravstveniRadnik zdravstveniRadnik;
    @ManyToOne(fetch = FetchType.EAGER)
    private Pacijent pacijent;
    @ManyToOne(fetch = FetchType.EAGER)
    private Apoteka apoteka;
    @OneToMany(mappedBy = "pregled", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PregledLek> preporuceniLekovi;

    @Version
	private Long version;
    // constructors
    public Pregled(LocalDateTime vreme, Long trajanje, LocalDateTime kraj, String dijagnoza, boolean pregledObavljen,
                   boolean pregledZakazan, ZdravstveniRadnik zdravstveniRadnik, Pacijent pacijent, Apoteka apoteka, Set<PregledLek> preporuceniLekovi) {
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
        this.version = 0L;
    }
    // getters and setters

    public boolean isPregledZakazan() {
        return pregledZakazan;
    }

    public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
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

    public LocalDateTime getVreme() {
        return vreme;
    }

    public void setVreme(LocalDateTime vreme) {
        this.vreme = vreme;
    }

    public Long getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(Long trajanje) {
        this.trajanje = trajanje;
    }

    public LocalDateTime getKraj() {
        return kraj;
    }

    public void setKraj(LocalDateTime kraj) {
        this.kraj = kraj;
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

    public ZdravstveniRadnik getDermatolog() {
        return zdravstveniRadnik;
    }

    public void setDermatolog(ZdravstveniRadnik zdravstveniRadnik) {
        this.zdravstveniRadnik = zdravstveniRadnik;
    }

    public Pacijent getPacijent() {
        return pacijent;
    }

    public void setPacijent(Pacijent pacijent) {
        this.pacijent = pacijent;
    }

    public Apoteka getApoteka() {
        return apoteka;
    }

    public void setApoteka(Apoteka apoteka) {
        this.apoteka = apoteka;
    }

    public Set<PregledLek> getPreporuceniLekovi() {
        return preporuceniLekovi;
    }

    public void setPreporuceniLekovi(Set<PregledLek> preporuceniLekovi) {
        this.preporuceniLekovi = preporuceniLekovi;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public ZdravstveniRadnik getZdravstveniRadnik() {
        return zdravstveniRadnik;
    }

    public void setZdravstveniRadnik(ZdravstveniRadnik zdravstveniRadnik) {
        this.zdravstveniRadnik = zdravstveniRadnik;
    }

    public Boolean getPacijentSeNijePojavio() {
        return pacijentSeNijePojavio;
    }

    public void setPacijentSeNijePojavio(Boolean pacijentSeNijePojavio) {
        this.pacijentSeNijePojavio = pacijentSeNijePojavio;
    }
}
