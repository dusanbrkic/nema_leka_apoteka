package com.team_08.ISAproj.dto;

public class NarudzbenicaDTO {

	private Long   id;
	private String ApotekaNaziv;
	private String DatumPreuzimanja;
	private String Lekovi;
	public NarudzbenicaDTO(Long id, String apotekaNaziv, String datumPreuzimanja, String lekovi) {
		super();
		this.id = id;
		ApotekaNaziv = apotekaNaziv;
		DatumPreuzimanja = datumPreuzimanja;
		Lekovi = lekovi;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getApotekaNaziv() {
		return ApotekaNaziv;
	}
	public void setApotekaNaziv(String apotekaNaziv) {
		ApotekaNaziv = apotekaNaziv;
	}
	public String getDatumPreuzimanja() {
		return DatumPreuzimanja;
	}
	public void setDatumPreuzimanja(String datumPreuzimanja) {
		DatumPreuzimanja = datumPreuzimanja;
	}
	public String getLekovi() {
		return Lekovi;
	}
	public void setLekovi(String lekovi) {
		Lekovi = lekovi;
	}
	
	
	
}
