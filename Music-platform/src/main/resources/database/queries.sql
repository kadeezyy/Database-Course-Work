SELECT DISTINCT id FROM artist
WHERE user_id = (
    SELECT DISTINCT id FROM custom_user WHERE username='lxr4k'
);

SELECT song.title as song, artist.nickname as artist_name FROM artist_songs
       JOIN artist ON artist.id = artist_songs.artist_id
       JOIN song ON song.id = artist_songs.song_id;

SELECT album.title as album_name, COUNT(song.title) as songs FROM album_songs
      JOIN album ON album.id = album_songs.album_id
      JOIN song ON song.id = album_songs.song_id
GROUP BY album_name;

SELECT song.title as title, artist.nickname as artist_name FROM user_liked_songs
    JOIN song ON song.id = user_liked_songs.song_id
    JOIN custom_user ON custom_user.id = user_liked_songs.user_id
    JOIN artist_songs ON song.id = artist_songs.song_id
    JOIN artist ON artist.id = artist_songs.artist_id
WHERE custom_user.email = 'ya1@po.pa';
