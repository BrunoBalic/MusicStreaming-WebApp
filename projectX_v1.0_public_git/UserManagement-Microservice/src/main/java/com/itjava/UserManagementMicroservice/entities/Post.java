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
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    @Column(name = "PUBLISH_DATE", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp published;
    private int user;

    public Post(String content, int userId) {
        this.content = content;
        this.user = userId;
    }

}
