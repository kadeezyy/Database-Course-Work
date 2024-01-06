package com.example.musicplatform.service;

import com.example.musicplatform.dto.PlaylistDto;
import com.example.musicplatform.model.pojos.CustomUser;
import com.example.musicplatform.model.pojos.Playlist;
import com.example.musicplatform.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

    public Map<String, UUID> createPlaylist(CustomUser user, PlaylistDto playlist) {
        return new HashMap<>() {{
            put("id", repository.createPlaylist(user, playlist));
        }};
    }

    public Map<String, UUID> insertSongIntoPlaylist(UUID songId, UUID playlistId) {
        return new HashMap<>() {{
            put("id", repository.insertSongIntoPlaylist(songId, playlistId));
        }};
    }

    public Map<String, UUID> removeSongFromPlaylist(UUID songId, UUID playlistId) {
        return new HashMap<>() {{
            put("id", repository.removeSongFromPlaylist(songId, playlistId));
        }};
    }

    public List<Playlist> searchPlaylist(String query) {
        return repository.searchPlaylist(query);
    }

    public void likePlaylist(CustomUser user, UUID playlistId) {
        repository.likePlaylist(user, playlistId);
    }
}
