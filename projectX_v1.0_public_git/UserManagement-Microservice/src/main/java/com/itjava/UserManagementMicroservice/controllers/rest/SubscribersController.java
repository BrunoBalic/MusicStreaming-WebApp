package com.itjava.UserManagementMicroservice.controllers.rest;

import com.itjava.UserManagementMicroservice.entities.Subscribers;
import com.itjava.UserManagementMicroservice.entities.services.SubscribersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/subs")
public class SubscribersController {

    @Autowired
    SubscribersService subscribersService;

    // uid - user koji se zeli subscribat ne nekoga
    // fid - osoba na koju se subscriba
    @RequestMapping(value = "/{uid}/{fid}", method = RequestMethod.POST)
    public Subscribers createSubscription(@PathVariable int uid, @PathVariable int fid) {
        return subscribersService.newSubscription(uid, fid);
    }

    @RequestMapping(value = "/{uid}/{fid}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteSubscription(@PathVariable int uid, @PathVariable int fid) {
        subscribersService.removeSubscription(uid, fid);
        return ResponseEntity.status(200).build();
    }

}
