/*
 * This file is generated by jOOQ.
 */
package com.example.musicplatform.entity.tables.records;


import com.example.musicplatform.entity.tables.AlbumSongs;

import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AlbumSongsRecord extends UpdatableRecordImpl<AlbumSongsRecord> implements Record2<UUID, UUID> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.album_songs.album_id</code>.
     */
    public AlbumSongsRecord setAlbumId(UUID value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.album_songs.album_id</code>.
     */
    public UUID getAlbumId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.album_songs.song_id</code>.
     */
    public AlbumSongsRecord setSongId(UUID value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.album_songs.song_id</code>.
     */
    public UUID getSongId() {
        return (UUID) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<UUID, UUID> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<UUID, UUID> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<UUID, UUID> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return AlbumSongs.ALBUM_SONGS.ALBUM_ID;
    }

    @Override
    public Field<UUID> field2() {
        return AlbumSongs.ALBUM_SONGS.SONG_ID;
    }

    @Override
    public UUID component1() {
        return getAlbumId();
    }

    @Override
    public UUID component2() {
        return getSongId();
    }

    @Override
    public UUID value1() {
        return getAlbumId();
    }

    @Override
    public UUID value2() {
        return getSongId();
    }

    @Override
    public AlbumSongsRecord value1(UUID value) {
        setAlbumId(value);
        return this;
    }

    @Override
    public AlbumSongsRecord value2(UUID value) {
        setSongId(value);
        return this;
    }

    @Override
    public AlbumSongsRecord values(UUID value1, UUID value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AlbumSongsRecord
     */
    public AlbumSongsRecord() {
        super(AlbumSongs.ALBUM_SONGS);
    }

    /**
     * Create a detached, initialised AlbumSongsRecord
     */
    public AlbumSongsRecord(UUID albumId, UUID songId) {
        super(AlbumSongs.ALBUM_SONGS);

        setAlbumId(albumId);
        setSongId(songId);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised AlbumSongsRecord
     */
    public AlbumSongsRecord(com.example.musicplatform.entity.tables.pojos.AlbumSongs value) {
        super(AlbumSongs.ALBUM_SONGS);

        if (value != null) {
            setAlbumId(value.getAlbumId());
            setSongId(value.getSongId());
            resetChangedOnNotNull();
        }
    }
}
