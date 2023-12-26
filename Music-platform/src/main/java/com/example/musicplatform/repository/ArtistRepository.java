package com.example.musicplatform.repository;

import com.example.musicplatform.dto.ArtistDto;
import com.example.musicplatform.entity.tables.ArtistSongs;
import com.example.musicplatform.exception.NotFoundException;
import com.example.musicplatform.exception.enums.DataAccessMessages;
import com.example.musicplatform.model.pojos.Artist;
import com.example.musicplatform.model.pojos.CustomUser;
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
                .fetchInto(Song.class);
    }

    public List<Artist> searchArtist(String query) {
        return jooq.selectFrom(ARTIST)
                .where(ARTIST.NICKNAME.like("%" + query + "%"))
                .fetch().map((record) -> record.into(Artist.class));
    }

    public Artist createArtist(CustomUser user, ArtistDto artist) {
        return null;
    }
}
