package com.example.musicplatform.dto;


import java.util.UUID;

public record AlbumDto(UUID artistId, UUID genreId, String title) {
}
