package com.example.musicplatform.controller;

import com.example.musicplatform.dto.SongDto;
import com.example.musicplatform.model.pojos.CustomUser;
import com.example.musicplatform.model.pojos.Song;
import com.example.musicplatform.service.SongService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
    public Map<String, UUID> insertSong(@AuthenticationPrincipal CustomUser user, @RequestBody SongDto song) {
        return service.createSong(user, song);
    }

    @GetMapping("/{id}")
    public Song getSong(@PathVariable UUID id) {
        return service.getSong(id);
    }

    @GetMapping("/search/{query}")
    public List<Song> searchSong(@PathVariable String query) {
        return service.searchSong(query);
    }

    @GetMapping("/getAuthor/{songId}")
    public Map<String, String> getAuthor(@PathVariable UUID songId) {
        Map<String, String> map = new HashMap<>();
        map.put("title", service.getArtist(songId));
        return map;
    }
}
