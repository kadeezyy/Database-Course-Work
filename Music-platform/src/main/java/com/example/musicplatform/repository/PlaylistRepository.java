package com.example.musicplatform.repository;

import com.example.musicplatform.entity.tables.PlaylistSongs;
import com.example.musicplatform.exception.NotFoundException;
import com.example.musicplatform.exception.enums.DataAccessMessages;
import com.example.musicplatform.model.pojos.Playlist;
import com.example.musicplatform.model.pojos.Song;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PlaylistRepository {
    private final DSLContext jooq;
    private final com.example.musicplatform.entity.tables.Playlist PLAYLIST = com.example.musicplatform.entity.tables.Playlist.PLAYLIST;
    private final com.example.musicplatform.entity.tables.Song SONG = com.example.musicplatform.entity.tables.Song.SONG;

    public PlaylistRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    public Playlist getPlaylistInfo(UUID id) {
        return jooq.selectFrom(PLAYLIST).where(PLAYLIST.ID.equal(id)).fetchOptional().orElseThrow(
                () -> new NotFoundException(DataAccessMessages.OBJECT_NOT_FOUND.name())
        ).into(Playlist.class);
    }

    public List<Song> getPlaylistSongs(UUID playlistId) {
        return jooq.selectFrom(SONG
                        .leftJoin(PlaylistSongs.PLAYLIST_SONGS)
                        .on(PlaylistSongs.PLAYLIST_SONGS.PLAYLIST_ID.equal(playlistId)))

                .fetch()
                .stream().distinct()
                .map((record -> record.into(Song.class))).toList();
    }


}
