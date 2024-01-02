package com.example.musicplatform.repository;

import com.example.musicplatform.dto.AlbumDto;
import com.example.musicplatform.entity.Routines;
import com.example.musicplatform.entity.tables.Artist;
import com.example.musicplatform.entity.tables.Genre;
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

    public UUID add(AlbumDto album) {
        var artistName = jooq
                .select(Artist.ARTIST.NICKNAME)
                .from(Artist.ARTIST)
                .where(Artist.ARTIST.ID.eq(album.artistId()))
                .fetchOneInto(String.class);
        var genreName = jooq
                .select(Genre.GENRE.NAME)
                .from(Genre.GENRE)
                .where(Genre.GENRE.ID.eq(album.genreId()))
                .fetchOneInto(String.class);

        return jooq.select(Routines.addAlbum(artistName, genreName, album.title())).fetchOneInto(UUID.class);
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

    public List<Album> searchAlbum(String query) {
        return jooq.selectFrom(ALBUM)
                .where(ALBUM.TITLE.like("%" + query + "%"))
                .fetch().map((record) -> record.into(Album.class));
    }
}
