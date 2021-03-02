package com.itjava.FrontendMicroservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Track {
    private Integer id;
    private String title;
    private Timestamp publish_date;
    private int duration;
    private int user;
    private String file_path;
    private List<TrackPlaylist> playlists;
}