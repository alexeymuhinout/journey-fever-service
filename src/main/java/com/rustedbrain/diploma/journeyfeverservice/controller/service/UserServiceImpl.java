package com.rustedbrain.diploma.journeyfeverservice.controller.service;

import com.rustedbrain.diploma.journeyfeverservice.controller.repository.UserRepository;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.Role;
import com.rustedbrain.diploma.journeyfeverservice.model.persistence.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByEmailOrUsername(usernameOrEmail);
    }

    @Override
    public User createUser(String firstName, String lastName, String username, String password, String email, Role role) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setEmail(email);
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public void removeUser(String usernameOrEmail) {
        userRepository.removeByEmailOrUsername(usernameOrEmail);
    }

    @Override
    public User editUser(String targetEditProfileLoginOrEmail, String firstName, String lastName, String username, String password, String email, Role role) {
        Optional<User> optionalUser = userRepository.findByEmailOrUsername(targetEditProfileLoginOrEmail);
        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("User with inputted username not found");
        } else {
            User user = optionalUser.get();
            if (Objects.nonNull(username)) {
                user.setUsername(username);
            }
            if (Objects.nonNull(firstName)) {
                user.setFirstName(firstName);
            }
            if (Objects.nonNull(lastName)) {
                user.setLastName(lastName);
            }
            if (Objects.nonNull(email)) {
                user.setEmail(email);
            }
            if (Objects.nonNull(password)) {
                user.setPassword(password);
            }
            if (Objects.nonNull(role)) {
                user.setRole(role);
            }
            return userRepository.save(user);
        }
    }
}
