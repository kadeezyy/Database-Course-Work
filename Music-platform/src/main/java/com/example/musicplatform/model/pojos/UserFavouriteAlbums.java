/*
 * This file is generated by jOOQ.
 */
package com.example.musicplatform.model.pojos;


import java.io.Serializable;
import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserFavouriteAlbums implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID userId;
    private UUID albumId;

    public UserFavouriteAlbums() {}

    public UserFavouriteAlbums(UserFavouriteAlbums value) {
        this.userId = value.userId;
        this.albumId = value.albumId;
    }

    public UserFavouriteAlbums(
        UUID userId,
        UUID albumId
    ) {
        this.userId = userId;
        this.albumId = albumId;
    }

    /**
     * Getter for <code>public.user_favourite_albums.user_id</code>.
     */
    public UUID getUserId() {
        return this.userId;
    }

    /**
     * Setter for <code>public.user_favourite_albums.user_id</code>.
     */
    public UserFavouriteAlbums setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Getter for <code>public.user_favourite_albums.album_id</code>.
     */
    public UUID getAlbumId() {
        return this.albumId;
    }

    /**
     * Setter for <code>public.user_favourite_albums.album_id</code>.
     */
    public UserFavouriteAlbums setAlbumId(UUID albumId) {
        this.albumId = albumId;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final UserFavouriteAlbums other = (UserFavouriteAlbums) obj;
        if (this.userId == null) {
            if (other.userId != null)
                return false;
        }
        else if (!this.userId.equals(other.userId))
            return false;
        if (this.albumId == null) {
            if (other.albumId != null)
                return false;
        }
        else if (!this.albumId.equals(other.albumId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.userId == null) ? 0 : this.userId.hashCode());
        result = prime * result + ((this.albumId == null) ? 0 : this.albumId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserFavouriteAlbums (");

        sb.append(userId);
        sb.append(", ").append(albumId);

        sb.append(")");
        return sb.toString();
    }
}