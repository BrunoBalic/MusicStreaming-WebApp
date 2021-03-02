package com.itjava.MusicManagementMicroservice.controllers.test;

import com.itjava.MusicManagementMicroservice.entities.Track;
import com.itjava.MusicManagementMicroservice.entities.services.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/")
public class TestTrackController {

    @Autowired
    TrackService trackService;

    @GetMapping("/tracks")
    public Iterable<Track> allTracks() {
        return trackService.getAllTracks();
    }

    @GetMapping(value = "/tracks/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Track> allTracksByUserId(@PathVariable(value = "id") int userId) {
        return trackService.getAllTracksByUserId(userId);
    }

}
