DROP FUNCTION IF EXISTS get_artist_ids_by_username(text);
DROP FUNCTION IF EXISTS get_artist_song_info();
DROP FUNCTION IF EXISTS get_album_song_count();
DROP FUNCTION IF EXISTS get_user_liked_songs(text);
DROP FUNCTION IF EXISTS add_artist(VARCHAR(128), VARCHAR(128));
DROP FUNCTION IF EXISTS add_album(VARCHAR(128), VARCHAR(128), VARCHAR(128));
DROP FUNCTION IF EXISTS add_playlist(VARCHAR(128), VARCHAR(128));
DROP FUNCTION IF EXISTS create_new_user(role, VARCHAR(128), VARCHAR(128), VARCHAR(128));

CREATE OR REPLACE FUNCTION get_artist_ids_by_username(username_param text)
    RETURNS TABLE(artist_id uuid) AS
$$
BEGIN
    RETURN QUERY
        SELECT DISTINCT id
        FROM artist
        WHERE user_id = (
            SELECT DISTINCT id
            FROM custom_user
            WHERE username = username_param
        );
END;
$$ LANGUAGE plpgsql;

-- Получить ИД по указанному никнейму
SELECT * FROM get_artist_ids_by_username('lxr4k');
-- Аналогичный запрос
SELECT DISTINCT id FROM artist
WHERE user_id = (
    SELECT DISTINCT id FROM custom_user WHERE username='lxr4k'
);

CREATE OR REPLACE FUNCTION get_artist_song_info()
    RETURNS TABLE(song_title character varying(128), artist_name character varying(128)) AS
$$
BEGIN
    RETURN QUERY
        SELECT song.title, artist.nickname
        FROM artist_songs
                 JOIN artist ON artist.id = artist_songs.artist_id
                 JOIN song ON song.id = artist_songs.song_id;
END;
$$ LANGUAGE plpgsql;

-- Получить информацию о песне
SELECT * FROM get_artist_song_info();
-- Аналогичный запрос
SELECT song.title as song, artist.nickname as artist_name FROM artist_songs
       JOIN artist ON artist.id = artist_songs.artist_id
       JOIN song ON song.id = artist_songs.song_id;

CREATE OR REPLACE FUNCTION get_album_song_count()
    RETURNS TABLE(album_name character varying(128), song_count bigint) AS
$$
BEGIN
    RETURN QUERY
        SELECT album.title, COUNT(song.title)
        FROM album_songs
                 JOIN album ON album.id = album_songs.album_id
                 JOIN song ON song.id = album_songs.song_id
        GROUP BY album.title;
END;
$$ LANGUAGE plpgsql;

-- Получить количество песен в альбоме
SELECT * FROM get_album_song_count();
-- Аналогичный запрос
SELECT album.title as album_name, COUNT(song.title) as songs FROM album_songs
      JOIN album ON album.id = album_songs.album_id
      JOIN song ON song.id = album_songs.song_id
GROUP BY album_name;

CREATE OR REPLACE FUNCTION get_user_liked_songs(email_param text)
    RETURNS TABLE(song_title character varying(128), artist_name character varying(128)) AS
$$
BEGIN
    RETURN QUERY
        SELECT song.title, artist.nickname
        FROM user_liked_songs
                 JOIN song ON song.id = user_liked_songs.song_id
                 JOIN custom_user ON custom_user.id = user_liked_songs.user_id
                 JOIN artist_songs ON song.id = artist_songs.song_id
                 JOIN artist ON artist.id = artist_songs.artist_id
        WHERE custom_user.email = email_param;
END;
$$ LANGUAGE plpgsql;

SELECT * FROM get_user_liked_songs('ya1@po.pa');
-- Аналогичный запрос
SELECT song.title as title, artist.nickname as artist_name FROM user_liked_songs
    JOIN song ON song.id = user_liked_songs.song_id
    JOIN custom_user ON custom_user.id = user_liked_songs.user_id
    JOIN artist_songs ON song.id = artist_songs.song_id
    JOIN artist ON artist.id = artist_songs.artist_id
WHERE custom_user.email = 'ya1@po.pa';



-- Function to Add a New Genre
CREATE OR REPLACE FUNCTION add_genre(genre_name VARCHAR(128), genre_description VARCHAR(128))
    RETURNS UUID AS
$$
DECLARE
    genre_id UUID;
BEGIN
    INSERT INTO genre (id, name, description)
    VALUES (gen_random_uuid(), genre_name, genre_description)
    RETURNING id INTO genre_id;

    RETURN genre_id;
END;
$$ LANGUAGE plpgsql;
-- usage
SELECT add_genre('HipHop', 'A broad genre of popular music that originated as rock and roll in the United States');


-- Function to Add a New Song
CREATE OR REPLACE FUNCTION add_song(genre_name VARCHAR(128), song_title VARCHAR(128))
    RETURNS UUID AS
$$
DECLARE
    genre_id UUID;
    song_id UUID;
BEGIN
    -- Assuming the genre already exists
    SELECT id INTO genre_id FROM genre WHERE name = genre_name;

    INSERT INTO song (id, genre_id, title)
    VALUES (gen_random_uuid(), genre_id, song_title)
    RETURNING id INTO song_id;

    RETURN song_id;
END;
$$ LANGUAGE plpgsql;

SELECT add_song('rock', 'Stairway to Heaven');

-- create new user
CREATE OR REPLACE FUNCTION create_new_user(user_role role, user_username VARCHAR(128), user_password VARCHAR(128), user_email VARCHAR(128))
    RETURNS UUID AS
$$
DECLARE
    new_user_id UUID;
BEGIN
    INSERT INTO custom_user (id, role_id, username, password, email)
    VALUES (gen_random_uuid(), user_role, user_username, user_password, user_email)
    RETURNING id INTO new_user_id;

    RETURN new_user_id;
END;
$$ LANGUAGE plpgsql;
-- usage
SELECT create_new_user('regular', 'kanat', 'password', 'email@mail.ru');

-- create new playlist
CREATE OR REPLACE FUNCTION add_playlist(user_username VARCHAR(128), playlist_title VARCHAR(128))
    RETURNS UUID AS
$$
DECLARE
    user_id UUID;
    playlist_id UUID;
BEGIN
    -- Assuming the user already exists
    SELECT id INTO user_id FROM custom_user WHERE username = user_username;

    INSERT INTO playlist (id, user_creator_id, title)
    VALUES (gen_random_uuid(), user_id, playlist_title)
    RETURNING id INTO playlist_id;

    RETURN playlist_id;
END;
$$ LANGUAGE plpgsql;
-- usage
SELECT add_playlist('kanat', 'Greatest Hill');

-- add new artist
CREATE OR REPLACE FUNCTION add_artist(user_name VARCHAR(128), artist_nickname VARCHAR(128))
    RETURNS UUID AS
$$
DECLARE
    user_id UUID;
    artist_id UUID;
BEGIN
    -- Assuming the user already exists
    SELECT id INTO user_id FROM custom_user WHERE custom_user.username = user_name;

    INSERT INTO artist (id, user_id, nickname)
    VALUES (gen_random_uuid(), user_id, artist_nickname)
    RETURNING id INTO artist_id;

    RETURN artist_id;
END;
$$ LANGUAGE plpgsql;
-- usage
SELECT add_artist('kanat', 'kanatoroev');

-- create new album
CREATE OR REPLACE FUNCTION add_album(artist_nickname VARCHAR(128), genre_name VARCHAR(128), album_title VARCHAR(128))
    RETURNS UUID AS
$$
DECLARE
    artist_id UUID;
    genre_id UUID;
    album_id UUID;
BEGIN
    -- Assuming the artist and genre already exist
    SELECT id INTO artist_id FROM artist WHERE nickname = artist_nickname;
    SELECT id INTO genre_id FROM genre WHERE name = genre_name;

    INSERT INTO album (id, artist_id, genre_id, title)
    VALUES (gen_random_uuid(), artist_id, genre_id, album_title)
    RETURNING id INTO album_id;

    RETURN album_id;
END;
$$ LANGUAGE plpgsql;
-- usage
SELECT add_album('kanatoroev', 'rock', 'Greatest Hits');