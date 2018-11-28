package com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.request;

import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.LatLngDTO;

public class NamingPlaceRequest extends NamingRequest {

    private LatLngDTO latLngDTO;

    public NamingPlaceRequest() {
    }

    public NamingPlaceRequest(String username, LatLngDTO latLngDTO) {
        super(username);
        this.latLngDTO = latLngDTO;
    }

    public LatLngDTO getLatLngDTO() {
        return latLngDTO;
    }

    public void setLatLngDTO(LatLngDTO latLngDTO) {
        this.latLngDTO = latLngDTO;
    }
}