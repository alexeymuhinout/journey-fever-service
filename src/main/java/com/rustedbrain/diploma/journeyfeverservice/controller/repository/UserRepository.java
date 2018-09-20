package com.rustedbrain.diploma.journeyfeverservice.controller.repository;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User as u where u.email = ?1 or u.username = ?1")
    Optional<User> findByEmailOrUsername(String username);

    @Transactional
    @Modifying
    @Query("update User user set user.username = ?2 where user.id = ?1 ")
    void changeUserName(long userId, String newName);

    @Modifying
    @Transactional
    @Query("delete from User u where u.username = ?1 or u.email = ?1")
    void removeByEmailOrUsername(String usernameOrEmail);
}
