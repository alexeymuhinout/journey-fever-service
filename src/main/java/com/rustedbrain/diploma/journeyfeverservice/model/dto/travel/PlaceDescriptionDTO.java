package com.rustedbrain.diploma.journeyfeverservice.model.dto.travel;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.security.User;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.Comment;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.Place;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.PlacePhoto;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.PlaceType;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class PlaceDescriptionDTO {

    private PlaceType type;
    private String name;
    private String description;
    private float rating;
    private LatLngDTO latLngDTO;
    private List<byte[]> photoList;
    private List<CommentDTO> commentList;
    private boolean ignoredByUser;

    public PlaceDescriptionDTO(Place place, String username) {
        this.type = place.getPlaceType();
        this.name = place.getName();
        this.description = place.getDescription();
        OptionalDouble optionalRating = place.getComments().stream().mapToDouble(Comment::getRating).average();
        this.rating = optionalRating.isPresent() ? (float) optionalRating.getAsDouble() : 0f;
        this.latLngDTO = new LatLngDTO(place.getLatitude(), place.getLongitude());
        this.photoList = place.getPhotos().stream().map(PlacePhoto::getData).collect(Collectors.toList());
        this.commentList = place.getComments().stream().map(CommentDTO::new).collect(Collectors.toList());
        this.ignoredByUser = place.getIgnoredToUsers().stream().map(User::getUsername).anyMatch(username::equals);
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

    public boolean isIgnoredByUser() {
        return ignoredByUser;
    }

    public void setIgnoredByUser(boolean ignoredByUser) {
        this.ignoredByUser = ignoredByUser;
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

    public List<CommentDTO> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentDTO> commentList) {
        this.commentList = commentList;
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