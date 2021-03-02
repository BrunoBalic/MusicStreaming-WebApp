package com.itjava.FrontendMicroservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUser {
    private String email;
    private String password;
    private String first_name;
    private String last_name;
    private String username;
}
