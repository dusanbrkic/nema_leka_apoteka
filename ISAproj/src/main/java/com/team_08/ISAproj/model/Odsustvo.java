package com.team_08.ISAproj.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "ODSUSTVO")
public class Odsustvo {
    @Id
    @SequenceGenerator(name = "OdsustvoSeqGen", sequenceName = "OdsustvoSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OdsustvoSeqGen")
    private Long id;
    @Column(name = "POCETAK")
    private LocalDateTime pocetak;
    @Column(name = "KRAJ")
    private LocalDateTime kraj;

    public Odsustvo(LocalDateTime start, LocalDateTime end) {
        pocetak = start;
        kraj = end;
    }

    public Odsustvo() {
    }

    public LocalDateTime getPocetak() {
        return pocetak;
    }

    public void setPocetak(LocalDateTime pocetak) {
        this.pocetak = pocetak;
    }

    public LocalDateTime getKraj() {
        return kraj;
    }

    public void setKraj(LocalDateTime kraj) {
        this.kraj = kraj;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
