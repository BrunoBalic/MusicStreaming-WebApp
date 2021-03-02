package com.itjava.FrontendMicroservice.controllers.test.rest;

import com.itjava.FrontendMicroservice.microservices.MusicManagement.PlaylistService;
import com.itjava.FrontendMicroservice.microservices.MusicManagement.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test/rest/music")
public class MusicMngm {

    @Autowired
    TrackService trackService;
    @Autowired
    PlaylistService playlistService;

    @GetMapping("t1")
    public void t1() {

    }

}
