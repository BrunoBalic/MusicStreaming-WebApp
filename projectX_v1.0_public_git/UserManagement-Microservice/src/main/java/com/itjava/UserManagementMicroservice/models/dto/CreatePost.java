package com.itjava.UserManagementMicroservice.models.dto;

import com.itjava.UserManagementMicroservice.entities.Post;
import com.itjava.UserManagementMicroservice.entities.Subscribers;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
public class CreatePost {
    private String content;
    private int user;
}
