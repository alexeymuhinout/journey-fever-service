package com.rustedbrain.diploma.journeyfeverservice.controller.repository;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.travel.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface TravelRepository extends JpaRepository<Travel, Long> {

    @Query("select t from Travel t where t.user.username = ?1 or t.user.email = ?1")
    List<Travel> findByUser(String usernameOrMail);
}