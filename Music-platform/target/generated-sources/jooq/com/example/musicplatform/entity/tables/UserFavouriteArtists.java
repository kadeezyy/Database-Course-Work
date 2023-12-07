/*
 * This file is generated by jOOQ.
 */
package com.example.musicplatform.entity.tables;


import com.example.musicplatform.entity.Keys;
import com.example.musicplatform.entity.Public;
import com.example.musicplatform.entity.tables.records.UserFavouriteArtistsRecord;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function2;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserFavouriteArtists extends TableImpl<UserFavouriteArtistsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.user_favourite_artists</code>
     */
    public static final UserFavouriteArtists USER_FAVOURITE_ARTISTS = new UserFavouriteArtists();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserFavouriteArtistsRecord> getRecordType() {
        return UserFavouriteArtistsRecord.class;
    }

    /**
     * The column <code>public.user_favourite_artists.user_id</code>.
     */
    public final TableField<UserFavouriteArtistsRecord, UUID> USER_ID = createField(DSL.name("user_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.user_favourite_artists.artist_id</code>.
     */
    public final TableField<UserFavouriteArtistsRecord, UUID> ARTIST_ID = createField(DSL.name("artist_id"), SQLDataType.UUID.nullable(false), this, "");

    private UserFavouriteArtists(Name alias, Table<UserFavouriteArtistsRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserFavouriteArtists(Name alias, Table<UserFavouriteArtistsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.user_favourite_artists</code> table
     * reference
     */
    public UserFavouriteArtists(String alias) {
        this(DSL.name(alias), USER_FAVOURITE_ARTISTS);
    }

    /**
     * Create an aliased <code>public.user_favourite_artists</code> table
     * reference
     */
    public UserFavouriteArtists(Name alias) {
        this(alias, USER_FAVOURITE_ARTISTS);
    }

    /**
     * Create a <code>public.user_favourite_artists</code> table reference
     */
    public UserFavouriteArtists() {
        this(DSL.name("user_favourite_artists"), null);
    }

    public <O extends Record> UserFavouriteArtists(Table<O> child, ForeignKey<O, UserFavouriteArtistsRecord> key) {
        super(child, key, USER_FAVOURITE_ARTISTS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<UserFavouriteArtistsRecord> getPrimaryKey() {
        return Keys.USER_FAVOURITE_ARTISTS_PKEY;
    }

    @Override
    public List<ForeignKey<UserFavouriteArtistsRecord, ?>> getReferences() {
        return Arrays.asList(Keys.USER_FAVOURITE_ARTISTS__USER_ID_FK, Keys.USER_FAVOURITE_ARTISTS__ARTIST_ID_FK);
    }

    private transient CustomUser _customUser;
    private transient Artist _artist;

    /**
     * Get the implicit join path to the <code>public.custom_user</code> table.
     */
    public CustomUser customUser() {
        if (_customUser == null)
            _customUser = new CustomUser(this, Keys.USER_FAVOURITE_ARTISTS__USER_ID_FK);

        return _customUser;
    }

    /**
     * Get the implicit join path to the <code>public.artist</code> table.
     */
    public Artist artist() {
        if (_artist == null)
            _artist = new Artist(this, Keys.USER_FAVOURITE_ARTISTS__ARTIST_ID_FK);

        return _artist;
    }

    @Override
    public UserFavouriteArtists as(String alias) {
        return new UserFavouriteArtists(DSL.name(alias), this);
    }

    @Override
    public UserFavouriteArtists as(Name alias) {
        return new UserFavouriteArtists(alias, this);
    }

    @Override
    public UserFavouriteArtists as(Table<?> alias) {
        return new UserFavouriteArtists(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserFavouriteArtists rename(String name) {
        return new UserFavouriteArtists(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserFavouriteArtists rename(Name name) {
        return new UserFavouriteArtists(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserFavouriteArtists rename(Table<?> name) {
        return new UserFavouriteArtists(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<UUID, UUID> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function2<? super UUID, ? super UUID, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function2<? super UUID, ? super UUID, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
