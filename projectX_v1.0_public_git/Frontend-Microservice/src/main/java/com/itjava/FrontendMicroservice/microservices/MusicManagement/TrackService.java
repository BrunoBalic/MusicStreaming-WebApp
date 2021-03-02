package com.itjava.FrontendMicroservice.microservices.MusicManagement;

import com.itjava.FrontendMicroservice.microservices.Token;
import com.itjava.FrontendMicroservice.models.CreateTrack;
import com.itjava.FrontendMicroservice.models.entities.Playlist;
import com.itjava.FrontendMicroservice.models.entities.Track;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackService {

    String base_uri = "http://localhost:8102/api/track";

    @Autowired
    Token token;

    public Track createNewTrack(CreateTrack track) {
        HttpResponse<Track> res = Unirest
                .post(base_uri + "/create")
                .header("Authorization", "Bearer " + token.getAccessToken())
                .contentType("application/json")
                .accept("application/json")
                .body(track)
                .asObject(Track.class);

        if (res.isSuccess()) {
            return res.getBody();
        } else return null;
    }

    public Track getTrackById(int id) {
        HttpResponse<Track> res = Unirest
                .get(base_uri + "/{id}")
                .header("Authorization", "Bearer " + token.getAccessToken())
                .routeParam("id", String.valueOf(id))
                .accept("application/json")
                .asObject(Track.class);

        if (res.isSuccess()) {
            return res.getBody();
        } else return null;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private class ListWrapper {
        List<Track> tracks;
    }

    public List<Track> getAllTracksFromUser(int userId) {
        HttpResponse<ListWrapper> res = Unirest
                .get(base_uri + "/user/{id}")
                .header("Authorization", "Bearer " + token.getAccessToken())
                .routeParam("id", String.valueOf(userId))
                .accept("application/json")
                .asObject(ListWrapper.class);

        if (res.isSuccess()) {
            return res.getBody().getTracks();
        } else return null;
    }

    public void deleteTrack(int id) {
        HttpResponse res = Unirest
                .delete(base_uri + "/{id}")
                .header("Authorization", "Bearer " + token.getAccessToken())
                .routeParam("id", String.valueOf(id))
                .asEmpty();

        if (res.isSuccess()) {
            System.out.println("track deleted");
        } else System.out.println("error deleting track");
    }

}
