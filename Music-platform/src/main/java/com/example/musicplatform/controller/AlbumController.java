package com.example.musicplatform.controller;

import com.example.musicplatform.model.pojos.Album;
import com.example.musicplatform.service.AlbumService;
import org.springframework.web.bind.annotation.*;

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
    public List<Album> searchAlbum(@PathVariable String query) {return service.searchAlbum(query);}

    @PostMapping
    public Map<String, UUID> insertAlbum(@RequestBody Album album) {
        return service.insertAlbum(album);
    }
}
