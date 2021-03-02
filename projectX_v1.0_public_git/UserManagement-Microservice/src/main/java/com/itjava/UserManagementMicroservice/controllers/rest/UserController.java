package com.itjava.UserManagementMicroservice.controllers.rest;

import com.itjava.UserManagementMicroservice.entities.User;
import com.itjava.UserManagementMicroservice.entities.services.UserService;
import com.itjava.UserManagementMicroservice.models.dto.CreateUser;
import com.itjava.UserManagementMicroservice.models.dto.UserCreationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    UserService userService;

    // CREATE USER
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<UserCreationResponse> createNewUser(@RequestBody CreateUser user) {
        return ResponseEntity.status(200).body(userService.createNewUser(user));
    }

    // READ USER BY ID
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // READ USER BY AUTH0 ID
    @RequestMapping(value = "/auth0/{id}", method = RequestMethod.GET)
    public User getUserByAuth0Id(@PathVariable String id) {
        return userService.getUserByAuth0Id(id);
    }

    // READ USER BY USERNAME
    @RequestMapping(value = "/un/{username}", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private class Tmp {
        List<User> users;
    }

    @RequestMapping(value = "/search/{searchString}")
    public Tmp searchUsers(@PathVariable String searchString) {
        return new Tmp(userService.searchUsers(searchString));
    }

    // DELETE USER BY ID
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUserById(@PathVariable int id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
