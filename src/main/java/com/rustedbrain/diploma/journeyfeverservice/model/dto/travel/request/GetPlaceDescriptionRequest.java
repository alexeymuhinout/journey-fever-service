package com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.request;

import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.LatLngDTO;

public class GetPlaceDescriptionRequest {

    private LatLngDTO latLngDTO;

    public GetPlaceDescriptionRequest() {
    }

    public GetPlaceDescriptionRequest(LatLngDTO latLngDTO) {
        this.latLngDTO = latLngDTO;
    }

    public LatLngDTO getLatLngDTO() {
        return latLngDTO;
    }

    public void setLatLngDTO(LatLngDTO latLngDTO) {
        this.latLngDTO = latLngDTO;
    }


}