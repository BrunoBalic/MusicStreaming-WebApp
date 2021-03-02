package com.itjava.MusicManagementMicroservice.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TRACK")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "PUBLISH_DATE")
    @CreationTimestamp
    private Timestamp publish_date;
    @Column(name = "DURATION")
    private int duration;
    @Column(name = "USER")
    private int user;
    @Column(name = "FILE_PATH")
    private String file_path;

    @OneToMany(mappedBy = "track")
    private List<TrackPlaylist> playlists;

    public Track(String title, int duration, int user, String file_path) {
        this.title = title;
        this.duration = duration;
        this.user = user;
        this.file_path = file_path;
    }
}
