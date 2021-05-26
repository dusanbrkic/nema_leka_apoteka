package com.team_08.ISAproj.model;

import com.team_08.ISAproj.dto.KorisnikDTO;
import com.team_08.ISAproj.model.enums.KorisnickaRola;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "OCENA")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Ocena {
	@Id
    @SequenceGenerator(name = "OcenaSeqGen", sequenceName = "OcenaSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OcenaSeqGen")
    private Long id;
	
    @Column(name = "OCENA")
    private Integer ocena;
    
    @Column(name = "DATUM")
    private LocalDateTime datum;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Pacijent pacijent;

    // constructors
    public Ocena(Integer o, LocalDateTime d, Pacijent p) {
    	this.ocena = o;
    	this.datum = d;
    	this.pacijent = p;
    }
    public Ocena() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOcena() {
		return ocena;
	}

	public void setOcena(Integer ocena) {
		this.ocena = ocena;
	}

	public LocalDateTime getDatum() {
		return datum;
	}

	public void setDatum(LocalDateTime datum) {
		this.datum = datum;
	}
}
