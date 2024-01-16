package com.example.musicplatform.repository;

import com.example.musicplatform.dto.ArtistDto;
import com.example.musicplatform.entity.Routines;
import com.example.musicplatform.entity.tables.ArtistSongs;
import com.example.musicplatform.entity.tables.UserFavouriteAlbums;
import com.example.musicplatform.entity.tables.UserFavouriteArtists;
import com.example.musicplatform.entity.tables.UserLikedSongs;
import com.example.musicplatform.exception.NotFoundException;
import com.example.musicplatform.exception.NotUniqueObjectException;
import com.example.musicplatform.exception.enums.DataAccessMessages;
import com.example.musicplatform.model.pojos.Artist;
import com.example.musicplatform.model.pojos.CustomUser;
import com.example.musicplatform.model.pojos.Song;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.example.musicplatform.entity.tables.Song.SONG;

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

    public Artist getByUser(UUID id) {
        return jooq.selectFrom(ARTIST).where(ARTIST.USER_ID.equal(id)).fetchOptional().orElseThrow(
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
        return jooq.select(Routines.addArtist(user.getUsername(), artist.nickname()))
                .fetchOneInto(Artist.class);
    }

    public void likeArtist(CustomUser user, UUID artistId) {
        var object = jooq.selectFrom(UserFavouriteArtists.USER_FAVOURITE_ARTISTS)
                .where(UserFavouriteArtists.USER_FAVOURITE_ARTISTS.USER_ID.eq(user.getId())
                        .and(UserFavouriteArtists.USER_FAVOURITE_ARTISTS.ARTIST_ID.eq(artistId))
                )
                .fetchOptional()
                .orElse(null);
        if (object != null) {
            jooq.deleteFrom(UserFavouriteArtists.USER_FAVOURITE_ARTISTS)
                    .where(UserFavouriteArtists.USER_FAVOURITE_ARTISTS.USER_ID.eq(user.getId())
                            .and(UserFavouriteArtists.USER_FAVOURITE_ARTISTS.ARTIST_ID.eq(artistId))
                    ).execute();
            return;
        }
        jooq.insertInto(UserFavouriteArtists.USER_FAVOURITE_ARTISTS,
                        UserFavouriteArtists.USER_FAVOURITE_ARTISTS.ARTIST_ID,
                        UserFavouriteArtists.USER_FAVOURITE_ARTISTS.USER_ID)
                .values(artistId, user.getId())
                .returning()
                .fetch();
    }

    public List<Artist> getFavouriteArtists(CustomUser user) {
        return jooq.selectFrom(ARTIST
                        .leftJoin(UserFavouriteArtists.USER_FAVOURITE_ARTISTS)
                        .on(UserFavouriteArtists.USER_FAVOURITE_ARTISTS.ARTIST_ID.eq(ARTIST.ID)))
                .where(UserFavouriteArtists.USER_FAVOURITE_ARTISTS.USER_ID.eq(user.getId()))
                .fetch()
                .stream()
                .distinct()
                .map((record -> record.into(Artist.class)))
                .toList();
    }
}
