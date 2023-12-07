/*
 * This file is generated by jOOQ.
 */
package com.example.musicplatform.entity.tables;


import com.example.musicplatform.entity.Public;
import com.example.musicplatform.entity.tables.records.GetAlbumSongCountRecord;

import java.util.function.Function;

import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Name;
import org.jooq.Records;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GetAlbumSongCount extends TableImpl<GetAlbumSongCountRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.get_album_song_count</code>
     */
    public static final GetAlbumSongCount GET_ALBUM_SONG_COUNT = new GetAlbumSongCount();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GetAlbumSongCountRecord> getRecordType() {
        return GetAlbumSongCountRecord.class;
    }

    /**
     * The column <code>public.get_album_song_count.album_name</code>.
     */
    public final TableField<GetAlbumSongCountRecord, String> ALBUM_NAME = createField(DSL.name("album_name"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.get_album_song_count.song_count</code>.
     */
    public final TableField<GetAlbumSongCountRecord, Long> SONG_COUNT = createField(DSL.name("song_count"), SQLDataType.BIGINT, this, "");

    private GetAlbumSongCount(Name alias, Table<GetAlbumSongCountRecord> aliased) {
        this(alias, aliased, new Field[] {
        });
    }

    private GetAlbumSongCount(Name alias, Table<GetAlbumSongCountRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.function());
    }

    /**
     * Create an aliased <code>public.get_album_song_count</code> table
     * reference
     */
    public GetAlbumSongCount(String alias) {
        this(DSL.name(alias), GET_ALBUM_SONG_COUNT);
    }

    /**
     * Create an aliased <code>public.get_album_song_count</code> table
     * reference
     */
    public GetAlbumSongCount(Name alias) {
        this(alias, GET_ALBUM_SONG_COUNT);
    }

    /**
     * Create a <code>public.get_album_song_count</code> table reference
     */
    public GetAlbumSongCount() {
        this(DSL.name("get_album_song_count"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public GetAlbumSongCount as(String alias) {
        return new GetAlbumSongCount(DSL.name(alias), this, parameters);
    }

    @Override
    public GetAlbumSongCount as(Name alias) {
        return new GetAlbumSongCount(alias, this, parameters);
    }

    @Override
    public GetAlbumSongCount as(Table<?> alias) {
        return new GetAlbumSongCount(alias.getQualifiedName(), this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public GetAlbumSongCount rename(String name) {
        return new GetAlbumSongCount(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public GetAlbumSongCount rename(Name name) {
        return new GetAlbumSongCount(name, null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public GetAlbumSongCount rename(Table<?> name) {
        return new GetAlbumSongCount(name.getQualifiedName(), null, parameters);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * Call this table-valued function
     */
    public GetAlbumSongCount call() {
        GetAlbumSongCount result = new GetAlbumSongCount(DSL.name("get_album_song_count"), null, new Field[] {});

        return aliased() ? result.as(getUnqualifiedName()) : result;
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function2<? super String, ? super Long, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function2<? super String, ? super Long, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
