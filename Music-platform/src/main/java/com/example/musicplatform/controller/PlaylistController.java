package com.example.musicplatform.controller;

import com.example.musicplatform.model.pojos.CustomUser;
import com.example.musicplatform.model.pojos.Playlist;
import com.example.musicplatform.model.pojos.PlaylistSongs;
import com.example.musicplatform.service.PlaylistService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
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

    @GetMapping("/{id}")
    public HashMap<String, Object> getPlaylist(@PathVariable UUID id) {
        return service.getPlaylist(id);
    }

    @PostMapping("/insertPlaylist")
    public Map<String, UUID> createPlaylist(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody Playlist playlist
    ) {
        return service.createPlaylist(user, playlist);
    }

    @PostMapping("/insertSong")
    public Map<String, UUID> insertSongIntoPlaylist(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody UUID songId,
            @RequestBody UUID playlistId
    ) {
        return service.insertSongIntoPlaylist(user, songId, playlistId);
    }

    @DeleteMapping("/removeSong")
    public Map<String, UUID> removeSongFromPlaylist(@RequestBody UUID songId, @RequestBody UUID playlistId) {
        return service.removeSongFromPlaylist(songId, playlistId);
    }
}
