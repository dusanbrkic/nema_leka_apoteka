package com.team_08.ISAproj.dto;

import com.team_08.ISAproj.model.enums.KorisnickaRola;

public class CookieRoleDTO {
    private String cookie;
    private KorisnickaRola rola;
    private Boolean firstLogin;

    public Boolean getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(Boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public CookieRoleDTO(String cookie, KorisnickaRola rola) {
        this.cookie = cookie;
        this.rola = rola;
    }

    public CookieRoleDTO(String cookie, KorisnickaRola rola, Boolean firstLogin) {
        this.cookie = cookie;
        this.rola = rola;
        this.firstLogin = firstLogin;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public KorisnickaRola getRola() {
        return rola;
    }

    public void setRola(KorisnickaRola rola) {
        this.rola = rola;
    }
}
