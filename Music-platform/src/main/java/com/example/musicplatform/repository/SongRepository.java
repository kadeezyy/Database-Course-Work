package com.example.musicplatform.repository;

import com.example.musicplatform.exception.NotFoundException;
import com.example.musicplatform.exception.enums.DataAccessMessages;
import com.example.musicplatform.model.pojos.Song;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class SongRepository {
    private final DSLContext jooq;
    private final com.example.musicplatform.entity.tables.Song SONG = com.example.musicplatform.entity.tables.Song.SONG;

    public SongRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    public Song getSong(UUID id) {
        return jooq.selectFrom(SONG).where(SONG.ID.equal(id)).fetchOptional().orElseThrow(
                () -> new NotFoundException(DataAccessMessages.OBJECT_NOT_FOUND.name())
        ).into(Song.class);
    }

    public UUID createSong(Song song) {
        return jooq.insertInto(SONG,
                        SONG.ID,
                        SONG.TITLE,
                        SONG.GENRE_ID,
                        SONG.CREATION_DATE,
                        SONG.LIKES_COUNT)
                .values(
                        song.getId(),
                        song.getTitle(),
                        song.getGenreId(),
                        song.getCreationDate(),
                        song.getLikesCount()
                ).returning().fetchSingle().get(SONG.ID);
    }
}
