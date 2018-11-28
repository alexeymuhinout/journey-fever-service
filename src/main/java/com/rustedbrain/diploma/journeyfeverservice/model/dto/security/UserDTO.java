package com.rustedbrain.diploma.journeyfeverservice.model.dto.security;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.security.User;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class UserDTO implements Serializable {

    private String username;
    private List<String> roles;

    public UserDTO (User user){
        this.username = user.getUsername();
        this.roles = Collections.singletonList(user.getRole().name());
    }

    public UserDTO() {
    }

    public UserDTO(String username, List<String> roles) {
        this.roles = roles;
        this.username = username;
    }


    public List<String> getRoles() {
        return this.roles;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", roles=" + roles +
                '}';
    }
}
