package com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.DatabaseEntity;

import javax.persistence.*;
import java.sql.Blob;
import java.util.List;

@Entity
@Table(name = "showplace", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")})
public class Showplace extends DatabaseEntity {

    @Column(name = "name", length = 128)
    private String name;

    @Column(name = "description", length = 1024)
    private String description;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "latitude")
    private double latitude;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Blob> photos;

    @ManyToMany(mappedBy = "showplaces", cascade = {CascadeType.ALL})
    private List<Travel> travels;

    @OneToMany(mappedBy = "showplace", fetch = FetchType.LAZY)
    private List<Comment> comments;

    public List<Travel> getTravels() {
        return travels;
    }

    public void setTravels(List<Travel> travels) {
        this.travels = travels;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Blob> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Blob> photos) {
        this.photos = photos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Showplace{" +
                "name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
