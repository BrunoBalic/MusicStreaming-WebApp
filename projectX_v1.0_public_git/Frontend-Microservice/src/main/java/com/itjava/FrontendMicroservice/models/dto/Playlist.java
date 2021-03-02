package com.itjava.FrontendMicroservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {
    private int id;
    private String name;
    private Date publish_date;
    private int number_of_tracks;
    private int user;
    private List<TrackPlaylist> tracks;
}
