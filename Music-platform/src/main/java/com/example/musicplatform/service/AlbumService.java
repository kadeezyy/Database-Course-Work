package com.example.musicplatform.service;

import com.example.musicplatform.dto.AlbumDto;
import com.example.musicplatform.model.pojos.Album;
import com.example.musicplatform.model.pojos.CustomUser;
import com.example.musicplatform.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

    public HashMap<String, UUID> insertAlbum(CustomUser user, AlbumDto album) {
        return new HashMap<>() {{
            put("id", repository.add(album));
        }};
    }

    public List<Album> searchAlbum(String query) {return repository.searchAlbum(query);}


}
