package com.example.musicplatform.service;

import com.example.musicplatform.model.pojos.Album;
import com.example.musicplatform.model.pojos.Artist;
import com.example.musicplatform.model.pojos.Song;
import com.example.musicplatform.repository.AlbumRepository;
import com.example.musicplatform.repository.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    public ArtistService(ArtistRepository artistRepository, AlbumRepository albumRepository) {
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    public List<Album> getArtistsAlbums(UUID artistId) {
        return albumRepository.getArtistsAlbums(artistId);
    }

    public Artist getArtistInfo(UUID id) {
        return artistRepository.get(id);
    }

    public List<Song> getAllSongs(UUID id) {
        return artistRepository.getAllSongs(id);
    }
}
