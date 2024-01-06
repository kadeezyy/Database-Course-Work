package com.example.musicplatform.controller;

import com.example.musicplatform.dto.PlaylistDto;
import com.example.musicplatform.dto.PlaylistSongDto;
import com.example.musicplatform.model.pojos.CustomUser;
import com.example.musicplatform.model.pojos.Playlist;
import com.example.musicplatform.model.pojos.PlaylistSongs;
import com.example.musicplatform.service.PlaylistService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
            @RequestBody PlaylistDto playlist
    ) {
        return service.createPlaylist(user, playlist);
    }

    @PostMapping("/insertSong")
    public Map<String, UUID> insertSongIntoPlaylist(
//            @AuthenticationPrincipal CustomUser user,
            @RequestBody PlaylistSongDto playlistSong
            ) {
        return service.insertSongIntoPlaylist(playlistSong.songId(), playlistSong.playlistId());
    }

    @DeleteMapping("/removeSong")
    public Map<String, UUID> removeSongFromPlaylist(@RequestBody UUID songId, @RequestBody UUID playlistId) {
        return service.removeSongFromPlaylist(songId, playlistId);
    }

    @GetMapping("/search/{query}")
    public List<Playlist> searchPlaylist(@PathVariable String query) {
        return service.searchPlaylist(query);
    }

    @PostMapping("/userPlaylists")
    public List<Playlist> userPlaylists(@AuthenticationPrincipal CustomUser user) {
        return service.getUserPlaylists(user.getId());
    }
}
