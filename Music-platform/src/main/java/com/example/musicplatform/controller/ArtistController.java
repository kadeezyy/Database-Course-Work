package com.example.musicplatform.controller;

import com.example.musicplatform.model.pojos.Album;
import com.example.musicplatform.model.pojos.Artist;
import com.example.musicplatform.model.pojos.Song;
import com.example.musicplatform.service.ArtistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/getAll/{id}")
    public List<Album> getArtistsAlbums(@PathVariable UUID id) {
        return service.getArtistsAlbums(id);
    }

    @GetMapping("/getArtistsSongs/{id}")
    public List<Song> getAllSongs(@PathVariable UUID id) {
        return service.getAllSongs(id);
    }

    @GetMapping("/search/{query}")
    public List<Artist> searchArtist(@PathVariable String query) {return service.searchArtist(query);}
}
