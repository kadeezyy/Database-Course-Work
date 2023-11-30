SELECT * FROM get_artist_ids_by_username('lxr4k');
-- Аналогичный запрос
SELECT DISTINCT id
FROM artist
WHERE user_id = (SELECT DISTINCT id
                 FROM custom_user
                 WHERE username = 'lxr4k');

-- Получить информацию о песне
SELECT *
FROM get_artist_song_info();

-- Получить количество песен в альбоме
SELECT *
FROM get_album_song_count();
-- Аналогичный запрос
SELECT album.title as album_name, COUNT(song.title) as songs
FROM album_songs
         JOIN album ON album.id = album_songs.album_id
         JOIN song ON song.id = album_songs.song_id
GROUP BY album_name;

SELECT *
FROM get_user_liked_songs('ya1@po.pa');
-- Аналогичный запрос
SELECT song.title as title, artist.nickname as artist_name
FROM user_liked_songs
         JOIN song ON song.id = user_liked_songs.song_id
         JOIN custom_user ON custom_user.id = user_liked_songs.user_id
         JOIN artist_songs ON song.id = artist_songs.song_id
         JOIN artist ON artist.id = artist_songs.artist_id
WHERE custom_user.email = 'ya1@po.pa';

-- usage
SELECT add_genre('HipHop', 'A broad genre of popular music that originated as rock and roll in the United States');

SELECT add_song('rock', 'Stairway to Heaven');

-- usage
SELECT create_new_user('regular', 'kanat', 'password', 'email@mail.ru');

-- usage
SELECT add_playlist('kanat', 'Greatest Hill');

-- usage
SELECT add_artist('kanat', 'kanatoroev');

-- usage
SELECT add_album('kanatoroev', 'rock', 'Greatest Hits');