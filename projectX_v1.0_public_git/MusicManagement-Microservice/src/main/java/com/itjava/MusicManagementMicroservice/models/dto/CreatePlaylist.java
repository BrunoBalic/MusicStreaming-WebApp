package com.itjava.MusicManagementMicroservice.models.dto;

import lombok.Getter;

@Getter
public class CreatePlaylist {
    private String name;
    private int number_of_tracks;
    private int user;

    public CreatePlaylist(String name, int user) {
        this.name = name;
        this.user = user;
    }
}

