DELETE FROM vote;
DELETE FROM dish;
DELETE FROM restaurant;

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

INSERT INTO restaurant (name)
VALUES ('Tkemali'),
       ('Megobari');

INSERT INTO dish (restaurant_id, dish_date, name, price)
VALUES (100002, '2020-08-20', 'Chicken, lamb or veal kebab', 620),
       (100002, '2020-08-20', 'Ojakhuri (roasted veal with potatoes served with tkemali sauce)', 650),
       (100002, '2020-08-20', 'Khinkali with lamb', 320),
       (100003, '2020-08-20', 'Ajapsandali', 450),
       (100003, '2020-08-20', 'Chicken Satsivi', 470),
       (100003, '2020-08-20', 'Eggplant roll with walnut and spices', 460);

INSERT INTO vote (vote_date, user_id, restaurant_id)
VALUES ('2020-08-20', 100000, 100003),
       ('2020-08-20', 100001, 100003)