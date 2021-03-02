package com.itjava.MusicManagementMicroservice.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PLAYLIST")
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @CreationTimestamp
    private Date publish_date;

    private int number_of_tracks;
    private int user;
    @OneToMany(mappedBy = "playlist")
    private List<TrackPlaylist> tracks;

    public Playlist(String name, int user, int number_of_tracks) {
        this.name = name;
        this.user = user;
        this.number_of_tracks = number_of_tracks;
    }
}
