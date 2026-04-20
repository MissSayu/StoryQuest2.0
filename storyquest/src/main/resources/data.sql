INSERT INTO users (id, username, password, role, email)
VALUES (1, 'Sayu', '$2a$10$SFASysrHg8VaYvNTy4P/Ne1lPNJ341L6MsGXyOnKk53//o0PnDPHu', 'USER', 'sayuri-chan@hotmail.com'),
       (2, 'Virelight', '$2a$10$.VBgNMjBisrs3JHdQn4xV.KNndSZztiToIlApoQxUe4N2bFN0IM96', 'MOD', 'virelightofficial@hotmail.com'),
       (3, 'Sayuri', '$2a$10$iyb.p9.K0dDa2pUl9FDRLexTk87hMF1JDKe4BUMmuoQiA1AQmRZI6', 'USER', 'sayuri-chan_nyan@hotmail.com'),
       (4, 'Lumine', ' $2a$10$9/O1L9522kTyR0gRWZ1./u5zC5cY8TtSjc7sdf3VMq5sBI6MYF7U2', 'USER', 'lumine@gmail.com'),
       (5, 'Jinae', '$2a$10$Z8pc40O9SAGxWX1EKndXYeBPgFwGL03JyNQvNsfJMP86OClzLvsQ.', 'USER', 'lilbunny@gmail.com'),
       (6, 'Evie', '$2a$10$XNX9ShQUyBcdSNse3kckv.50Dfg/Fj2i88wl87jruKuRPolmijJnW', 'USER', 'evie@gmail.com');

INSERT INTO profiles (id, bio, avatar_url, user_id)
VALUES (1, 'Cheerful dreamer weaving magical tales.', 'http://localhost:8081/avatars/sayu-avatar.png', 1),
       (2, 'Calm strategist and world builder of Eyrndor!', 'http://localhost:8081/avatars/virelight-avatar.png', 2),
       (3, 'Nya nyan nyan~!', 'http://localhost:8081/avatars/default.jpg', 3),
       (4, 'The worlds best flame boi!!!!!', 'http://localhost:8081/avatars/avatar_8_1761489643275_Lumine.png', 4),
       (5, 'Im just a bunny', 'http://localhost:8081/avatars/avatar_9_1761487677038_Dtiys.png', 5),
       (6, 'Everyones favorite Fox~.', 'http://localhost:8081/avatars/avatar_10_1761899713190_wp7543257-chloe-price-wallpapers.png', 6);

INSERT INTO stories (id, tags, title, description, type, genre, cover_image, status, publish_date, user_id)
VALUES
    (1,
     NULL,
     'Whispers of the Forgotten Light',
     'A lost girl named Sayuri begins to uncover her connection to a forgotten magical world called Eryndor.',
     'story',
     'Fantasy',
     '/uploads/covers/1776604663596_1761413360252_starry-night.jpeg',
     'published',
     '2026-04-19 20:00:00',
     1),

    (2,
     NULL,
     'Flame of Eryndor',
     'Lumine, a warrior of flame, is sent on a mission that will change his fate forever.',
     'story',
     'Action',
     '/uploads/covers/dreamy-rainbow-countryside.jpg',
     'published',
     '2026-04-19 21:00:00',
     4),

    (3,
     NULL,
     'Echoes of the Void',
     'Strange distortions begin appearing across Eryndor, hinting at a deeper threat.',
     'story',
     'Mystery',
     '/uploads/covers/1761411808804_sakura.jpg',
     'draft',
     NULL,
     2),

    (4,
     NULL,
     'A Pokemon Story',
     'A small eevee is about to go on a life changing journey',
     'story',
     'Mystery',
     '/uploads/covers/1761840050727_eevee.jpg',
     'published',
     '2026-04-19 23:00:00',
     1);

INSERT INTO episode (id, title, content, episode_order, cover_url, publication_date, story_id)
VALUES
    (1,
     'Prologue',
     'Sayuri always felt like she didn’t belong. Strange dreams, flashes of light, and a voice calling her name...',
     0,
     NULL,
     '2026-04-19 20:05:00',
     1),

    (2,
     'Episode 1: The Silver Oak',
     'The air shimmered as Sayuri touched the ancient tree. Light burst around her, and everything changed.',
     1,
     NULL,
     '2026-04-19 20:10:00',
     1),

    (3,
     'Episode 1: The Silver Oak',
     'The air shimmered as Sayuri touched the ancient tree. Light burst around her, and everything changed.',
     1,
     NULL,
     '2026-04-19 20:10:00',
     1),

    (4,
     'Prologue',
     'Flames danced in Lumine’s hand, but they were unstable… uncontrollable.',
     0,
     NULL,
     '2026-04-19 21:05:00',
     2),

    (5,
     'Episode 1: Trial by Fire',
     'The guild demanded proof of strength. Lumine stepped forward, flames roaring to life.',
     1,
     NULL,
     '2026-04-19 21:10:00',
     2),

    (6,
     'Into the Forest',
     'The morning sun stretched over Greenleaf Town, casting a golden glow across the streets. In a small, cozy house at the edge of town, a young Eevee with bright, curious eyes stretched its paws and yawned. Today was different.',
     1,
     NULL,
     '2026-04-19 23:10:00',
     4);

INSERT INTO comment (id, text_content, created_at, episode_id, user_id)
VALUES

-- Story 1 (Sayuri)
(1,
 'This gave me chills... I love the mystery already!',
 '2026-04-19 20:15:00',
 1,
 2),

(2,
 'Sayuri is so relatable here 😭',
 '2026-04-19 20:16:00',
 1,
 3),

(3,
 'The Silver Oak scene is BEAUTIFUL. I can picture it so clearly.',
 '2026-04-19 20:20:00',
 2,
 4),

(4,
 'Wait… that voice… is it connected to Eryndor?? 👀',
 '2026-04-19 20:22:00',
 2,
 5),

-- Story 2 (Lumine)
(5,
 'Lumine already feels like such a strong character 🔥',
 '2026-04-19 21:15:00',
 4,
 1),

(6,
 'The flames being unstable is such a cool detail',
 '2026-04-19 21:18:00',
 4,
 3),

(7,
 'TRIAL BY FIRE LETS GOOOO 🔥🔥',
 '2026-04-19 21:20:00',
 5,
 2),

-- Story 4 (Eevee story)
(8,
 'THIS IS SO CUTE I CANT 😭💕',
 '2026-04-19 23:15:00',
 6,
 1),

(9,
 'Eevee protagonist?? Instant 10/10.',
 '2026-04-19 23:16:00',
 6,
 4);

SELECT setval('users_id_seq', COALESCE((SELECT MAX(id) FROM users), 1));
SELECT setval('profiles_id_seq', COALESCE((SELECT MAX(id) FROM profiles), 1));
SELECT setval('stories_id_seq', COALESCE((SELECT MAX(id) FROM stories), 1));
SELECT setval('episode_id_seq', COALESCE((SELECT MAX(id) FROM episode), 1));
SELECT setval('comment_id_seq', COALESCE((SELECT MAX(id) FROM comment), 1));
SELECT setval('follow_relations_id_seq', COALESCE((SELECT MAX(id) FROM follow_relations), 1));