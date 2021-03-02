package com.itjava.FrontendMicroservice.microservices.UserManagement;

import com.itjava.FrontendMicroservice.microservices.Token;
import com.itjava.FrontendMicroservice.models.UserCreationResponse;
import com.itjava.FrontendMicroservice.models.entities.User;
import com.itjava.FrontendMicroservice.models.CreateUser;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    // prebaciti vrijednosti varijabli u yml
    private String baseUri = "http://localhost:8101";

    @Autowired
    Token token;

    // CREATE - new User
    public UserCreationResponse createNewUser(CreateUser createUser) {
        HttpResponse<UserCreationResponse> res = Unirest
                .post(baseUri + "/api/user/create")
                .header("Authorization", "Bearer " + token.getAccessToken())
                .contentType("application/json")
                .accept("application/json")
                .body(createUser)
                .asObject(UserCreationResponse.class);

        System.out.println(res.getStatus());

        if (res.isSuccess()) {
            System.out.println("success je");
            return res.getBody();
        } else {
            System.out.println("nije success");
            return null;
        }
    }

    // READ - User by id
    public User getUserById(String id) {
        HttpResponse<User> res = Unirest
                .get(baseUri + "/api/user/{id}")
                .routeParam("id", id)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .accept("application/json")
                .asObject(User.class);

        if (res.isSuccess()) {
            return res.getBody();
        } else return null;
    }

    // READ - User by auth0 id
    public User getUserByAuth0Id(String id) {
        HttpResponse<User> res = Unirest
                .get(baseUri + "/api/user/auth0/{id}")
                .routeParam("id", id)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .accept("application/json")
                .asObject(User.class);

        if (res.isSuccess()) {
            return res.getBody();
        } else return null;
    }

    // READ - User by username
    public User getUserByUsername(String username) {
        HttpResponse<User> res = Unirest
                .get(baseUri + "/api/user/un/{username}")
                .routeParam("username", username)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .accept("application/json")
                .asObject(User.class);

        if (res.isSuccess()) {
            return res.getBody();
        } else return null;
    }

    // Unirest nije dovoljno pametan da pretvori Listu objekata u Listu objekata koju vraca response...
    // privremeno rijesenje
    // incae koristi jackson mapper
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private class ListWrapper {
        List<User> users;
    }

    public List<User> searchUsers(String searchString) {
        HttpResponse<ListWrapper> res = Unirest
                .get(baseUri + "/api/user/search/{searchString}")
                .routeParam("searchString", searchString)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .accept("application/json")
                .asObject(ListWrapper.class);

        if (res.isSuccess()) {
            //return res.getBody().getArray().toList(); // ovo ne daje ocekivan rezultat, vraca List i kad castam u List<User> ne radi...
            return res.getBody().getUsers();
        } else {
            System.out.println("neki je error");
            return null;
        }
    }

}
