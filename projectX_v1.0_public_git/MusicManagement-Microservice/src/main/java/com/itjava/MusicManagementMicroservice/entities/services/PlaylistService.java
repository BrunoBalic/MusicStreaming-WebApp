package com.itjava.MusicManagementMicroservice.entities.services;

import com.itjava.MusicManagementMicroservice.entities.Playlist;
import com.itjava.MusicManagementMicroservice.entities.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    @Autowired
    PlaylistRepository playlistRepository;

    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public Playlist getPlaylist(int id) {
        return playlistRepository.findById(id).orElse(null);
    }

    public List<Playlist> getAllPlaylistsByUserId(int id) {
        return playlistRepository.findByUser(id);
    }

    public Playlist updatePlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(int id) {
        playlistRepository.deleteById(id);
    }

}
