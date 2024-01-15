package com.example.musicplatform.repository;

import com.example.musicplatform.dto.SongDto;
import com.example.musicplatform.entity.Routines;
import com.example.musicplatform.entity.tables.Artist;
import com.example.musicplatform.entity.tables.ArtistSongs;
import com.example.musicplatform.entity.tables.Genre;
import com.example.musicplatform.entity.tables.UserLikedSongs;
import com.example.musicplatform.exception.NotFoundException;
import com.example.musicplatform.exception.enums.DataAccessMessages;
import com.example.musicplatform.model.pojos.AlbumSongs;
import com.example.musicplatform.model.pojos.CustomUser;
import com.example.musicplatform.model.pojos.Playlist;
import com.example.musicplatform.model.pojos.Song;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    public UUID createSong(CustomUser user, SongDto song) {
        var genreName = jooq
                .select(Genre.GENRE.NAME)
                .from(Genre.GENRE)
                .where(Genre.GENRE.ID.eq(song.genreId()))
                .fetchOneInto(String.class);
        var songId = jooq.select(Routines.addSong(genreName, song.title())).fetchOneInto(UUID.class);
        var artistId = jooq
                .select(Artist.ARTIST.ID)
                .from(Artist.ARTIST)
                .where(Artist.ARTIST.USER_ID.eq(user.getId()))
                .fetchOneInto(UUID.class);
        jooq.select(Routines.addArtistSong(artistId, songId)).fetch();
        return songId;
    }

    public List<Song> searchSong(String query) {
        return jooq.selectFrom(SONG)
                .where(SONG.TITLE.like("%" + query + "%"))
                .fetch().map((record) -> record.into(Song.class));
    }

    public String getAuthor(UUID songId) {
        return jooq.select(Artist.ARTIST.NICKNAME)
                .from(ArtistSongs.ARTIST_SONGS)
                .join(Artist.ARTIST)
                .on(Artist.ARTIST.ID.eq(ArtistSongs.ARTIST_SONGS.ARTIST_ID))
                .where(ArtistSongs.ARTIST_SONGS.SONG_ID.eq(songId))
                .fetchOneInto(String.class);
    }

    public List<Song> getLikedSongs(CustomUser user) {
        return jooq.selectFrom(SONG
                        .leftJoin(UserLikedSongs.USER_LIKED_SONGS)
                        .on(UserLikedSongs.USER_LIKED_SONGS.SONG_ID.eq(SONG.ID)))
                .where(UserLikedSongs.USER_LIKED_SONGS.USER_ID.eq(user.getId()))
                .fetch()
                .stream()
                .distinct()
                .map((record -> record.into(Song.class)))
                .toList();
    }
}
