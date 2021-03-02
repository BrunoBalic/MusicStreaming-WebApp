package com.itjava.MusicManagementMicroservice.entities.repositories;

import com.itjava.MusicManagementMicroservice.entities.Playlist;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PlaylistRepository extends PagingAndSortingRepository<Playlist, Integer> {
    List<Playlist> findByUser(int id);
}
