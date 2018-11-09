package com.rustedbrain.diploma.journeyfeverservice.controller.service;

import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.GreetingServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.ServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.LatLngBoundsDTO;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.LatLngDTO;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.PlaceDescriptionDTO;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.*;

import java.util.List;

public interface TravelVisualizerService {

    ServiceInfo status();

    GreetingServiceInfo greeting(String name);

    Boolean login(String username, String password);

    Place addPlace(PlaceType placeType, String name, String description, double lat, double lng, List<byte[]> photoList);

    List<Place> getPlaces(LatLngBoundsDTO boundsDTO);

    PlaceDescriptionDTO getPlaceDescription(LatLngDTO latLngDTO, String username);

    List<PlacePhoto> getAllPhotos();

    Comment addComment(LatLngDTO placeLatLng, String commentAddRequest, float rating, String text);

    boolean placeIgnore(double latitude, double longitude, String username);

    List<Travel> getUserTravels(String userName);
}
