-- Update "songs_count" field in "playlist"
CREATE OR REPLACE FUNCTION update_playlist_songs_count_func()
    RETURNS TRIGGER AS
$$
BEGIN
    UPDATE "playlist"
    SET songs_count = (SELECT COUNT(*) FROM playlist_songs WHERE playlist_id = NEW.playlist_id)
    WHERE id = NEW.playlist_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER update_playlist_songs_count
    AFTER INSERT OR DELETE
    ON "playlist_songs"
    FOR EACH ROW
EXECUTE FUNCTION update_playlist_songs_count_func();

-- Update "songs_count" field in "album"
CREATE OR REPLACE FUNCTION update_album_songs_count_func()
    RETURNS TRIGGER AS
$$
BEGIN
    UPDATE "album"
    SET songs_count = (SELECT COUNT(*) FROM album_songs WHERE album_id = NEW.album_id)
    WHERE id = NEW.album_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER update_album_songs_count
    AFTER INSERT OR DELETE
    ON "album_songs"
    FOR EACH ROW
EXECUTE FUNCTION update_album_songs_count_func();

-- Update "last_updated" field in "playlist"
CREATE OR REPLACE FUNCTION update_playlist_last_updated_field_func()
    RETURNS TRIGGER AS
$$
BEGIN
    UPDATE playlist
    SET last_updated = CURRENT_TIMESTAMP
    WHERE id = NEW.playlist_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER update_playlist_last_updated_field
    AFTER INSERT OR DELETE OR UPDATE -- Updated for all changes
    ON "playlist_songs"
    FOR EACH ROW
EXECUTE FUNCTION update_playlist_last_updated_field_func();

--update "likes_count" field on "artist"
CREATE OR REPLACE FUNCTION update_artists_likes_count_func()
    RETURNS TRIGGER AS
$$
BEGIN
    UPDATE "artist"
    SET likes_count = (SELECT COUNT(*) FROM user_favourite_artists WHERE artist_id = NEW.artist_id)
    WHERE id = NEW.artist_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER update_artists_likes_count
    AFTER INSERT OR DELETE
    ON "user_favourite_artists"
    FOR EACH ROW
EXECUTE FUNCTION update_artists_likes_count_func();

--update "likes_count" field on "song"
CREATE OR REPLACE FUNCTION update_song_like_count_func()
    RETURNS TRIGGER AS
$$
BEGIN
    UPDATE "song"
    SET likes_count = (SELECT COUNT(*) FROM user_liked_songs WHERE song_id = NEW.song_id)
    WHERE id = NEW.song_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER update_song_like_count
    AFTER INSERT OR DELETE
    ON "user_liked_songs"
    FOR EACH ROW
EXECUTE FUNCTION update_song_like_count_func();

-- update "likes_count" field on "playlist"
CREATE OR REPLACE FUNCTION update_playlist_like_count_func()
    RETURNS TRIGGER AS
$$
BEGIN
    UPDATE "playlist"
    SET likes_count = (SELECT COUNT(*) FROM user_favourite_playlists WHERE playlist_id = NEW.playlist_id)
    WHERE id = NEW.playlist_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER update_playlist_like_count
    AFTER INSERT OR DELETE
    ON "user_favourite_playlists"
    FOR EACH ROW
EXECUTE FUNCTION update_playlist_like_count_func();

-- update "likes_count" field on "album"
CREATE OR REPLACE FUNCTION update_album_like_count_func()
    RETURNS TRIGGER AS
$$
BEGIN
    UPDATE "album"
    SET likes_count = (SELECT COUNT(*) FROM user_favourite_albums WHERE album_id = NEW.album_id)
    WHERE id = NEW.album_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER update_album_like_count
    AFTER INSERT OR DELETE
    ON "user_favourite_albums"
    FOR EACH ROW
EXECUTE FUNCTION update_album_like_count_func();

-- "date_time" field in "user_listening_history" table is always set to the current timestamp
CREATE OR REPLACE FUNCTION update_listening_history_timestamp()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.date_time := CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER update_listening_history_timestamp_trigger
    AFTER INSERT
    ON user_listening_history
    FOR EACH ROW
EXECUTE FUNCTION update_listening_history_timestamp();

--Update field role in custom_user
CREATE OR REPLACE FUNCTION update_artist_role()
    RETURNS TRIGGER AS
$$
BEGIN
    UPDATE custom_user
    SET role_id = 'artist'
    WHERE id = NEW.user_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER update_artist_role
    AFTER INSERT
    ON artist
    FOR EACH ROW
EXECUTE FUNCTION update_artist_role();