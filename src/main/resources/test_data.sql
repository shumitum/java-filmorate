INSERT INTO MPA (id, name) VALUES (1, 'G');
INSERT INTO MPA (id, name) VALUES (2, 'PG');
INSERT INTO MPA (id, name) VALUES (3, 'PG-13');
INSERT INTO MPA (id, name) VALUES (4, 'R');
INSERT INTO MPA (id, name) VALUES (5, 'NC-17');

INSERT INTO GENRES (id, name) VALUES (1, 'Комедия');
INSERT INTO GENRES (id, name) VALUES (2, 'Драма');
INSERT INTO GENRES (id, name) VALUES (3, 'Мультфильм');
INSERT INTO GENRES (id, name) VALUES (4, 'Триллер');
INSERT INTO GENRES (id, name) VALUES (5, 'Документальный');
INSERT INTO GENRES (id, name) VALUES (6, 'Боевик');

INSERT INTO FILMS (FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATE, MPA_ID)
VALUES ('New film', 'New film about friends', '1999-04-30',  120, 5, 1);

INSERT INTO USERS (EMAIL, LOGIN, USER_NAME, BIRTHDAY)
VALUES ('mail@mail.ru', 'dolore', 'Nick Name', '1946-08-20');