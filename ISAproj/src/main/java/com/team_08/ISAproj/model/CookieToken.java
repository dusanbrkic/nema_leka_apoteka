package com.team_08.ISAproj.model;

import com.team_08.ISAproj.exceptions.CookieParseException;

public class CookieToken {
    private String username;
    private String password;
    private String value;

    public CookieToken(String username, String password) {
        this.username = username;
        this.password = password;
        this.value = createTokenValue(username, password);
    }

    private String createTokenValue(String username, String password) {
        return username + "-" + password;
    }

    public static CookieToken parseToken(String token) throws CookieParseException {
        if (!token.contains("-"))
            throw new CookieParseException();
        String[] values = token.split("-");
        return new CookieToken(values[0], values[1]);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
