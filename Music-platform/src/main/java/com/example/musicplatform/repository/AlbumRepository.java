package com.example.musicplatform.repository;

import com.example.musicplatform.dto.AlbumDto;
import com.example.musicplatform.entity.Routines;
import com.example.musicplatform.entity.tables.*;
import com.example.musicplatform.exception.NotFoundException;
import com.example.musicplatform.exception.NotUniqueObjectException;
import com.example.musicplatform.exception.enums.DataAccessMessages;
import com.example.musicplatform.model.pojos.Album;
import com.example.musicplatform.model.pojos.CustomUser;
import com.example.musicplatform.model.pojos.Song;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

import static com.example.musicplatform.entity.tables.Song.SONG;

@Repository
public class AlbumRepository {
    private final DSLContext jooq;
    private final com.example.musicplatform.entity.tables.Album ALBUM = com.example.musicplatform.entity.tables.Album.ALBUM;

    public AlbumRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    public UUID add(CustomUser user, AlbumDto album) {
        var artistName = jooq
                .select(Artist.ARTIST.NICKNAME)
                .from(Artist.ARTIST)
                .where(Artist.ARTIST.USER_ID.eq(user.getId()))
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

    public UUID insertSongIntoAlbum(CustomUser user, UUID songId, UUID albumId) {
        return jooq.insertInto(AlbumSongs.ALBUM_SONGS,
                AlbumSongs.ALBUM_SONGS.ALBUM_ID,
                AlbumSongs.ALBUM_SONGS.SONG_ID).values(
                albumId, songId
        ).returning().fetchSingle().get(AlbumSongs.ALBUM_SONGS.ALBUM_ID);
    }

    public List<Song> getAlbumSongs(UUID albumId) {
        return jooq.selectFrom(SONG
                        .leftJoin(AlbumSongs.ALBUM_SONGS)
                        .on(AlbumSongs.ALBUM_SONGS.SONG_ID.eq(SONG.ID)))
                .where(AlbumSongs.ALBUM_SONGS.ALBUM_ID.equal(albumId))
                .fetch()
                .stream().distinct()
                .map((record -> record.into(Song.class))).toList();
    }

    public List<Album> getLikedAlbums(CustomUser user) {
        return jooq.selectFrom(ALBUM
                        .leftJoin(UserFavouriteAlbums.USER_FAVOURITE_ALBUMS)
                        .on(UserFavouriteAlbums.USER_FAVOURITE_ALBUMS.ALBUM_ID.eq(ALBUM.ID)))
                .where(UserFavouriteAlbums.USER_FAVOURITE_ALBUMS.USER_ID.eq(user.getId()))
                .fetch()
                .stream().distinct()
                .map((record -> record.into(Album.class))).toList();
    }

    public void likeAlbum(CustomUser user, UUID albumId) {
        var object = jooq.selectFrom(UserFavouriteAlbums.USER_FAVOURITE_ALBUMS)
                .where(UserFavouriteAlbums.USER_FAVOURITE_ALBUMS.USER_ID.eq(user.getId())
                        .and(UserFavouriteAlbums.USER_FAVOURITE_ALBUMS.ALBUM_ID.eq(albumId))
                )
                .fetchOptional()
                .orElse(null);
        if (object != null) {
            jooq.deleteFrom(UserFavouriteAlbums.USER_FAVOURITE_ALBUMS)
                    .where(UserFavouriteAlbums.USER_FAVOURITE_ALBUMS.USER_ID.eq(user.getId())
                            .and(UserFavouriteAlbums.USER_FAVOURITE_ALBUMS.ALBUM_ID.eq(albumId))
                    ).execute();
            return;
        }
        jooq.insertInto(UserFavouriteAlbums.USER_FAVOURITE_ALBUMS,
                        UserFavouriteAlbums.USER_FAVOURITE_ALBUMS.ALBUM_ID,
                        UserFavouriteAlbums.USER_FAVOURITE_ALBUMS.USER_ID)
                .values(albumId, user.getId())
                .returning()
                .fetch();
    }
}
