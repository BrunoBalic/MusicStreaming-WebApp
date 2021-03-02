package com.itjava.FrontendMicroservice.controllers;


import com.itjava.FrontendMicroservice.microservices.MusicManagement.TrackService;
import com.itjava.FrontendMicroservice.microservices.Token;
import com.itjava.FrontendMicroservice.microservices.UserManagement.UserService;
import com.itjava.FrontendMicroservice.models.CreateTrack;
import com.itjava.FrontendMicroservice.models.entities.Track;
import com.itjava.FrontendMicroservice.models.entities.User;
import kong.unirest.ContentType;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileUploadController {
    private String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/uploads/music";
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    Token token;

    @Autowired
    UserService userService;
    @Autowired
    TrackService trackService;

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String uploadPage(@AuthenticationPrincipal OidcUser principal, Model model) {
        return "upload_page";
    }

    @RequestMapping(value = "/uploaded", method = RequestMethod.POST)
    public String uploaded(@AuthenticationPrincipal OidcUser principal, Model model, @RequestParam("files") MultipartFile[] files, @RequestParam("song_name") String song_name) throws IOException {

        User currentUser = userService.getUserByAuth0Id(principal.getSubject());

        StringBuilder fileNames = new StringBuilder();
        for (MultipartFile file : files) {
            Track track = trackService.createNewTrack(new CreateTrack(song_name, 0, currentUser.getId(), ""));
            //
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setBearerAuth(token.getAccessToken());

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            
            body.add("file", new ByteArrayResource(file.getBytes()));
            Integer id = track.getId();
            body.add("id", id);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<Void> response = restTemplate.postForEntity(
                    "http://localhost:8103/api/streaming/music/upload",
                    requestEntity,
                    Void.class);
        }
        model.addAttribute("msg", "Successfully uploaded files " + fileNames.toString());
        return "uploadStatus";
    }
}