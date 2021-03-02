package com.itjava.FrontendMicroservice.controllers.test.rest;

import com.itjava.FrontendMicroservice.microservices.UserManagement.PostService;
import com.itjava.FrontendMicroservice.microservices.UserManagement.SubscribersService;
import com.itjava.FrontendMicroservice.microservices.UserManagement.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test/rest/user")
public class UserMngm {

    @Autowired
    UserService userService;
    @Autowired
    PostService postService;
    @Autowired
    SubscribersService subscribersService;

    @GetMapping("t1")
    public void t1() {

    }

}
