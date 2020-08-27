DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM restaurants;

DELETE FROM user_roles;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@gmail.com', '{noop}user'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001);

INSERT INTO restaurants (name)
VALUES ('Tkemali'),
       ('Megobari');

INSERT INTO dishes (restaurant_id, date, name, price)
VALUES (100002, '2020-08-20 00:00:00', 'Chicken, lamb or veal kebab', 620),
       (100002, '2020-08-20 00:00:00', 'Ojakhuri (roasted veal with potatoes served with tkemali sauce)', 650),
       (100002, '2020-08-20 00:00:00', 'Khinkali with lamb', 320),
       (100003, '2020-08-20 00:00:00', 'Ajapsandali', 450),
       (100003, '2020-08-20 00:00:00', 'Chicken Satsivi', 470),
       (100003, '2020-08-20 00:00:00', 'Eggplant roll with walnut and spices', 460);

INSERT INTO votes (date, user_id, restaurant_id)
VALUES ('2020-08-20 00:00:00', 100000, 100003),
       ('2020-08-20 00:00:00', 100001, 100003)