package com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.DatabaseEntity;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.security.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "travel", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")})
public class Travel extends DatabaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "name", length = 256)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = javax.persistence.CascadeType.ALL)
    @JoinTable(name = "travelShowplace", joinColumns = @JoinColumn(name = "travelId"), inverseJoinColumns = @JoinColumn(name = "showplaceId"))
    private List<Showplace> showplaces;

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

    public List<Showplace> getShowplaces() {
        return showplaces;
    }

    public void setShowplaces(List<Showplace> showplaces) {
        this.showplaces = showplaces;
    }

    @Override
    public String toString() {
        return "Travel{" +
                "user=" + user +
                ", name='" + name + '\'' +
                ", showplaces=" + showplaces +
                ", sharedToUsers=" + sharedToUsers +
                "} " + super.toString();
    }
}
