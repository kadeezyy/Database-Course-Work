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


}
