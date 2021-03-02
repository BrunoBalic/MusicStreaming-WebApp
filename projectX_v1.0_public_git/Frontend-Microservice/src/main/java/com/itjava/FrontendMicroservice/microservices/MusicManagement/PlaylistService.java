package com.itjava.FrontendMicroservice.microservices.MusicManagement;

import com.itjava.FrontendMicroservice.microservices.Token;
import com.itjava.FrontendMicroservice.models.CreatePlaylist;
import com.itjava.FrontendMicroservice.models.entities.Playlist;
import com.itjava.FrontendMicroservice.models.entities.Track;
import com.itjava.FrontendMicroservice.models.entities.User;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistService {

    String base_uri = "http://localhost:8102/api/playlist";

    @Autowired
    Token token;

    @Autowired
    TrackService trackService;

    public Playlist createNewPlaylist(CreatePlaylist playlist) {
        HttpResponse<Playlist> res = Unirest
                .post(base_uri + "/create")
                .header("Authorization", "Bearer " + token.getAccessToken())
                .contentType("application/json")
                .accept("application/json")
                .body(playlist)
                .asObject(Playlist.class);

        if (res.isSuccess()) {
            return res.getBody();
        } else return null;
    }

    public Playlist getPlaylistById(int id) {
        HttpResponse<Playlist> res = Unirest
                .get(base_uri + "/{id}")
                .header("Authorization", "Bearer " + token.getAccessToken())
                .routeParam("id", String.valueOf(id))
                .accept("application/json")
                .asObject(Playlist.class);

        if (res.isSuccess()) {
            return res.getBody();
        } else return null;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private class ListWrapper {
        List<Playlist> playlists;
    }

    public List<Playlist> getAllPlaylistsFromUser(int userId) {
        HttpResponse<ListWrapper> res = Unirest
                .get(base_uri + "/user/{id}")
                .header("Authorization", "Bearer " + token.getAccessToken())
                .routeParam("id", String.valueOf(userId))
                .accept("application/json")
                .asObject(ListWrapper.class);

        if (res.isSuccess()) {
            return res.getBody().getPlaylists();
        } else return null;
    }

    public List<Track> getAllTracksFromPlaylist(int playlistId) {
        Playlist pl = getPlaylistById(playlistId);
        List<Track> tracks = new ArrayList<>();
        pl.getTracks().forEach(tr -> {
            tracks.add(trackService.getTrackById(tr.getTrack()));
        });
        return tracks;
    }

    public void deletePlaylist(int id) {
        HttpResponse res = Unirest
                .delete(base_uri + "/{id}")
                .header("Authorization", "Bearer " + token.getAccessToken())
                .routeParam("id", String.valueOf(id))
                .asEmpty();

        if (res.isSuccess()) {
            System.out.println("playlist deleted");
        } else System.out.println("error deleting playlist");
    }

    // dodavanje i micanje s playlist-e

    public void addTrackToPlaylist(int trackId, int playlistId) {
        HttpResponse res = Unirest
                .put("http://localhost:8102/api/track-playlist/{trackId}/{playlistId}")
                .header("Authorization", "Bearer " + token.getAccessToken())
                .routeParam("trackId", String.valueOf(trackId))
                .routeParam("playlistId", String.valueOf(playlistId))
                .asEmpty();

        if (res.isSuccess()) {
            System.out.println("track added to playlist");
        } else System.out.println("error adding track to playlist");
    }

    public void removeTrackFromPlaylist(int trackId, int playlistId) {
        HttpResponse res = Unirest
                .delete("http://localhost:8102/api/track-playlist/{trackId}/{playlistId}")
                .header("Authorization", "Bearer " + token.getAccessToken())
                .routeParam("trackId", String.valueOf(trackId))
                .routeParam("playlistId", String.valueOf(playlistId))
                .asEmpty();

        if (res.isSuccess()) {
            System.out.println("track removed to playlist");
        } else System.out.println("error removing track from playlist");
    }

}
