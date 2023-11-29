INSERT INTO genre
VALUES (gen_random_uuid(), 'rock', '');
INSERT INTO genre
VALUES (gen_random_uuid(), 'rap', '');
INSERT INTO genre
VALUES (gen_random_uuid(), 'hyperpop', '');
INSERT INTO genre
VALUES (gen_random_uuid(), 'remix', '');
INSERT INTO genre
VALUES (gen_random_uuid(), 'pop', '');

INSERT INTO custom_user
VALUES (gen_random_uuid(), 'regular', 'lxr4k', 'pass_hash', 'ya@po.pa', DEFAULT);
INSERT INTO custom_user
VALUES (gen_random_uuid(), 'regular', 'xxxmpty', 'pass_hash', 'ya1@po.pa', DEFAULT);

INSERT INTO artist
VALUES (gen_random_uuid(), (SELECT DISTINCT id FROM custom_user WHERE username = 'lxr4k'),
        'drain shawty',
        DEFAULT);

INSERT INTO song
VALUES (gen_random_uuid(), (SELECT DISTINCT id FROM genre WHERE name = 'hyperpop'),
        'void in my heart',
        DEFAULT);
INSERT INTO song
VALUES (gen_random_uuid(), (SELECT DISTINCT id FROM genre WHERE name = 'hyperpop'),
        'где я',
        DEFAULT);

INSERT INTO artist_songs
VALUES ((SELECT DISTINCT id FROM artist WHERE nickname = 'drain shawty'),
        (SELECT DISTINCT id FROM song WHERE title = 'где я'));

INSERT INTO artist_songs
VALUES ((SELECT DISTINCT id FROM artist WHERE nickname = 'drain shawty'),
        (SELECT DISTINCT id FROM song WHERE title = 'void in my heart'));

INSERT INTO album
VALUES (gen_random_uuid(),
        (SELECT DISTINCT id FROM artist WHERE user_id = (SELECT DISTINCT id FROM custom_user WHERE username = 'lxr4k')),
        (SELECT DISTINCT id FROM genre WHERE name = 'hyperpop'),
        'lobotomy');

INSERT INTO album_songs
VALUES ((SELECT DISTINCT id FROM album WHERE title = 'lobotomy'),
        (SELECT DISTINCT id FROM song WHERE title = 'где я'));

INSERT INTO album_songs
VALUES ((SELECT DISTINCT id FROM album WHERE title = 'lobotomy'),
        (SELECT DISTINCT id FROM song WHERE title = 'void in my heart'));

INSERT INTO playlist
VALUES (gen_random_uuid(),
        (SELECT DISTINCT id FROM custom_user WHERE username = 'lxr4k'),
        '1 hour dead inside tracks');

INSERT INTO playlist_songs
VALUES ((SELECT DISTINCT id FROM playlist WHERE title = '1 hour dead inside tracks'),
        (SELECT DISTINCT id FROM song WHERE title = 'где я'));

INSERT INTO playlist_songs
VALUES ((SELECT DISTINCT id FROM playlist WHERE title = '1 hour dead inside tracks'),
        (SELECT DISTINCT id FROM song WHERE title = 'void in my heart'));

INSERT INTO user_favourite_albums
VALUES ((SELECT DISTINCT id FROM custom_user WHERE username = 'xxxmpty'),
        ((SELECT DISTINCT id FROM album WHERE title = 'lobotomy')));

INSERT INTO user_favourite_playlists
VALUES ((SELECT DISTINCT id FROM custom_user WHERE username = 'lxr4k'),
        ((SELECT DISTINCT id FROM playlist WHERE title = '1 hour dead inside tracks')));

INSERT INTO user_liked_songs
VALUES ((SELECT DISTINCT id FROM custom_user WHERE username = 'xxxmpty'),
        (SELECT DISTINCT id FROM song WHERE title = 'где я'));

INSERT INTO user_favourite_artists
VALUES ((SELECT DISTINCT id FROM custom_user WHERE username = 'xxxmpty'),
        (SELECT DISTINCT id FROM artist WHERE nickname = 'drain shawty'));