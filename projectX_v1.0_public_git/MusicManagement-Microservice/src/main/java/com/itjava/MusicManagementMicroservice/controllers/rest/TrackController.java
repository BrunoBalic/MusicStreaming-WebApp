package com.itjava.MusicManagementMicroservice.controllers.rest;

import com.itjava.MusicManagementMicroservice.entities.Track;
import com.itjava.MusicManagementMicroservice.entities.services.TrackService;
import com.itjava.MusicManagementMicroservice.models.dto.CreateTrack;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/track")
public class TrackController {

    @Autowired
    TrackService trackService;

    // CREATE TRACK
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Track createTrack(@RequestBody CreateTrack track) {
        return trackService.createTrack(new Track(track.getTitle(), track.getDuration(), track.getUser(), track.getFile_path()));
    }

    // GET TRACK BY TRACK ID
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Track getTrackById(@PathVariable int id) {
        return trackService.getTrack(id);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private class ListWrapper {
        List<Track> tracks;
    }

    // GET ALL TRACKS FROM USER
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ListWrapper getAllTracksFromUser(@PathVariable(value = "id") int userId) {
        return new ListWrapper(trackService.getAllTracksByUserId(userId));
    }

    // DELETE TRACK
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteTrack(@PathVariable int id) {
        trackService.deleteTrack(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
