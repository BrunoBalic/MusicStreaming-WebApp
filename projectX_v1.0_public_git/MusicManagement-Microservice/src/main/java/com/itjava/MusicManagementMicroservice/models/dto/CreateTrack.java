package com.itjava.MusicManagementMicroservice.models.dto;

import lombok.Getter;

@Getter
public class CreateTrack {
    private String title;
    private int duration;
    private int user;
    private String file_path;
}
