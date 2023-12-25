CREATE TYPE role AS ENUM ('admin', 'regular', 'artist');

CREATE TABLE IF NOT EXISTS custom_user (
    id UUID PRIMARY KEY,
    role_id role NOT NULL,
    username VARCHAR(128) NOT NULL,
    password VARCHAR(128) NOT NULL,
    email VARCHAR(128) UNIQUE NOT NULL,
    creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
-- CREATE INDEX ON custom_user (username);
-- DROP INDEX custom_user_username_idx;

CREATE TABLE IF NOT EXISTS genre (
    id UUID PRIMARY KEY,
    name VARCHAR(128) NOT NULL unique,
    description VARCHAR(128) NOT NULL
);
CREATE INDEX ON genre (name);

CREATE TABLE IF NOT EXISTS song (
    id UUID PRIMARY KEY,
    genre_id UUID NOT NULL,
    title VARCHAR(128) NOT NULL,
    creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    likes_count INT NOT NULL DEFAULT 0,
    CONSTRAINT genre_id_fk
        FOREIGN KEY (genre_id)
            REFERENCES genre (id)
);
CREATE INDEX ON song (title);

CREATE TABLE IF NOT EXISTS artist (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    nickname VARCHAR(128) UNIQUE NOT NULL,
    CONSTRAINT user_id_fk
        FOREIGN KEY (user_id)
            REFERENCES custom_user (id),
    likes_count INT NOT NULL DEFAULT 0
);
CREATE INDEX ON artist (nickname);


CREATE TABLE IF NOT EXISTS album (
    id UUID PRIMARY KEY,
    artist_id UUID NOT NULL,
    genre_id UUID NOT NULL,
    title VARCHAR(128) NOT NULL,
    creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    songs_count INT NOT NULL DEFAULT 0,
    likes_count INT NOT NULL DEFAULT 0,
    CONSTRAINT artist_id_fk
        FOREIGN KEY (artist_id)
            REFERENCES artist (id),
    CONSTRAINT genre_id_fk
        FOREIGN KEY (genre_id)
            REFERENCES genre (id)
);
CREATE INDEX ON album (title);

CREATE TABLE IF NOT EXISTS playlist (
    id UUID PRIMARY KEY,
    user_creator_id UUID NOT NULL,
    title VARCHAR(128) NOT NULL,
    creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    likes_count INT NOT NULL DEFAULT 0,
    songs_count INT NOT NULL DEFAULT 0,
    CONSTRAINT user_creator_id_fk
        FOREIGN KEY (user_creator_id)
            REFERENCES custom_user (id)
);
CREATE INDEX ON playlist (title);

CREATE TABLE IF NOT EXISTS playlist_songs (
    playlist_id UUID NOT NULL,
    song_id UUID NOT NULL,
    date_added TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT playlist_id_fk
        FOREIGN KEY (playlist_id)
            REFERENCES playlist (id),
    CONSTRAINT song_id_fk
        FOREIGN KEY (song_id)
            REFERENCES song (id),
    PRIMARY KEY (playlist_id, song_id)
);

CREATE TABLE IF NOT EXISTS user_favourite_artists (
      user_id UUID NOT NULL,
      artist_id UUID NOT NULL,
      CONSTRAINT artist_id_fk
          FOREIGN KEY (artist_id)
              REFERENCES artist (id),
      CONSTRAINT user_id_fk
          FOREIGN KEY (user_id)
              REFERENCES custom_user (id),
      PRIMARY KEY (user_id, artist_id)
);

CREATE TABLE IF NOT EXISTS user_liked_songs (
    user_id UUID NOT NULL,
    song_id UUID NOT NULL,
    CONSTRAINT user_id_fk
        FOREIGN KEY (user_id)
            REFERENCES custom_user (id),
    CONSTRAINT song_id_fk
        FOREIGN KEY (song_id)
            REFERENCES song (id),
    PRIMARY KEY (user_id, song_id)
);

CREATE TABLE IF NOT EXISTS user_listening_history (
    user_id UUID NOT NULL,
    song_id UUID NOT NULL,
    date_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT user_id_fk
        FOREIGN KEY (user_id)
            REFERENCES custom_user (id),
    CONSTRAINT song_id_fk
        FOREIGN KEY (song_id)
            REFERENCES song (id),
    PRIMARY KEY (user_id, song_id)
);

CREATE TABLE IF NOT EXISTS user_favourite_playlists (
    user_id UUID NOT NULL,
    playlist_id UUID NOT NULL,
    CONSTRAINT user_id_fk
        FOREIGN KEY (user_id)
            REFERENCES custom_user (id),
    CONSTRAINT playlist_id_fk
        FOREIGN KEY (playlist_id)
            REFERENCES playlist (id),
    PRIMARY KEY (user_id, playlist_id)
);

CREATE TABLE IF NOT EXISTS user_favourite_albums (
    user_id UUID NOT NULL,
    album_id UUID NOT NULL,
    CONSTRAINT user_id_fk
        FOREIGN KEY (user_id)
            REFERENCES custom_user (id),
    CONSTRAINT album_id_fk
        FOREIGN KEY (album_id)
            REFERENCES album (id),
    PRIMARY KEY (user_id, album_id)
);


CREATE TABLE IF NOT EXISTS album_songs (
    album_id UUID NOT NULL,
    song_id UUID NOT NULL,
    CONSTRAINT album_id_fk
        FOREIGN KEY (album_id)
            REFERENCES album (id),
    CONSTRAINT song_id_fk
        FOREIGN KEY (song_id)
            REFERENCES song (id),
    PRIMARY KEY (album_id, song_id)
);

CREATE TABLE IF NOT EXISTS artist_songs (
    artist_id UUID NOT NULL,
    song_id UUID NOT NULL,
    CONSTRAINT artist_id_fk
        FOREIGN KEY (artist_id)
            REFERENCES artist (id),
    CONSTRAINT song_id_fk
        FOREIGN KEY (song_id)
            REFERENCES song (id),
    PRIMARY KEY (artist_id, song_id)
);

CREATE TABLE IF NOT EXISTS JWT_TOKEN (
    ID UUID NOT NULL,
    user_id UUID NOT NULL,
    expired bool NOT NULL DEFAULT FALSE,
    revoked bool NOT NULL DEFAULT FALSE,
    token varchar NOT NULL,
    CONSTRAINT user_id_fk
        FOREIGN KEY (user_id)
            REFERENCES custom_user (id)
);