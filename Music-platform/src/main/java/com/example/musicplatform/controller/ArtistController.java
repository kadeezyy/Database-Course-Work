package com.example.musicplatform.controller;

import com.example.musicplatform.dto.ArtistDto;
import com.example.musicplatform.model.pojos.Album;
import com.example.musicplatform.model.pojos.Artist;
import com.example.musicplatform.model.pojos.CustomUser;
import com.example.musicplatform.model.pojos.Song;
import com.example.musicplatform.service.ArtistService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/artist")
public class ArtistController {
    private final ArtistService service;

    public ArtistController(ArtistService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Artist getArtistInfo(@PathVariable UUID id) {
        return service.getArtistInfo(id);
    }


    //todo: add 'limit' param
    @GetMapping("/getAllAlbums/{id}")
    public List<Album> getArtistsAlbums(@PathVariable UUID id) {
        return service.getArtistsAlbums(id);
    }

    @PostMapping("/create")
    public Artist createArtist(@AuthenticationPrincipal CustomUser user, @RequestBody ArtistDto artist) {
        return service.createArtist(user, artist);
    }

    @GetMapping("/getArtistsSongs/{id}")
    public List<Song> getAllSongs(@PathVariable UUID id) {
        return service.getAllSongs(id);
    }

    @GetMapping("/search/{query}")
    public List<Artist> searchArtist(@PathVariable String query) {
        return service.searchArtist(query);
    }

    @PostMapping("/likeArtist/{id}")
    public void likeArtist(
            @AuthenticationPrincipal CustomUser user,
            @PathVariable UUID id
    ) {
        service.likeArtist(user, id);
    }

    @GetMapping("/getLikedArtist/")
    public List<Artist> getLikedArtist(@AuthenticationPrincipal CustomUser user) {
        return service.getLikedArtist(user);
    }
}
