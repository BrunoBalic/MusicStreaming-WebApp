package com.itjava.MusicManagementMicroservice.controllers.rest;

import com.itjava.MusicManagementMicroservice.entities.Playlist;
import com.itjava.MusicManagementMicroservice.entities.services.PlaylistService;
import com.itjava.MusicManagementMicroservice.models.dto.CreatePlaylist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/playlist")
public class PlaylistController {

    @Autowired
    PlaylistService playlistService;

    // CREATE PLAYLIST
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Playlist createPlaylist(@RequestBody CreatePlaylist playlist) {
        return playlistService.createPlaylist(new Playlist(playlist.getName(), playlist.getUser(), playlist.getNumber_of_tracks()));
    }

    // GET PLAYLIST BY ID
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Playlist getPlaylistById(@PathVariable int id) {
        return playlistService.getPlaylist(id);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private class ListWrapper {
        List<Playlist> playlists;
    }

    // GET ALL PLAYLISTS FROM USER
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ListWrapper getAllPlaylistsFromUser(@PathVariable int id) {
        return new ListWrapper(playlistService.getAllPlaylistsByUserId(id));
    }

    // DELETE PLAYLIST
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deletePlaylist(@PathVariable int id) {
        playlistService.deletePlaylist(id);
    }

}
