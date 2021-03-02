package com.itjava.FrontendMicroservice.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistration {
    private String email;
    private String password;
    private String password_repeat;
    private String first_name;
    private String last_name;
    private String username;

    public boolean isValid() {
        if (!password.equals(password_repeat)) {
            return false;
        }
        return true;
    }
}
