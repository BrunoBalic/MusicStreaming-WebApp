package com.itjava.UserManagementMicroservice.controllers.test;

import com.itjava.UserManagementMicroservice.Auth0.Auth0ManagementApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test/auth0")
public class TestAuth0Controller {

    @Autowired
    Auth0ManagementApi auth0ManagementApi;

    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    public void test1() {
        auth0ManagementApi.createNewUser("8balic@gmail.com", "javaJAVA13", "8balic"); // RADI
    }

    @RequestMapping(value = "/assignRole", method = RequestMethod.GET)
    public void test2() {
        int responseCode = auth0ManagementApi.assignRolesToUser("auth0|6029ab4fd8ace4007007251f", "regular_user"); // RADI
        System.out.println(responseCode);
    }

}
