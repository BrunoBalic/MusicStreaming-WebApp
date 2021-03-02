package com.itjava.UserManagementMicroservice.entities.repositories;

import com.itjava.UserManagementMicroservice.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
    List<Post> findByUser(int id);
    Page<Post> findAllByUser(int id, Pageable pageable);
}
