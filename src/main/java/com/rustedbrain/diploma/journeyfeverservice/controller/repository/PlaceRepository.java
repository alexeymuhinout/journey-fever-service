package com.rustedbrain.diploma.journeyfeverservice.controller.repository;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Query("select p from Place as p where p.latitude = ?1 and p.longitude = ?2")
    Optional<Place> findPlaceByCoordinates(double latitude, double longitude);

}