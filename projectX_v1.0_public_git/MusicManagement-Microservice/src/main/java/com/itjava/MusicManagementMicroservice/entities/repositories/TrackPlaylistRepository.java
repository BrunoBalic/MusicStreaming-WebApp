package com.itjava.MusicManagementMicroservice.entities.repositories;

import com.itjava.MusicManagementMicroservice.entities.TrackPlaylist;
import org.springframework.data.repository.CrudRepository;

public interface TrackPlaylistRepository extends CrudRepository<TrackPlaylist, Integer> {
    TrackPlaylist findByTrackAndPlaylist(int trackId, int playlistId);
}
