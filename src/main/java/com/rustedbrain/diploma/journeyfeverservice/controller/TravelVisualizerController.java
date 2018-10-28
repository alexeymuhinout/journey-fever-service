package com.rustedbrain.diploma.journeyfeverservice.controller;

import com.rustedbrain.diploma.journeyfeverservice.controller.security.util.Constants;
import com.rustedbrain.diploma.journeyfeverservice.controller.service.TravelVisualizerService;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.GreetingServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.ServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.PlaceDescriptionDTO;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.PlaceMapDTO;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.PlaceMapDTOList;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.request.AddPlaceRequest;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.request.GetPlaceDescriptionRequest;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.request.GetPlacesRequest;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.Place;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.PlaceType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/travel")
public class TravelVisualizerController {

    private final TravelVisualizerService controller;

    @Autowired
    public TravelVisualizerController(TravelVisualizerService controller) {
        this.controller = controller;
    }


    @RequestMapping(path = "/status", method = RequestMethod.GET)
    public ServiceInfo protectedStatus() {
        return controller.status();
    }

    @RequestMapping(path = "/greeting", method = RequestMethod.GET)
    public GreetingServiceInfo greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return controller.greeting(name);
    }

    @RequestMapping(value = "/place/add", method = {RequestMethod.POST})
    @ApiOperation(value = "add place")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = PlaceMapDTO.class),
            @ApiResponse(code = 406, message = Constants.NOT_ACCEPTABLE),
            @ApiResponse(code = 400, message = Constants.BAD_REQUEST)})
    public ResponseEntity<PlaceMapDTO> addPlace(@RequestBody AddPlaceRequest addPlaceRequest) {
        PlaceType placeType = addPlaceRequest.getPlaceType();
        String name = addPlaceRequest.getName();
        String description = addPlaceRequest.getDescription();
        float rating = addPlaceRequest.getRating();
        double lat = addPlaceRequest.getLatitude();
        double lng = addPlaceRequest.getLongitude();
        List<byte[]> photoList = addPlaceRequest.getPhotoList();

        try {
            Place createdPlace = controller.addPlace(placeType, name, description, rating, lat, lng, photoList);
            return new ResponseEntity<>(new PlaceMapDTO(HttpStatus.OK, createdPlace), HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/place/get/bounds", method = {RequestMethod.POST})
    @ApiOperation(value = "get places")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = PlaceMapDTO.class),
            @ApiResponse(code = 406, message = Constants.NOT_ACCEPTABLE),
            @ApiResponse(code = 400, message = Constants.BAD_REQUEST)})
    public ResponseEntity<PlaceMapDTOList> getPlaces(@RequestBody GetPlacesRequest getPlacesRequest) {
        try {
            List<Place> places = controller.getPlaces(getPlacesRequest.getLatLngBoundsDTO());
            List<PlaceMapDTO> placeMapDTOList = places.stream().map(PlaceMapDTO::new).collect(Collectors.toList());
            return new ResponseEntity<>(new PlaceMapDTOList(HttpStatus.OK, placeMapDTOList), HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/place/description/get", method = {RequestMethod.POST})
    @ApiOperation(value = "get place description")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = PlaceMapDTO.class),
            @ApiResponse(code = 406, message = Constants.NOT_ACCEPTABLE),
            @ApiResponse(code = 400, message = Constants.BAD_REQUEST)})
    public ResponseEntity<PlaceDescriptionDTO> getPlaceDescription(@RequestBody GetPlaceDescriptionRequest getPlaceDescriptionRequest) {
        try {
            Place place = controller.getPlace(getPlaceDescriptionRequest.getLatLngDTO());
            controller.getAllPhotos().stream().forEach(placePhoto -> System.out.println(placePhoto.getPlace()));
            return new ResponseEntity<>(new PlaceDescriptionDTO(HttpStatus.OK, place), HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
