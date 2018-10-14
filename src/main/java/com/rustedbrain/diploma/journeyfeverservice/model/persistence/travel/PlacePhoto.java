package com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.DatabaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "placePhoto")
public class PlacePhoto extends DatabaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
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
}
