package com.example.musicplatform.repository;

import com.example.musicplatform.exception.NotFoundException;
import com.example.musicplatform.exception.enums.DataAccessMessages;
import com.example.musicplatform.model.pojos.Album;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class AlbumRepository {
    private final DSLContext jooq;
    private final com.example.musicplatform.entity.tables.Album ALBUM = com.example.musicplatform.entity.tables.Album.ALBUM;

    public AlbumRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    public UUID add(Album album) {
        return jooq.insertInto(ALBUM, ALBUM.ID, ALBUM.TITLE, ALBUM.ARTIST_ID, ALBUM.CREATION_DATE, ALBUM.GENRE_ID)
                .values(
                        album.getId(), album.getTitle(), album.getArtistId(), album.getCreationDate(), album.getGenreId()
                ).returning().fetchSingle().get(ALBUM.ID);
    }

    public Album get(UUID id) {
        return jooq.selectFrom(ALBUM).where(ALBUM.ID.equal(id)).fetchOptional().orElseThrow(
                () -> new NotFoundException(DataAccessMessages.OBJECT_NOT_FOUND.name())
        ).into(Album.class);
    }

    public List<Album> getArtistsAlbums(UUID artistId) {
        return jooq.selectFrom(ALBUM)
                .where(ALBUM.ARTIST_ID.equal(artistId))
                .fetch().map((record) -> record.into(Album.class));
    }
}
