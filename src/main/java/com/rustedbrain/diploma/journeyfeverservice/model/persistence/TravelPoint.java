package com.rustedbrain.diploma.journeyfeverservice.model.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "travelPoint", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")})
public class TravelPoint extends DatabaseEntity {

    private String name;
    private String description;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "latitude")
    private double latitude;
}
