package com.example.musicplatform.controller;

import com.example.musicplatform.model.pojos.Song;
import com.example.musicplatform.service.SongService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/song")
public class SongController {
    private final SongService service;


    public SongController(SongService service) {
        this.service = service;
    }

    @PostMapping("/insertSong")
    public Map<String, UUID> insertSong(@RequestBody Song song) {
        return service.createSong(song);
    }

    @GetMapping("/getSong/{id}")
    public Song getSong(@PathVariable UUID id) {
        return service.getSong(id);
    }
}
