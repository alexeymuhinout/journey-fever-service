package com.rustedbrain.diploma.journeyfeverservice.controller.repository;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface TravelRepository extends JpaRepository<Travel, Long> {

}