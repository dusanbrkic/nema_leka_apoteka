package com.team_08.ISAproj.model;

public class CookieToken {
    public static String createTokenValue(String username, String password) {
        return username + "-" + password;
    }
}
