package com.rustedbrain.diploma.journeyfeverservice.controller;

import com.rustedbrain.diploma.journeyfeverservice.controller.security.util.Constants;
import com.rustedbrain.diploma.journeyfeverservice.controller.service.TravelVisualizerService;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.security.UserDTOList;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.GreetingServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.ServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.*;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.travel.request.*;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.security.User;
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

    private final TravelVisualizerService service;

    @Autowired
    public TravelVisualizerController(TravelVisualizerService service) {
        this.service = service;
    }


    @RequestMapping(path = "/status", method = RequestMethod.GET)
    public ServiceInfo protectedStatus() {
        return service.status();
    }

    @RequestMapping(path = "/greeting", method = RequestMethod.GET)
    public GreetingServiceInfo greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return service.greeting(name);
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
            Place createdPlace = service.addPlace(placeType, name, description, lat, lng, photoList);
            PlaceMapDTO placeMapDTO = new PlaceMapDTO(createdPlace);
            return new ResponseEntity<>(placeMapDTO, HttpStatus.OK);
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
    public ResponseEntity<PlaceIgnoredDTO> removePlaceFromIgnore(@RequestBody NamingPlaceRequest placeRequest) {
        try {
            boolean placeIgnored = service.placeIgnore(placeRequest.getLatLngDTO().getLatitude(), placeRequest.getLatLngDTO().getLongitude(), placeRequest.getName());
            PlaceIgnoredDTO placeIgnoredDTO = new PlaceIgnoredDTO(placeIgnored);
            return new ResponseEntity<>(placeIgnoredDTO, HttpStatus.OK);
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
    public ResponseEntity<TravelDTOList> getUserTravels(@RequestBody NamingRequest namingRequest) {
        try {
            String userName = namingRequest.getName();
            List<Travel> userTravels = service.getUserTravels(userName);
            TravelDTOList travelDTOList = new TravelDTOList(userTravels.stream().map(TravelDTO::new).collect(Collectors.toList()));
            return new ResponseEntity<>(travelDTOList, HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/travel/add", method = {RequestMethod.POST})
    @ApiOperation(value = "add travel")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = PlaceMapDTO.class),
            @ApiResponse(code = 406, message = Constants.NOT_ACCEPTABLE),
            @ApiResponse(code = 400, message = Constants.BAD_REQUEST)})
    public ResponseEntity<TravelDTO> addTravel(@RequestBody AuthorizedNamingRequest authorizedNamingRequest) {
        try {
            String username = authorizedNamingRequest.getUsername();
            String travelName = authorizedNamingRequest.getName();
            Travel travel = service.addTravel(username, travelName);
            return new ResponseEntity<>(new TravelDTO(travel), HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/travel/archive", method = {RequestMethod.POST})
    @ApiOperation(value = "archive travel")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = PlaceMapDTO.class),
            @ApiResponse(code = 406, message = Constants.NOT_ACCEPTABLE),
            @ApiResponse(code = 400, message = Constants.BAD_REQUEST)})
    public ResponseEntity<TravelDTO> archiveTravel(@RequestBody AuthorizedNamingRequest authorizedNamingRequest) {
        try {
            String username = authorizedNamingRequest.getUsername();
            String travelName = authorizedNamingRequest.getName();
            Travel travel = service.archiveTravel(username, travelName);
            return new ResponseEntity<>(new TravelDTO(travel), HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/travel/place/modify", method = {RequestMethod.POST})
    @ApiOperation(value = "add place to travel")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = PlaceMapDTO.class),
            @ApiResponse(code = 406, message = Constants.NOT_ACCEPTABLE),
            @ApiResponse(code = 400, message = Constants.BAD_REQUEST)})
    public ResponseEntity<PlaceDescriptionDTO> modifyTravelPlace(@RequestBody TravelPlaceAddRequest travelPlaceAddRequest) {
        try {
            String username = travelPlaceAddRequest.getUsername();
            String travelName = travelPlaceAddRequest.getName();
            LatLngDTO latLngDTO = travelPlaceAddRequest.getLatLngDTO();
            Place place = service.addRemoveTravelPlace(username, travelName, latLngDTO.getLatitude(), latLngDTO.getLongitude());
            PlaceDescriptionDTO travelDTO = new PlaceDescriptionDTO(place, username);
            return new ResponseEntity<>(travelDTO, HttpStatus.OK);
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
            List<Place> places = service.getPlaces(getPlacesRequest.getLatLngBoundsDTO(), getPlacesRequest.getTripsMapPlacesFilterDTO());
            List<PlaceMapDTO> placeMapDTOs = places.stream().map(PlaceMapDTO::new).collect(Collectors.toList());
            PlaceMapDTOList placeMapDTOList1 = new PlaceMapDTOList(placeMapDTOs);
            return new ResponseEntity<>(placeMapDTOList1, HttpStatus.OK);
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
    public ResponseEntity<PlaceDescriptionDTO> getPlaceDescription(@RequestBody NamingPlaceRequest userPlaceRequest) {
        try {
            Place place = service.getPlaceDescription(userPlaceRequest.getLatLngDTO(), userPlaceRequest.getName());
            PlaceDescriptionDTO placeDescriptionDTO = new PlaceDescriptionDTO(place, userPlaceRequest.getName());
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

            Comment comment = service.addComment(placeLatLng, commentAuthorLogin, rating, text);
            CommentDTO commentDTO = new CommentDTO(comment);
            return new ResponseEntity<>(commentDTO, HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/users/get", method = {RequestMethod.GET})
    @ApiOperation(value = "retrieve users")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = PlaceMapDTO.class),
            @ApiResponse(code = 406, message = Constants.NOT_ACCEPTABLE),
            @ApiResponse(code = 400, message = Constants.BAD_REQUEST)})
    public ResponseEntity<UserDTOList> getUsers() {
        try {
            List<User> users = service.getUsers();
            UserDTOList userDTOList = new UserDTOList(users);
            return new ResponseEntity<>(userDTOList, HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
