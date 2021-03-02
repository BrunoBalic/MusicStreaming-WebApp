package com.itjava.MusicManagementMicroservice.entities.repositories;

import com.itjava.MusicManagementMicroservice.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface TrackRepository extends PagingAndSortingRepository<Track, Integer> {
    List<Track> findByUser(int id);
}