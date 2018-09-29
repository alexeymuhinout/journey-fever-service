package com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.DatabaseEntity;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.security.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "travel", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")})
public class Travel extends DatabaseEntity {

    @ManyToMany
    @JoinTable(name = "travelUser", joinColumns = @JoinColumn(name = "travelId"), inverseJoinColumns = @JoinColumn(name = "userId"))
    private List<User> users;
    @Column(name = "name")
    private String name;
    private List<TravelPoint> travelPoints;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TravelPoint> getTravelPoints() {
        return travelPoints;
    }

    public void setTravelPoints(List<TravelPoint> travelPoints) {
        this.travelPoints = travelPoints;
    }

    @Override
    public String toString() {
        return "Travel{" +
                "users=" + users +
                ", name='" + name + '\'' +
                ", travelPoints=" + travelPoints +
                '}';
    }
}
