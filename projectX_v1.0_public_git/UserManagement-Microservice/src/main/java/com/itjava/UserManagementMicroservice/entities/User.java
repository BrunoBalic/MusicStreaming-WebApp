package com.itjava.UserManagementMicroservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "AUTH0_ID", unique = true)
    private String auth0id;
    private String first_name;
    private String last_name;
    @Column(unique = true)
    private String username;
    @OneToMany(mappedBy = "user")
    private List<Post> posts;
    @OneToMany(mappedBy = "user")
    private List<Subscribers> subscribers_user;
    @OneToMany(mappedBy = "userFollows")
    private List<Subscribers> subscribers_user_follows;

    public User(String auth0id, String first_name, String last_name, String username) {
        this.auth0id = auth0id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
    }
}
