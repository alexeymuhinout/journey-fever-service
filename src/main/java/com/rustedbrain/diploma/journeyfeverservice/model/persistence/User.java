package com.rustedbrain.diploma.journeyfeverservice.model.persistence;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = {"firstName", "lastName"})})
public class User extends DatabaseEntity {

    @Column(name = "firstName", length = 75)
    private String firstName;

    @Column(name = "lastName", length = 80)
    private String lastName;

    @Column(name = "username", unique = true, length = 65)
    private String username;

    @Column(name = "password", length = 64)
    private String password;

    @Column(name = "email", unique = true, length = 115)
    private String email;

    @Enumerated
    @Column(columnDefinition = "smallint")
    private Role role;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", role=" + role +
                "} " + super.toString();
    }
}
