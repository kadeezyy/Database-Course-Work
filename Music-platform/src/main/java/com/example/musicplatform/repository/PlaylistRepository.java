package com.example.musicplatform.repository;

import com.example.musicplatform.entity.Routines;
import com.example.musicplatform.entity.routines.AddPlaylist;
import com.example.musicplatform.entity.tables.PlaylistSongs;
import com.example.musicplatform.exception.NotFoundException;
import com.example.musicplatform.exception.enums.DataAccessMessages;
import com.example.musicplatform.model.pojos.CustomUser;
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


    public UUID createPlaylist(CustomUser user, Playlist playlist) {
        return jooq.select(Routines.addPlaylist(user.getUsername(), playlist.getTitle())).fetchOne(0, UUID.class);
//        return jooq.insertInto(PLAYLIST,
//                PLAYLIST.ID,
//                PLAYLIST.TITLE,
//                PLAYLIST.CREATION_DATE,
//                PLAYLIST.LAST_UPDATED,
//                PLAYLIST.USER_CREATOR_ID,
//                PLAYLIST.LIKES_COUNT,
//                PLAYLIST.SONGS_COUNT).values(
//                playlist.getId(),
//                playlist.getTitle(),
//                playlist.getCreationDate(),
//                playlist.getLastUpdated(),
//                playlist.getUserCreatorId(),
//                playlist.getLikesCount(),
//                playlist.getSongsCount()
//        ).returning().fetchSingle().get(PLAYLIST.ID);
    }

    public UUID insertSongIntoPlaylist(CustomUser user, UUID songId, UUID playlistId) {
        return jooq.insertInto(PlaylistSongs.PLAYLIST_SONGS,
                PlaylistSongs.PLAYLIST_SONGS.PLAYLIST_ID,
                PlaylistSongs.PLAYLIST_SONGS.SONG_ID).values(
                playlistId, songId
        ).returning().fetchSingle().get(PlaylistSongs.PLAYLIST_SONGS.PLAYLIST_ID);
    }

    public UUID removeSongFromPlaylist(UUID songId, UUID playlistId) {
        return jooq.deleteFrom(PlaylistSongs.PLAYLIST_SONGS)
                .where(PlaylistSongs.PLAYLIST_SONGS.PLAYLIST_ID.equal(playlistId)
                        .and(PlaylistSongs.PLAYLIST_SONGS.SONG_ID.equal(songId)))
                .returning().fetchSingle().get(PlaylistSongs.PLAYLIST_SONGS.PLAYLIST_ID);
    }
}
