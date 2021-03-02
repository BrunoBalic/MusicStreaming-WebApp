package com.itjava.MusicManagementMicroservice.entities.services;

import com.itjava.MusicManagementMicroservice.entities.Track;
import com.itjava.MusicManagementMicroservice.entities.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackService {

    @Autowired
    TrackRepository trackRepository;

    public Track createTrack(Track track) {
        return trackRepository.save(track);
    }

    public Track getTrack(int id) {
        return trackRepository.findById(id).orElse(null);
    }

    public Iterable<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    public List<Track> getAllTracksByUserId(int userId) {
        return trackRepository.findByUser(userId);
    }

    public void deleteTrack(int id) {
        trackRepository.deleteById(id);
    }

}
