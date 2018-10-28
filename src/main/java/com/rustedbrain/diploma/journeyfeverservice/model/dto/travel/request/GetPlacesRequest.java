package com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.request;

import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.LatLngBoundsDTO;

public class GetPlacesRequest {

    private LatLngBoundsDTO latLngBoundsDTO;

    public GetPlacesRequest() {
    }

    public GetPlacesRequest(LatLngBoundsDTO latLngBoundsDTO) {
        this.latLngBoundsDTO = latLngBoundsDTO;
    }

    public LatLngBoundsDTO getLatLngBoundsDTO() {
        return latLngBoundsDTO;
    }

    public void setLatLngBoundsDTO(LatLngBoundsDTO latLngBoundsDTO) {
        this.latLngBoundsDTO = latLngBoundsDTO;
    }

}
