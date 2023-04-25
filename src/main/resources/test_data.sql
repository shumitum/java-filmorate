MERGE INTO MPA (id, name) VALUES (1, 'G'),
                                 (2, 'PG'),
                                 (3, 'PG-13'),
                                 (4, 'R'),
                                 (5, 'NC-17');

MERGE INTO GENRES (id, name) VALUES (1, 'Комедия'),
                                    (2, 'Драма'),
                                    (3, 'Мультфильм'),
                                    (4, 'Триллер'),
                                    (5, 'Документальный'),
                                    (6, 'Боевик');

INSERT INTO FILMS (FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATE, MPA_ID)
VALUES ('New film', 'New film about friends', '1999-04-30', 120, 5, 1);

INSERT INTO USERS (EMAIL, LOGIN, USER_NAME, BIRTHDAY)
VALUES ('mail@mail.ru', 'dolore', 'Nick Name', '1946-08-20');