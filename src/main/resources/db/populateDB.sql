DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'); -- 0

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin'); -- 1

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2015-05-18 09:34:00', 'Завтрак', 600); -- 2

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2015-05-18 12:34:00', 'Обед', 520); -- 3

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2015-05-18 19:34:00', 'Ужин', 820); -- 4

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);
