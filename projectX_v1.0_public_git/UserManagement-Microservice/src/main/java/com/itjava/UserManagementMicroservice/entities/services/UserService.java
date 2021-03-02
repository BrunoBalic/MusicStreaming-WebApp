package com.itjava.UserManagementMicroservice.entities.services;

import com.itjava.UserManagementMicroservice.Auth0.Auth0ManagementApi;
import com.itjava.UserManagementMicroservice.entities.User;
import com.itjava.UserManagementMicroservice.entities.repositories.UserRepository;
import com.itjava.UserManagementMicroservice.models.dto.CreateUser;
import com.itjava.UserManagementMicroservice.models.dto.UserCreationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    Auth0ManagementApi auth0ManagementApi;

    @Autowired
    UserRepository userRepository;

    // ovdje bi mogli vracati neki entry set <User, String> User i message
    // message da znam zasto nije proslo...
    /*
    public User createNewUser(CreateUser user) {

        // 1. provjera jel username zauzet, jel postoji u nasoj bazi
        // 2. kreiranje usera u auth0
        // 3. provjera jel kreiran u auth0
        //      - ako je auth0Id null - nesto nije proslo
        // 5. auth0 assign role
        // 4. kreiranje usera u nasoj bazi

        // 1.
        if (userRepository.findByUsername(user.getUsername()) != null) {
            System.out.println("username already exists in our db");
            return null;
        }

        // 2.
        String auth0Id = auth0ManagementApi.createNewUser(user.getEmail(), user.getPassword(), user.getUsername());

        // 3.
        if (auth0Id == null) {
            System.out.println("error creating new user");
            return null;
        }

        // 5.
        auth0ManagementApi.assignRolesToUser(auth0Id, "regular_user");

        // 4.
        return userRepository.save(new User(auth0Id, user.getFirst_name(), user.getLast_name(), user.getUsername()));
    }
     */

    public UserCreationResponse createNewUser(CreateUser user) {

        // 1. provjera jel username zauzet, jel postoji u nasoj bazi
        // 2. kreiranje usera u auth0
        // 3. provjera jel kreiran u auth0
        //      - ako je auth0Id null - nesto nije proslo
        // 5. auth0 assign role
        // 4. kreiranje usera u nasoj bazi

        // 1.
        if (userRepository.findByUsername(user.getUsername()) != null) {
            System.out.println("username already exists in our db");
            return new UserCreationResponse(null, "Username is already taken!");
        }

        // 2.
        Map<String, String> result = auth0ManagementApi.createNewUser(user.getEmail(), user.getPassword(), user.getUsername());
        String auth0Id = result.get("user_id");
        String errorMessage = result.get("error_message");

        // 3.
        if (auth0Id == null) {
            System.out.println("error creating new user");
            return new UserCreationResponse(null, errorMessage);
        }

        // 5.
        auth0ManagementApi.assignRolesToUser(auth0Id, "regular_user");

        // 4.
        return new UserCreationResponse(userRepository.save(new User(auth0Id, user.getFirst_name(), user.getLast_name(), user.getUsername())), null);
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByAuth0Id(String id) {
        return userRepository.findByAuth0id(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> searchUsers(String searchString) {
        return userRepository.searchUsers(searchString, JpaSort.unsafe("LENGTH(username)"));
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    // dodati brisanje i u auth0
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

}
