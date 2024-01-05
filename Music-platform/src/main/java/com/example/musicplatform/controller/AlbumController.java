package com.example.musicplatform.controller;

import com.example.musicplatform.dto.AlbumDto;
import com.example.musicplatform.model.pojos.Album;
import com.example.musicplatform.model.pojos.CustomUser;
import com.example.musicplatform.service.AlbumService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/album")
public class AlbumController {
    private final AlbumService service;

    public AlbumController(AlbumService service) {
        this.service = service;
    }

    @GetMapping("/{albumId}")
    public Album getAlbum(@PathVariable UUID albumId) {
        return service.getAlbum(albumId);
    }

    @GetMapping("/search/{query}")
    public List<Album> searchAlbum(@PathVariable String query) {
        return service.searchAlbum(query);
    }

    @PostMapping("/create_album")
    public Map<String, UUID> insertAlbum(@AuthenticationPrincipal CustomUser user, @RequestBody AlbumDto album) {
        return service.insertAlbum(user, album);
    }

    @PostMapping("/insertSong")
    public Map<String, UUID> insertSongIntoPlaylist(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody UUID songId,
            @RequestBody UUID playlistId
    ) {
        return service.insertSongIntoAlbum(user, songId, playlistId);
    }

    @GetMapping("/getSongs/{id}")
    public HashMap<String, Object> getPlaylist(@PathVariable UUID id) {
        return service.getAlbumSongs(id);
    }

}
