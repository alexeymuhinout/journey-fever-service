package com.rustedbrain.diploma.journeyfeverservice.controller.service;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.security.Role;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.security.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsernameOrEmail(String usernameOrEmail);

    User createUser(String firstName, String lastName, String username, String password, String email, Role role);

    void removeUser(String usernameOrEmail);

    User editUser(String targetEditProfileLoginOrEmail, String firstName, String lastName, String username, String password, String email, Role role);
}
