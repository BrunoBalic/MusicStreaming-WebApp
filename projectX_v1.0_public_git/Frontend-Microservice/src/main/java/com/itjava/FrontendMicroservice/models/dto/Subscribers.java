package com.itjava.FrontendMicroservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subscribers {
    private int id;
    private int user;
    private int userFollows;
    private Timestamp date;
}