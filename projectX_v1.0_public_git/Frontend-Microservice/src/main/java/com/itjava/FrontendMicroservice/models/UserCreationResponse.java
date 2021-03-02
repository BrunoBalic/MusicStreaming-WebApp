package com.itjava.FrontendMicroservice.models;

import com.itjava.FrontendMicroservice.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationResponse {
    User user;
    String error_message;
}
