package com.rustedbrain.diploma.journeyfeverservice.model.dto.travel;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.security.User;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.Place;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.Travel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TravelDTO implements Serializable {

    private String ownerUsername;
    private String name;
    private boolean archived;
    private List<String> sharedToUsersUsernames = new ArrayList<>();
    private List<PlaceMapDTO> places = new ArrayList<>();

    public TravelDTO() {
    }

    public TravelDTO(Travel travel) {
        this.ownerUsername = travel.getUser().getUsername();
        this.name = travel.getName();
        this.sharedToUsersUsernames = travel.getSharedToUsers() != null ? travel.getSharedToUsers().stream().map(User::getUsername).collect(Collectors.toList()) : new ArrayList<>();
        this.archived = travel.isArchived();
        //this.shared = travel.getSharedToUsers() != null && travel.getSharedToUsers().stream().map(User::getUsername).anyMatch(userName::equals);
        if (travel.getPlaces() != null) {
            for (Place place : travel.getPlaces()) {
                this.places.add(new PlaceMapDTO(place));
            }
        }
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public List<String> getSharedToUsersUsernames() {
        return sharedToUsersUsernames;
    }

    public void setSharedToUsersUsernames(List<String> sharedToUsersUsernames) {
        this.sharedToUsersUsernames = sharedToUsersUsernames;
    }

    public List<PlaceMapDTO> getPlaces() {
        return places;
    }

    public void setPlaces(List<PlaceMapDTO> places) {
        this.places = places;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TravelDTO travelDTO = (TravelDTO) o;
        return Objects.equals(ownerUsername, travelDTO.ownerUsername) &&
                Objects.equals(name, travelDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerUsername, name);
    }

    @Override
    public String toString() {
        return "TravelDTO{" +
                "name='" + name + '\'' +
                ", archived=" + archived +
                ", places=" + places +
                '}';
    }
}
