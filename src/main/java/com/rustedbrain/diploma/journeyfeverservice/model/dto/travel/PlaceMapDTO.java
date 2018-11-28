package com.rustedbrain.diploma.journeyfeverservice.model.dto.travel;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.Place;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.PlaceType;

import java.util.Objects;

public class PlaceMapDTO {

    private String name;
    private float rating;
    private double latitude;
    private double longitude;
    private PlaceType type;

    public PlaceMapDTO() {
    }

    public PlaceMapDTO(Place place) {
        this.name = place.getName();
        this.latitude = place.getLatitude();
        this.longitude = place.getLongitude();
        this.type = place.getPlaceType();
    }

    public PlaceMapDTO(String name, double latitude, double longitude, PlaceType type) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    public PlaceType getType() {
        return type;
    }

    public void setType(PlaceType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceMapDTO that = (PlaceMapDTO) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude);
    }

    @Override
    public String toString() {
        return "PlaceMapDTO{" +
                "name='" + name + '\'' +
                ", rating=" + rating +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", type=" + type +
                "} " + super.toString();
    }
}
