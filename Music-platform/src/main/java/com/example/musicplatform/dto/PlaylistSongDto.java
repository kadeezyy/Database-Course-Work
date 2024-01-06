package com.example.musicplatform.dto;

import java.util.UUID;

public record PlaylistSongDto(UUID playlistId, UUID songId) {
}
