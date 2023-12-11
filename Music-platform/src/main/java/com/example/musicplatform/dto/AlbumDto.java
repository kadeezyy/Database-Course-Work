package com.example.musicplatform.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AlbumDto {
    private final UUID artistId;
    private final UUID genreId;
    private final String title;
    private final LocalDateTime creationDate;
    private final Integer songsCount;
    private final Integer likesCount;
}
