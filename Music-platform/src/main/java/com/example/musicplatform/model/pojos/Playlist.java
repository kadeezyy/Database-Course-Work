/*
 * This file is generated by jOOQ.
 */
package com.example.musicplatform.model.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Playlist implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private UUID userCreatorId;
    private String title;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdated;
    private Integer likesCount;
    private Integer songsCount;

    public Playlist() {}

    public Playlist(Playlist value) {
        this.id = value.id;
        this.userCreatorId = value.userCreatorId;
        this.title = value.title;
        this.creationDate = value.creationDate;
        this.lastUpdated = value.lastUpdated;
        this.likesCount = value.likesCount;
        this.songsCount = value.songsCount;
    }

    public Playlist(
        UUID id,
        UUID userCreatorId,
        String title,
        LocalDateTime creationDate,
        LocalDateTime lastUpdated,
        Integer likesCount,
        Integer songsCount
    ) {
        this.id = id;
        this.userCreatorId = userCreatorId;
        this.title = title;
        this.creationDate = creationDate;
        this.lastUpdated = lastUpdated;
        this.likesCount = likesCount;
        this.songsCount = songsCount;
    }

    /**
     * Getter for <code>public.playlist.id</code>.
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Setter for <code>public.playlist.id</code>.
     */
    public Playlist setId(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * Getter for <code>public.playlist.user_creator_id</code>.
     */
    public UUID getUserCreatorId() {
        return this.userCreatorId;
    }

    /**
     * Setter for <code>public.playlist.user_creator_id</code>.
     */
    public Playlist setUserCreatorId(UUID userCreatorId) {
        this.userCreatorId = userCreatorId;
        return this;
    }

    /**
     * Getter for <code>public.playlist.title</code>.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Setter for <code>public.playlist.title</code>.
     */
    public Playlist setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Getter for <code>public.playlist.creation_date</code>.
     */
    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    /**
     * Setter for <code>public.playlist.creation_date</code>.
     */
    public Playlist setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * Getter for <code>public.playlist.last_updated</code>.
     */
    public LocalDateTime getLastUpdated() {
        return this.lastUpdated;
    }

    /**
     * Setter for <code>public.playlist.last_updated</code>.
     */
    public Playlist setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    /**
     * Getter for <code>public.playlist.likes_count</code>.
     */
    public Integer getLikesCount() {
        return this.likesCount;
    }

    /**
     * Setter for <code>public.playlist.likes_count</code>.
     */
    public Playlist setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
        return this;
    }

    /**
     * Getter for <code>public.playlist.songs_count</code>.
     */
    public Integer getSongsCount() {
        return this.songsCount;
    }

    /**
     * Setter for <code>public.playlist.songs_count</code>.
     */
    public Playlist setSongsCount(Integer songsCount) {
        this.songsCount = songsCount;
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
        final Playlist other = (Playlist) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.userCreatorId == null) {
            if (other.userCreatorId != null)
                return false;
        }
        else if (!this.userCreatorId.equals(other.userCreatorId))
            return false;
        if (this.title == null) {
            if (other.title != null)
                return false;
        }
        else if (!this.title.equals(other.title))
            return false;
        if (this.creationDate == null) {
            if (other.creationDate != null)
                return false;
        }
        else if (!this.creationDate.equals(other.creationDate))
            return false;
        if (this.lastUpdated == null) {
            if (other.lastUpdated != null)
                return false;
        }
        else if (!this.lastUpdated.equals(other.lastUpdated))
            return false;
        if (this.likesCount == null) {
            if (other.likesCount != null)
                return false;
        }
        else if (!this.likesCount.equals(other.likesCount))
            return false;
        if (this.songsCount == null) {
            if (other.songsCount != null)
                return false;
        }
        else if (!this.songsCount.equals(other.songsCount))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.userCreatorId == null) ? 0 : this.userCreatorId.hashCode());
        result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
        result = prime * result + ((this.creationDate == null) ? 0 : this.creationDate.hashCode());
        result = prime * result + ((this.lastUpdated == null) ? 0 : this.lastUpdated.hashCode());
        result = prime * result + ((this.likesCount == null) ? 0 : this.likesCount.hashCode());
        result = prime * result + ((this.songsCount == null) ? 0 : this.songsCount.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Playlist (");

        sb.append(id);
        sb.append(", ").append(userCreatorId);
        sb.append(", ").append(title);
        sb.append(", ").append(creationDate);
        sb.append(", ").append(lastUpdated);
        sb.append(", ").append(likesCount);
        sb.append(", ").append(songsCount);

        sb.append(")");
        return sb.toString();
    }
}
