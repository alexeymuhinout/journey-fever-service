package com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.DatabaseEntity;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.security.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "travel", schema = "public")
public class Travel extends DatabaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "name", length = 256)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = javax.persistence.CascadeType.ALL)
    @JoinTable(name = "travelPlace", joinColumns = @JoinColumn(name = "travelId"), inverseJoinColumns = @JoinColumn(name = "placeId"))
    private List<Place> places;

    @ManyToMany(mappedBy = "sharedTravels", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<User> sharedToUsers;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Travel travel = (Travel) o;

        return user.equals(travel.user) && name.equals(travel.name);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Travel{" +
                "user=" + user +
                ", name='" + name + '\'' +
                ", places=" + places +
                ", sharedToUsers=" + sharedToUsers +
                "} " + super.toString();
    }
}
