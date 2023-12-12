package com.example.musicplatform.service;

import com.example.musicplatform.model.pojos.Song;
import com.example.musicplatform.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SongService {
    private final SongRepository repository;

    public SongService(SongRepository repository) {
        this.repository = repository;
    }

    public Map<String, UUID> createSong(Song song) {
        return new HashMap<>() {{
            put("id", repository.createSong(song));
        }};
    }

    public Song getSong(UUID id) {
        return repository.getSong(id);
    }
}
