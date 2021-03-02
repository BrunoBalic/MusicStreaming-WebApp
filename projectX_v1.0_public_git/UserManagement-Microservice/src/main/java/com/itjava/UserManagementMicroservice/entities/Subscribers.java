package com.itjava.UserManagementMicroservice.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Subscribers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int user;
    @Column(name = "USER_FOLLOWS")
    private int userFollows;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp date;

    public Subscribers(int userId, int iWantToFollowUserId) {
        this.user = userId;
        this.userFollows = iWantToFollowUserId;
    }
}
