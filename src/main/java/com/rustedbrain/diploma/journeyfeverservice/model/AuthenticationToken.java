package com.rustedbrain.diploma.journeyfeverservice.model;


import java.io.Serializable;

public class AuthenticationToken implements Serializable, Comparable<AuthenticationToken> {

    private String username;
    private String password;

    public AuthenticationToken() {
    }

    public AuthenticationToken(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthenticationToken that = (AuthenticationToken) o;

        return username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return "AuthenticationToken{" +
                "username='" + username + '\'' +
                '}';
    }

    @Override
    public int compareTo(AuthenticationToken o) {
        return this.getUsername().compareTo(o.getUsername());
    }
}
