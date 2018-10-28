package com.rustedbrain.diploma.journeyfeverservice.model.dto.travel;

import com.rustedbrain.diploma.journeyfeverservice.model.dto.HttpDTO;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.Place;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.PlacePhoto;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.PlaceType;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

public class PlaceDescriptionDTO extends HttpDTO {

    private PlaceType type;
    private String name;
    private String description;
    private float rating;
    private LatLngDTO latLngDTO;
    private List<byte[]> photoList;

    public PlaceDescriptionDTO() {
    }

    public PlaceDescriptionDTO(HttpStatus status, Place place) {
        this(place);
        super.setStatus(status);
    }

    public PlaceDescriptionDTO(Place place) {
        this.type = place.getPlaceType();
        this.name = place.getName();
        this.description = place.getDescription();
        this.rating = place.getRating();
        this.latLngDTO = new LatLngDTO(place.getLatitude(), place.getLongitude());
        this.photoList = place.getPhotos().stream().map(PlacePhoto::getData).collect(Collectors.toList());
    }

    public LatLngDTO getLatLngDTO() {
        return latLngDTO;
    }

    public void setLatLngDTO(LatLngDTO latLngDTO) {
        this.latLngDTO = latLngDTO;
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

    public List<byte[]> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<byte[]> photoList) {
        this.photoList = photoList;
    }

    @Override
    public String toString() {
        return "PlaceDescriptionDTO{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                '}';
    }
}