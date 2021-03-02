package com.itjava.UserManagementMicroservice.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class CreateUser {
    private String email;
    private String password;
    private String first_name;
    private String last_name;
    private String username;
}
