package com.example.musicplatform.service;

import com.example.musicplatform.model.pojos.Playlist;
import com.example.musicplatform.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class PlaylistService {
    private final PlaylistRepository repository;

    public PlaylistService(PlaylistRepository repository) {
        this.repository = repository;
    }

    public Playlist getPlaylistInfo(UUID id) {
        return repository.getPlaylistInfo(id);
    }

    public HashMap<String, Object> getPlaylist(UUID id) {
        var playlistInfo = repository.getPlaylistInfo(id);
        var playlistSongs = repository.getPlaylistSongs(id);
        return new HashMap<>() {{
            put("info", playlistInfo);
            put("songs", playlistSongs);
        }};
    }
}
