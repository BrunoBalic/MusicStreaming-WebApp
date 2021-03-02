package com.itjava.FrontendMicroservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String auth0id;
    private String first_name;
    private String last_name;
    private String username;
    private List<Post> posts;
    private List<Subscribers> subscribers_user;
    private List<Subscribers> subscribers_user_follows;
}
