package com.team_08.ISAproj.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.team_08.ISAproj.model.Apoteka;
import org.springframework.stereotype.Repository;
@Repository
public class IMApotekaRepository implements ApotekaRepository {

	private static AtomicLong counter = new AtomicLong();
	
	private final HashMap<Long,Apoteka> apoteke = new HashMap<Long,Apoteka>();

	@Override
	public Collection<Apoteka> findAll() {
		return this.apoteke.values();
	}

	@Override
	public Apoteka findOne(Long id) {
		Apoteka a = new Apoteka();
		a.setId(id);
		a.setAdresa("Brata Balate 12");
		a.setOpis("Najjaca apoteka na Balkanu!");
		a.setProsecnaOcena(4.78);
		apoteke.put(a.getId(), a);
		return this.apoteke.get(id);
	}

	@Override
	public Apoteka create(Apoteka apoteka) {
		Long id = apoteka.getId();
		if(id == null) {
			id = counter.incrementAndGet();
			apoteka.setId(id);
		}
		
		this.apoteke.put(id, apoteka);
		return apoteka;
	}

}
