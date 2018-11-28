package com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.request;

import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.LatLngBoundsDTO;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.TripsMapPlacesFilterDTO;

public class GetPlacesRequest {

    private LatLngBoundsDTO latLngBoundsDTO;
    private TripsMapPlacesFilterDTO tripsMapPlacesFilterDTO;

    public GetPlacesRequest() {
    }

    public GetPlacesRequest(LatLngBoundsDTO latLngBoundsDTO) {
        this.latLngBoundsDTO = latLngBoundsDTO;
    }

    public TripsMapPlacesFilterDTO getTripsMapPlacesFilterDTO() {
        return tripsMapPlacesFilterDTO;
    }

    public void setTripsMapPlacesFilterDTO(TripsMapPlacesFilterDTO tripsMapPlacesFilterDTO) {
        this.tripsMapPlacesFilterDTO = tripsMapPlacesFilterDTO;
    }

    public LatLngBoundsDTO getLatLngBoundsDTO() {
        return latLngBoundsDTO;
    }

    public void setLatLngBoundsDTO(LatLngBoundsDTO latLngBoundsDTO) {
        this.latLngBoundsDTO = latLngBoundsDTO;
    }
}