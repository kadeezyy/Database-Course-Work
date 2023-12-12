package com.example.musicplatform.service;

import com.example.musicplatform.model.pojos.Playlist;
import com.example.musicplatform.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
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

    public Map<String, UUID> createPlaylist(Playlist playlist) {
        return new HashMap<>() {{
            put("id", repository.createPlaylist(playlist));
        }};
    }

    public Map<String, UUID> insertSongIntoPlaylist(UUID songId, UUID playlistId) {
        return new HashMap<>() {{
            put("id", repository.insertSongIntoPlaylist(songId, playlistId));
        }};
    }

    public Map<String, UUID> removeSongFromPlaylist(UUID songId, UUID playlistId) {
        return new HashMap<>(){{
            put("id", repository.removeSongFromPlaylist(songId, playlistId));
        }};
    }
}
