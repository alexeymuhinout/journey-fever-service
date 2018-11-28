package com.rustedbrain.diploma.journeyfeverservice.model.dto.security;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.security.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTOList {

    private Collection<UserDTO> userDTOList;

    public UserDTOList() {
    }

    public UserDTOList(Collection<UserDTO> userDTOList) {
        this.userDTOList = userDTOList;
    }

    public UserDTOList(List<User> users) {
        this.userDTOList = users.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public Collection<UserDTO> getUserDTOList() {
        return userDTOList;
    }

    public void setUserDTOList(Collection<UserDTO> userDTOList) {
        this.userDTOList = userDTOList;
    }

    @Override
    public String toString() {
        return "UserDTOList{" +
                "userDTOList=" + userDTOList +
                '}';
    }
}