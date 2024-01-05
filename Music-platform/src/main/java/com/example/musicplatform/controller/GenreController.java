package com.example.musicplatform.controller;

import com.example.musicplatform.dto.GenreDto;
import com.example.musicplatform.model.pojos.Genre;
import com.example.musicplatform.service.GenreService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/genre")
public class GenreController {
    private final GenreService service;

    public GenreController(GenreService service) {
        this.service = service;
    }

    @GetMapping("/getGenreInfo/{id}")
    public Genre getGenreInfo(@PathVariable UUID id) {
        return service.getGenreInfo(id);
    }

    @PostMapping("/insertGenre")
    public Map<String, UUID> createGenre(@RequestBody GenreDto genre) {
        return service.createGenre(genre);
    }
}
