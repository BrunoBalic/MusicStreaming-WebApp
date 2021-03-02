package com.itjava.FrontendMicroservice.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TrackPlaylist {
    private int id;
    private int track;
    private int playlist;
}