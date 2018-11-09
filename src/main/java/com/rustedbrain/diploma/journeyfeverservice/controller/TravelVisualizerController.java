package com.rustedbrain.diploma.journeyfeverservice.controller;

import com.rustedbrain.diploma.journeyfeverservice.controller.security.util.Constants;
import com.rustedbrain.diploma.journeyfeverservice.controller.service.TravelVisualizerService;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.GreetingServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.ServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.*;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.request.*;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.Comment;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.Place;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.PlaceType;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.Travel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
        double lat = addPlaceRequest.getLatitude();
        double lng = addPlaceRequest.getLongitude();
        List<byte[]> photoList = addPlaceRequest.getPhotoList();

        try {
            Place createdPlace = controller.addPlace(placeType, name, description, lat, lng, photoList);
            return new ResponseEntity<>(new PlaceMapDTO(HttpStatus.OK, createdPlace), HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/place/ignore", method = {RequestMethod.POST})
    @ApiOperation(value = "remove or add's place to user ignored places")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = PlaceMapDTO.class),
            @ApiResponse(code = 406, message = Constants.NOT_ACCEPTABLE),
            @ApiResponse(code = 400, message = Constants.BAD_REQUEST)})
    public ResponseEntity<PlaceIgnoredDTO> removePlaceFromIgnore(@RequestBody UserPlaceRequest placeRequest) {
        try {
            boolean placeIgnored = controller.placeIgnore(placeRequest.getLatLngDTO().getLatitude(), placeRequest.getLatLngDTO().getLongitude(), placeRequest.getUsername());
            return new ResponseEntity<>(new PlaceIgnoredDTO(placeIgnored, HttpStatus.OK), HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/travel/username/get", method = {RequestMethod.POST})
    @ApiOperation(value = "get user travels")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = PlaceMapDTO.class),
            @ApiResponse(code = 406, message = Constants.NOT_ACCEPTABLE),
            @ApiResponse(code = 400, message = Constants.BAD_REQUEST)})
    public ResponseEntity<TravelsDTO> getUserTravels(@RequestBody UserRequest userRequest) {
        try {
            String userName = userRequest.getUsername();
            List<Travel> userTravels = controller.getUserTravels(userName);
            TravelsDTO travelsDTO = new TravelsDTO(HttpStatus.OK, userTravels.stream().map(Travel::getName).collect(Collectors.toList()));
            return new ResponseEntity<>(travelsDTO, HttpStatus.OK);
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
    public ResponseEntity<PlaceDescriptionDTO> getPlaceDescription(@RequestBody UserPlaceRequest userPlaceRequest) {
        try {
            PlaceDescriptionDTO placeDescriptionDTO = controller.getPlaceDescription(userPlaceRequest.getLatLngDTO(), userPlaceRequest.getUsername());
            placeDescriptionDTO.setStatus(HttpStatus.OK);
            return new ResponseEntity<>(placeDescriptionDTO, HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/comment/add", method = {RequestMethod.POST})
    @ApiOperation(value = "add new comment")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = PlaceMapDTO.class),
            @ApiResponse(code = 406, message = Constants.NOT_ACCEPTABLE),
            @ApiResponse(code = 400, message = Constants.BAD_REQUEST)})
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentAddRequest commentAddRequest) {
        try {
            LatLngDTO placeLatLng = commentAddRequest.getPlaceLatLng();
            String commentAuthorLogin = SecurityContextHolder.getContext().getAuthentication().getName();
            float rating = commentAddRequest.getRating();
            String text = commentAddRequest.getText();

            Comment comment = controller.addComment(placeLatLng, commentAuthorLogin, rating, text);
            return new ResponseEntity<>(new CommentDTO(HttpStatus.OK, comment), HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
