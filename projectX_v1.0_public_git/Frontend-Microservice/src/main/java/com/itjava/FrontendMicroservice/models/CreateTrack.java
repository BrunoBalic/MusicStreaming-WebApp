package com.itjava.FrontendMicroservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateTrack {
    private String title;
    private int duration; // ovo se izracunava na osnovu file-a
    private int user;
    private String file_path;
}
