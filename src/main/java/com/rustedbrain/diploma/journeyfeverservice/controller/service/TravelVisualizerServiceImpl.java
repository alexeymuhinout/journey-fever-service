package com.rustedbrain.diploma.journeyfeverservice.controller.service;

import com.rustedbrain.diploma.journeyfeverservice.controller.repository.CommentRepository;
import com.rustedbrain.diploma.journeyfeverservice.controller.repository.PlacePhotoRepository;
import com.rustedbrain.diploma.journeyfeverservice.controller.repository.PlaceRepository;
import com.rustedbrain.diploma.journeyfeverservice.controller.repository.TravelRepository;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.GreetingServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.ServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.Status;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.LatLngBoundsDTO;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.LatLngDTO;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.Place;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.PlacePhoto;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.PlaceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TravelVisualizerServiceImpl implements TravelVisualizerService {

    private static final String GREETING_TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private final PlaceRepository placeRepository;
    private final CommentRepository commentRepository;
    private final TravelRepository travelRepository;
    private final PlacePhotoRepository placePhotoRepository;

    @Autowired
    public TravelVisualizerServiceImpl(PlaceRepository placeRepository, CommentRepository commentRepository, TravelRepository travelRepository, PlacePhotoRepository placePhotoRepository) {
        this.placeRepository = placeRepository;
        this.commentRepository = commentRepository;
        this.travelRepository = travelRepository;
        this.placePhotoRepository = placePhotoRepository;
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
        List<PlacePhoto> placePhotos = photoList.stream().map(PlacePhoto::new).collect(Collectors.toList());
        for (PlacePhoto placePhoto : placePhotos){
            placePhoto.setPlace(place);
        }
        place.setPhotos(placePhotos);
        place.setLatitude(lat);
        place.setLongitude(lng);
        return placeRepository.save(place);
    }

    @Override
    public List<Place> getPlaces(LatLngBoundsDTO boundsDTO) {
        List<Place> places = placeRepository.findAll();
        return places.stream().filter(place -> {
                    LatLngBoundsDTO.BoundsLatLngDTO boundsLatLngDTO = new LatLngBoundsDTO.BoundsLatLngDTO(place.getLatitude(), place.getLongitude());
                    return boundsDTO.contains(boundsLatLngDTO);
                }
        ).collect(Collectors.toList());
    }

    @Override
    public Place getPlace(LatLngDTO latLngDTO) {
        Optional<Place> optionalPlace = placeRepository.findPlaceByCoordinates(latLngDTO.getLatitude(), latLngDTO.getLongitude());
        if (optionalPlace.isPresent()) {
            return optionalPlace.get();
        } else {
            throw new IllegalArgumentException("Place not found by specified coordinates");
        }
    }

    @Override
    public List<PlacePhoto> getAllPhotos() {
        return placePhotoRepository.findAll();
    }
}
