package com.itjava.UserManagementMicroservice.entities.repositories;

import com.itjava.UserManagementMicroservice.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    User findByAuth0id(String auth0Id);
    // JPQL
    @Query("SELECT u FROM User u WHERE (u.first_name LIKE CONCAT('%', :str, '%') OR u.last_name LIKE CONCAT('%', :str, '%') OR u.username LIKE CONCAT('%', :str, '%'))")
    List<User> searchUsers(@Param("str") String searchString, Sort sort);
}
