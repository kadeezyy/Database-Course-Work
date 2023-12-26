package com.example.musicplatform.dto;

import lombok.Getter;

import java.util.UUID;

//@Getter
public record AlbumDto(UUID artistId, UUID genreId, String title) {
}
