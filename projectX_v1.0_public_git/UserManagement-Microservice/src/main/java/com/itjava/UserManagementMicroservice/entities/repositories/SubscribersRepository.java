package com.itjava.UserManagementMicroservice.entities.repositories;

import com.itjava.UserManagementMicroservice.entities.Subscribers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "subscribers", path = "subscribers")
public interface SubscribersRepository extends PagingAndSortingRepository<Subscribers, Integer> {
    // SVI KOJE USER PRATI
    @RestResource(rel = "find_who_user_follows", path = "/user_follows")
    List<Subscribers> findAllByUser(int id);
    // SVI KOJI PRATE USER-a
    @RestResource(rel = "find_who_follows_user", path = "/follows_user")
    List<Subscribers> findAllByUserFollows(int id);
    // SVI KOJE USER PRATI
    @RestResource(rel = "find_who_user_follows-sp", path = "/sp/user_follows")
    Page<Subscribers> findAllByUser(int id, Pageable pageable);
    // SVI KOJI PRATE USER-a
    @RestResource(rel = "find_who_follows_user-sp", path = "/sp/follows_user")
    Page<Subscribers> findAllByUserFollows(int id, Pageable pageable);
    Subscribers findByUserAndUserFollows(int user, int userFollows);
}
