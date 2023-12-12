package com.example.musicplatform.service;

import com.example.musicplatform.model.pojos.Album;
import com.example.musicplatform.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class AlbumService {
    private final AlbumRepository repository;

    public AlbumService(AlbumRepository repository) {
        this.repository = repository;
    }

    public Album getAlbum(UUID albumId) {
        return repository.get(albumId);
    }

    public HashMap<String, UUID> insertAlbum(Album album) {
        return new HashMap<>() {{
            put("id", repository.add(album));
        }};
    }


}
