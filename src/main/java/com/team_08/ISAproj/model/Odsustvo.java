package com.team_08.ISAproj.model;

import javax.persistence.*;

import com.team_08.ISAproj.dto.OdsustvoDTO;

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
    @Column(name = "STATUS")
    private String status;
    @Column(name = "RAZLOG")
    private String razlog;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private ZdravstveniRadnik zdravstveniRadnik;
    

	public Odsustvo(LocalDateTime start, LocalDateTime end,String status1,ZdravstveniRadnik zr) {
    	
        pocetak = start;
        kraj = end;
        status = status1;
        zdravstveniRadnik = zr;
    }
    public Odsustvo(LocalDateTime start, LocalDateTime end) {
        pocetak = start;
        kraj = end;
    }

    public Odsustvo() {
    }


	public Odsustvo(OdsustvoDTO odsustvoDTO) {
		pocetak = odsustvoDTO.getPocetak();
		kraj = odsustvoDTO.getKraj();
		status = odsustvoDTO.getStatus();
	}
	public String getRazlog() {
		return razlog;
	}
	public void setRazlog(String razlog) {
		this.razlog = razlog;
	}
	public ZdravstveniRadnik getZdravstveniRadnik() {
		return zdravstveniRadnik;
	}
	public void setZdravstveniRadnik(ZdravstveniRadnik zdravstveniRadnik) {
		this.zdravstveniRadnik = zdravstveniRadnik;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public void updateOdustov(OdsustvoDTO odsustvoDTO) {
		pocetak = odsustvoDTO.getPocetak();
		kraj = odsustvoDTO.getKraj();
		status = odsustvoDTO.getStatus();
		razlog = odsustvoDTO.getRazlog();
		
	}
}
