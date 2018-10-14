package com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.request;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.PlaceType;

import java.util.List;

public class AddPlaceRequest {

    private String name;
    private String description;
    private float rating;
    private double latitude;
    private double longitude;
    private List<byte[]> photoList;
    private PlaceType placeType;

    public AddPlaceRequest() {
    }

    public AddPlaceRequest(String name, String description, float rating, double latitude, double longitude, List<byte[]> photoList, PlaceType placeType) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoList = photoList;
        this.placeType = placeType;
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

    public List<byte[]> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<byte[]> photoList) {
        this.photoList = photoList;
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public void setPlaceType(PlaceType placeType) {
        this.placeType = placeType;
    }
}
