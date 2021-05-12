package com.team_08.ISAproj.model;
import javax.persistence.*;
import java.sql.Date;

@Entity(name = "PRETPLATA")
public class Pretplata {

    @Id
    @SequenceGenerator(name = "PretplataSeqGen", sequenceName = "PretplataSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PretplataSeqGen")
    private Long id;
	
    @ManyToOne(fetch = FetchType.EAGER)
    private Pacijent pacijent;
    @ManyToOne(fetch = FetchType.EAGER)
    private Apoteka apoteka;
    
    
    public Pretplata() {
    	
    	
    }
	public Pretplata(Long id, Pacijent pacijent, Apoteka apoteka) {
		super();
		this.id = id;
		this.pacijent = pacijent;
		this.apoteka = apoteka;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

    

}
