package com.example.musicplatform.service;

import com.example.musicplatform.dto.SongDto;
import com.example.musicplatform.model.pojos.CustomUser;
import com.example.musicplatform.model.pojos.Playlist;
import com.example.musicplatform.model.pojos.Song;
import com.example.musicplatform.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SongService {
    private final SongRepository repository;

    public SongService(SongRepository repository) {
        this.repository = repository;
    }

    public Map<String, UUID> createSong(CustomUser user, SongDto song) {
        return new HashMap<>() {{
            put("id", repository.createSong(user, song));
        }};
    }

    public Song getSong(UUID id) {
        return repository.getSong(id);
    }

    public List<Song> searchSong(String query) {return repository.searchSong(query);}

    public String getArtist(UUID songId) {
        return repository.getAuthor(songId);
    }
}
