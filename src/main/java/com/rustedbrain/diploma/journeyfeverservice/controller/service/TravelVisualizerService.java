package com.rustedbrain.diploma.journeyfeverservice.controller.service;

import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.GreetingServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.ServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.LatLngBoundsDTO;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.LatLngDTO;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.Place;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.PlacePhoto;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.PlaceType;

import java.util.List;

public interface TravelVisualizerService {

    ServiceInfo status();

    GreetingServiceInfo greeting(String name);

    Boolean login(String username, String password);

    Place addPlace(PlaceType placeType, String name, String description, float rating, double lat, double lng, List<byte[]> photoList);

    List<Place> getPlaces(LatLngBoundsDTO boundsDTO);

    Place getPlace(LatLngDTO latLngDTO);

    List<PlacePhoto> getAllPhotos();
}
