package com.team_08.ISAproj.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity(name="ADMIN_APOTEKE")
public class AdminApoteke extends Korisnik{
	
	// connections
	@ManyToOne(fetch = FetchType.EAGER)
	private Apoteka apoteka;
	
	
	
	public AdminApoteke(Apoteka apoteka) {
		super();
		this.apoteka = apoteka;
	}

	public AdminApoteke() {

	}

	void slobodniTerminDermatolog(Dermatolog dermatolog, Date datumPregleda, Date kraj, int cena)
	{
		// to do
	}
	
	void pretraziLekove(String sifra)
	{
		// to do
	}
	
	void dodajLek(ApotekaLek lek)
	{
		// to do
	}
	
	void pretraziFarmaceuta(String username)
	{
		// to do
	}
	
	void obrisiLek(String sifra)
	{
		// to do
	}
	
	void promeniLek(String sifra)
	{
		// to do
	}
	
	void kreirajFarmaceuta(Farmaceut f)
	{
		// to do
	}
	
	void obrisiFarmaceuta(String username)
	{
		// to do
	}
	
	void pretraziDermatologa(String username)
	{
		// to do
	}
	
	void kreirajDermatologa(Dermatolog d)
	{
		// to do
	}
	
	void obrisiDermatologa(String username)
	{
		// to do
	}
	
	void ispisUpita(String sifraLeka)
	{
		// to do
	}
	
	void kreirajNarudzbinu(Narudzbenica n)
	{
		// to do
	}
	
	void prikazNarudzbina()
	{
		// to do
	}
	
	void definisiAkciju(Promocija p)
	{
		// to do
	}
}
