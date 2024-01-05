package com.example.musicplatform.service;

import com.example.musicplatform.dto.GenreDto;
import com.example.musicplatform.model.pojos.Genre;
import com.example.musicplatform.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class GenreService {
    private final GenreRepository repository;

    public GenreService(GenreRepository repository) {
        this.repository = repository;
    }

    public Genre getGenreInfo(UUID id) {
        return repository.getGenreInfo(id);
    }

    public Map<String, UUID> createGenre(GenreDto genre) {
        return new HashMap<>(){{
            put("id", repository.addGenre(genre));
        }};
    }

    public List<Genre> getAllGenres() {
        return repository.getAllGenres();
    }
}
