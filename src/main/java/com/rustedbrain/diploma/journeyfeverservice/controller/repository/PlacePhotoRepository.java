package com.rustedbrain.diploma.journeyfeverservice.controller.repository;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.PlacePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlacePhotoRepository  extends JpaRepository<PlacePhoto, Long> {
}
