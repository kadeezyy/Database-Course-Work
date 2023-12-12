package com.example.musicplatform.controller;

import com.example.musicplatform.model.pojos.Playlist;
import com.example.musicplatform.service.PlaylistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/v1/playlist")
public class PlaylistController {
    private final PlaylistService service;

    public PlaylistController(PlaylistService service) {
        this.service = service;
    }

    @GetMapping("/getPlaylistInfo/{id}")
    public Playlist getPlaylistInfo(@PathVariable UUID id) {
        return service.getPlaylistInfo(id);
    }

    @GetMapping("/getPlaylist/{id}")
    public HashMap<String, Object> getPlaylist(@PathVariable UUID id) {
        return service.getPlaylist(id);
    }
}
