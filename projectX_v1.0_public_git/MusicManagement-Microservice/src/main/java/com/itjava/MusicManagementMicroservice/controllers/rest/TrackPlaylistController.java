package com.itjava.MusicManagementMicroservice.controllers.rest;

import com.itjava.MusicManagementMicroservice.entities.services.TrackPlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/track-playlist")
public class TrackPlaylistController {

    @Autowired
    TrackPlaylistService trackPlaylistService;

    // ADD TRACK TO PLAYLIST
    @RequestMapping(value = "/{trackId}/{playlistId}", method = RequestMethod.PUT)
    public void addTrackToPlaylist(@PathVariable int trackId, @PathVariable int playlistId) {
        trackPlaylistService.addTrackToPlaylist(trackId, playlistId);
    }

    // REMOVE TRACK FROM PLAYLIST
    @RequestMapping(value = "/{trackId}/{playlistId}", method = RequestMethod.DELETE)
    public void removeTrackFromPlaylist(@PathVariable int trackId, @PathVariable int playlistId) {
        trackPlaylistService.removeTrackFromPlaylist(trackId, playlistId);
    }

}
