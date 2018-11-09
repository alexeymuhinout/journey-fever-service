package com.rustedbrain.diploma.journeyfeverservice.model.persistence.security;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.DatabaseEntity;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.Comment;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.Place;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.Travel;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user", schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = {"firstName", "lastName"})})
public class User extends DatabaseEntity {

    @Column(name = "firstName", length = 64)
    private String firstName;

    @Column(name = "lastName", length = 64)
    private String lastName;

    @Column(name = "username", unique = true, length = 64)
    private String username;

    @Column(name = "password", length = 64)
    private String password;

    @Column(name = "email", unique = true, length = 128)
    private String email;

    @Enumerated
    @Column(columnDefinition = "smallint", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Travel> travels;

    @ManyToMany(fetch = FetchType.LAZY, cascade = javax.persistence.CascadeType.ALL)
    @JoinTable(name = "userTravel", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "travelId"))
    private List<Travel> sharedTravels;

    @ManyToMany(fetch = FetchType.LAZY, cascade = javax.persistence.CascadeType.ALL)
    @JoinTable(name = "userIgnoredPlace", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "placeId"))
    private List<Place> ignoredPlaces;

    public List<Place> getIgnoredPlaces() {
        return ignoredPlaces;
    }

    public void setIgnoredPlaces(List<Place> ignoredPlaces) {
        this.ignoredPlaces = ignoredPlaces;
    }

    public List<Travel> getSharedTravels() {
        return sharedTravels;
    }

    public void setSharedTravels(List<Travel> sharedTravels) {
        this.sharedTravels = sharedTravels;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Travel> getTravels() {
        return travels;
    }

    public void setTravels(List<Travel> travels) {
        this.travels = travels;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", role=" + role +
                "} " + super.toString();
    }
}
