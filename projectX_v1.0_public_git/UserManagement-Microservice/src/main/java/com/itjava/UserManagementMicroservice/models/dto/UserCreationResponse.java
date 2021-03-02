package com.itjava.UserManagementMicroservice.models.dto;

import com.itjava.UserManagementMicroservice.entities.User;
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
