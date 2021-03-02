package com.itjava.FrontendMicroservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePlaylist {
    private String name;
    private int number_of_tracks = 0;
    private int user;

    public CreatePlaylist(String name, int user) {
        this.name = name;
        this.user = user;
    }
}
