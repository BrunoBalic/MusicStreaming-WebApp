package com.itjava.MusicManagementMicroservice.entities.services;

import com.itjava.MusicManagementMicroservice.entities.TrackPlaylist;
import com.itjava.MusicManagementMicroservice.entities.repositories.TrackPlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackPlaylistService {

    @Autowired
    TrackPlaylistRepository trackPlaylistRepository;

    public void addTrackToPlaylist(int trackId, int PlaylistId) {
        trackPlaylistRepository.save(new TrackPlaylist(trackId, PlaylistId));
    }

    public void removeTrackFromPlaylist(int trackId, int PlaylistId) {
        trackPlaylistRepository.delete(trackPlaylistRepository.findByTrackAndPlaylist(trackId, PlaylistId));
    }

}
