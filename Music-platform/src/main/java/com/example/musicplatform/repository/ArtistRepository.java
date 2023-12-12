package com.example.musicplatform.repository;

import com.example.musicplatform.entity.tables.ArtistSongs;
import com.example.musicplatform.exception.NotFoundException;
import com.example.musicplatform.exception.enums.DataAccessMessages;
import com.example.musicplatform.model.pojos.Artist;
import com.example.musicplatform.model.pojos.Song;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ArtistRepository {
    private final DSLContext jooq;
    private final com.example.musicplatform.entity.tables.Artist ARTIST = com.example.musicplatform.entity.tables.Artist.ARTIST;
    private final com.example.musicplatform.entity.tables.Song SONG = com.example.musicplatform.entity.tables.Song.SONG;

    public ArtistRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    public Artist get(UUID id) {
        return jooq.selectFrom(ARTIST).where(ARTIST.ID.equal(id)).fetchOptional().orElseThrow(
                () -> new NotFoundException(DataAccessMessages.OBJECT_NOT_FOUND.name())
        ).into(Artist.class);
    }

    public List<Song> getAllSongs(UUID id) {
        return jooq.selectFrom(SONG
                        .join(ArtistSongs.ARTIST_SONGS)
                        .on(ArtistSongs.ARTIST_SONGS.ARTIST_ID.equal(id))
                )
                .fetch().map(this::mapToSong);
    }

    private Song mapToSong(Record record) {
        Song song = new Song();

        song.setId(record.get(SONG.ID));
        song.setTitle(record.get(SONG.TITLE));
        song.setCreationDate(record.get(SONG.CREATION_DATE));
        song.setGenreId(record.get(SONG.GENRE_ID));
        song.setLikesCount(record.get(SONG.LIKES_COUNT));

        return song;
    }
}
