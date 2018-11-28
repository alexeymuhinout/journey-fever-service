package com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.DatabaseEntity;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.security.User;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "travel", schema = "public")
public class Travel extends DatabaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "name", length = 256)
    private String name;

    @Column(name = "archived")
    private boolean archived;

    @ManyToMany(fetch = FetchType.EAGER, cascade = javax.persistence.CascadeType.ALL)
    @JoinTable(name = "travelPlace", joinColumns = @JoinColumn(name = "travelId"), inverseJoinColumns = @JoinColumn(name = "placeId"))
    private List<Place> places;

    @ManyToMany(mappedBy = "sharedTravels", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<User> sharedToUsers;

    public Travel() {
    }

    public Travel(User user, String name) {
        this.user = user;
        this.name = name;
    }

    public List<User> getSharedToUsers() {
        return sharedToUsers;
    }

    public void setSharedToUsers(List<User> sharedToUsers) {
        this.sharedToUsers = sharedToUsers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Travel travel = (Travel) o;
        return Objects.equals(user, travel.user) &&
                Objects.equals(name, travel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, name);
    }

    @Override
    public String toString() {
        return "Travel{" +
                "user=" + user +
                ", name='" + name + '\'' +
                ", archived=" + archived +
                ", places=" + places +
                ", sharedToUsers=" + sharedToUsers +
                '}';
    }
}
