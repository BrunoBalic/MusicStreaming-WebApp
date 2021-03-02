package com.itjava.AudioStreamingMicroservice.controllers;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//@RestController
@Controller
@RequestMapping("/api/streaming/music")
public class MainController {

    private final String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/uploads/music";

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> uploadFile(@RequestPart(value = "file") ByteArrayResource file, @RequestPart(value = "id") int id) throws IOException {
        Path fileNameAndPath = Paths.get(uploadDirectory, "id_" + String.valueOf(id) + ".mp3");
        Files.write(fileNameAndPath, file.getByteArray());
        return ResponseEntity.status(200).body(null);
    }

    @RequestMapping(value = "/play/{fileName}", produces = "audio/mpeg", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource playFile(@PathVariable String fileName) {
        return new FileSystemResource(System.getProperty("user.dir") + "/src/main/resources/uploads/music/" + fileName);
    }

}
