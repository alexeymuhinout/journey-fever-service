package com.rustedbrain.diploma.journeyfeverservice.controller.service;

import com.rustedbrain.diploma.journeyfeverservice.controller.repository.*;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.GreetingServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.ServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.Status;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.LatLngBoundsDTO;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.LatLngDTO;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.PlaceDescriptionDTO;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.security.User;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TravelVisualizerServiceImpl implements TravelVisualizerService {

    private static final String GREETING_TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final CommentRepository commentRepository;
    private final TravelRepository travelRepository;
    private final PlacePhotoRepository placePhotoRepository;

    @Autowired
    public TravelVisualizerServiceImpl(UserRepository userRepository, PlaceRepository placeRepository, CommentRepository commentRepository, TravelRepository travelRepository, PlacePhotoRepository placePhotoRepository) {
        this.userRepository = userRepository;
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
    public Place addPlace(PlaceType placeType, String name, String description, double lat, double lng, List<byte[]> photoList) {
        Place place = new Place();
        place.setPlaceType(placeType);
        place.setName(name);
        place.setDescription(description);
        List<PlacePhoto> placePhotos = photoList.stream().map(PlacePhoto::new).collect(Collectors.toList());
        for (PlacePhoto placePhoto : placePhotos) {
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
    public PlaceDescriptionDTO getPlaceDescription(LatLngDTO latLngDTO, String username) {
        Optional<Place> optionalPlace = placeRepository.findPlaceByCoordinates(latLngDTO.getLatitude(), latLngDTO.getLongitude());
        if (optionalPlace.isPresent()) {
            Place place = optionalPlace.get();
            return new PlaceDescriptionDTO(place, username);
        } else {
            throw new IllegalArgumentException("Place not found by specified coordinates");
        }
    }

    @Override
    public List<PlacePhoto> getAllPhotos() {
        return placePhotoRepository.findAll();
    }

    @Override
    public Comment addComment(LatLngDTO placeLatLng, String username, float rating, String text) {
        if (!StringUtils.hasText(text)) {
            throw new IllegalArgumentException("Comment should contain text");
        }
        Optional<User> optionalUser = userRepository.findByEmailOrUsername(username);
        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("");
        }
        Optional<Place> optionalPlace = placeRepository.findPlaceByCoordinates(placeLatLng.getLatitude(), placeLatLng.getLongitude());
        if (!optionalPlace.isPresent()) {
            throw new IllegalArgumentException();
        }

        Comment comment = new Comment();
        comment.setPlace(optionalPlace.get());
        comment.setUser(optionalUser.get());
        comment.setRating(rating);
        comment.setText(text);

        return commentRepository.save(comment);
    }

    @Override
    public boolean placeIgnore(double latitude, double longitude, String username) {
        Optional<User> optionalUser = userRepository.findByEmailOrUsername(username);
        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("");
        }
        Optional<Place> optionalPlace = placeRepository.findPlaceByCoordinates(latitude, longitude);
        if (!optionalPlace.isPresent()) {
            throw new IllegalArgumentException();
        }

        User user = optionalUser.get();
        Place place = optionalPlace.get();

        if(user.getIgnoredPlaces().stream().anyMatch(place::equals)){
            user.getIgnoredPlaces().remove(place);
            place.getIgnoredToUsers().remove(user);
        } else {
            user.getIgnoredPlaces().add(place);
            place.getIgnoredToUsers().add(user);
        }

        return userRepository.save(user).getIgnoredPlaces().stream().anyMatch(place::equals);
    }

    @Override
    public List<Travel> getUserTravels(String username) {
        return travelRepository.findByUser(username);
    }
}
