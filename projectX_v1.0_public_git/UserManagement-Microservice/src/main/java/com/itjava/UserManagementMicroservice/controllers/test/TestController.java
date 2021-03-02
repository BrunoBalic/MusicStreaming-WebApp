package com.itjava.UserManagementMicroservice.controllers.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @GetMapping("/t1")
    public String t1() {
        return "String from User Management microservice on /test/t1";
    }

}
