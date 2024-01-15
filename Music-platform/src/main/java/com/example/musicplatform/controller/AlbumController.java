package com.example.musicplatform.controller;

import com.example.musicplatform.dto.AlbumDto;
import com.example.musicplatform.dto.AlbumSongDto;
import com.example.musicplatform.model.pojos.Album;
import com.example.musicplatform.model.pojos.CustomUser;
import com.example.musicplatform.model.pojos.Song;
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
    public Map<String, UUID> insertSongIntoAlbum(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody AlbumSongDto albumSongDto
    ) {
        return service.insertSongIntoAlbum(user, albumSongDto.songId(), albumSongDto.albumId());
    }

    @GetMapping("/getSongs/{id}")
    public HashMap<String, Object> getAlbumSongs(@PathVariable UUID id) {
        return service.getAlbumSongs(id);
    }

    @GetMapping("/getLikedAlbums/")
    public List<Album> getLikedSongs(@AuthenticationPrincipal CustomUser user) {
        return service.getLikedAlbums(user);
    }

    @PostMapping("/likeAlbum/{id}")
    public void likeAlbum(
            @AuthenticationPrincipal CustomUser user,
            @PathVariable UUID id
    ) {
        service.likeAlbum(user, id);
    }
}
