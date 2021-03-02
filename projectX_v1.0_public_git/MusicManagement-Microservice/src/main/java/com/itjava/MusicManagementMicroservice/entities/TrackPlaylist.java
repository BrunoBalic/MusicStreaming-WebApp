package com.itjava.MusicManagementMicroservice.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TrackPlaylist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int track;
    private int playlist;

    public TrackPlaylist(int trackId, int PlaylistId) {
        this.track = trackId;
        this.playlist = PlaylistId;
    }
}
