package com.team_08.ISAproj.dto;

import java.time.LocalDateTime;

import com.team_08.ISAproj.model.Odsustvo;

public class OdsustvoDTO {

	private Long id;
	private String username;
	private LocalDateTime pocetak;
	private LocalDateTime kraj;
	private String status;
	private String cookie;
	private String razlog;
	public OdsustvoDTO() {
		
	}
	public OdsustvoDTO(Odsustvo o) {
		id = o.getId();
		pocetak = o.getPocetak();
		kraj = o.getKraj();
		status = o.getStatus();
		username = o.getZdravstveniRadnik().getUsername();
	}
	
	public String getRazlog() {
		return razlog;
	}
	public void setRazlog(String razlog) {
		this.razlog = razlog;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
