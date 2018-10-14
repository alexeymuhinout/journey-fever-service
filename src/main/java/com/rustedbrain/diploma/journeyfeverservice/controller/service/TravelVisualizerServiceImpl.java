package com.rustedbrain.diploma.journeyfeverservice.controller.service;

import com.rustedbrain.diploma.journeyfeverservice.controller.repository.CommentRepository;
import com.rustedbrain.diploma.journeyfeverservice.controller.repository.PlaceRepository;
import com.rustedbrain.diploma.journeyfeverservice.controller.repository.TravelRepository;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.GreetingServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.ServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.Status;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.LatLngBoundsDTO;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.Place;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.PlacePhoto;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.PlaceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TravelVisualizerServiceImpl implements TravelVisualizerService {

    private static final String GREETING_TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private final PlaceRepository placeRepository;
    private final CommentRepository commentRepository;
    private final TravelRepository travelRepository;

    @Autowired
    public TravelVisualizerServiceImpl(PlaceRepository placeRepository, CommentRepository commentRepository, TravelRepository travelRepository) {
        this.placeRepository = placeRepository;
        this.commentRepository = commentRepository;
        this.travelRepository = travelRepository;
    }

    @Override
    public ServiceInfo status() {
        return new ServiceInfo(counter.incrementAndGet(), System.currentTimeMillis());
    }

    @Override
    public GreetingServiceInfo greeting(String name) {
        return new GreetingServiceInfo(counter.incrementAndGet(), Status.OK, System.currentTimeMillis(), String.format(GREETING_TEMPLATE, name));
    }

    @Override
    public Boolean login(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }

    @Override
    public Place addPlace(PlaceType placeType, String name, String description, float rating, double lat, double lng, List<byte[]> photoList) {
        Place place = new Place();
        place.setPlaceType(placeType);
        place.setName(name);
        place.setDescription(description);
        place.setRating(rating);
        place.setPhotos(photoList.stream().map(PlacePhoto::new).collect(Collectors.toList()));
        place.setLatitude(lat);
        place.setLongitude(lng);
        return placeRepository.save(place);
    }

    @Override
    public List<Place> getPlaces(LatLngBoundsDTO boundsDTO) {
        List<Place> places = placeRepository.findAll();
        return places.stream().filter(place -> {
                    LatLngBoundsDTO.LatLngDTO latLngDTO = new LatLngBoundsDTO.LatLngDTO(place.getLatitude(), place.getLongitude());
                    return boundsDTO.contains(latLngDTO);
                }
        ).collect(Collectors.toList());
    }
}
