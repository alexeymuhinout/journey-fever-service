package com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.DatabaseEntity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "placePhoto")
public class PlacePhoto extends DatabaseEntity {

    @ManyToOne(targetEntity = Place.class, fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name="parentPlaceId")
    private Place place;

    @Lob
    @Column
    private byte[] data;

    public PlacePhoto() {
    }

    public PlacePhoto(byte[] data) {
        this.data = data;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlacePhoto that = (PlacePhoto) o;
        return Objects.equals(place, that.place) &&
                Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(place);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "PlacePhoto{" +
                "place=" + place +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
