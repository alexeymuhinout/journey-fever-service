package com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.DatabaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "place", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")})
public class Place extends DatabaseEntity {

    @Enumerated
    @Column(columnDefinition = "smallint", nullable = false)
    private PlaceType placeType;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Column(name = "description", length = 1024)
    private String description;

    @Column(name = "rating")
    private float rating;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "latitude")
    private double latitude;

    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PlacePhoto> photos;

    @ManyToMany(mappedBy = "places", cascade = {CascadeType.ALL})
    private List<Travel> travels;

    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY)
    private List<Comment> comments;

    public PlaceType getPlaceType() {
        return placeType;
    }

    public void setPlaceType(PlaceType placeType) {
        this.placeType = placeType;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

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

    public List<PlacePhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PlacePhoto> photos) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Place place = (Place) o;

        return Double.compare(place.longitude, longitude) == 0 && Double.compare(place.latitude, latitude) == 0 && name.equals(place.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
