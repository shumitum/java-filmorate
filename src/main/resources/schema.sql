DROP TABLE MPA CASCADE;
DROP TABLE GENRES CASCADE;
DROP TABLE USERS CASCADE;
DROP TABLE FILMS CASCADE;
DROP TABLE FILM_GENRES CASCADE;
DROP TABLE FRIENDS CASCADE;
DROP TABLE LIKES CASCADE;

create table IF NOT EXISTS MPA
(
	ID   INTEGER              not null,
	NAME CHARACTER VARYING(5) not null,
	constraint "MPA_pk"
		primary key (ID)
);

create table IF NOT EXISTS GENRES
(
	ID   INTEGER           not null,
	NAME CHARACTER VARYING not null,
	constraint "GENRES_pk"
		primary key (ID)
);

create table IF NOT EXISTS USERS
(
	USER_ID   INTEGER               not null,
	EMAIL     CHARACTER VARYING     not null,
	LOGIN     CHARACTER VARYING(20) not null,
	USER_NAME CHARACTER VARYING     not null,
	BIRTHDAY  DATE                  not null,
	constraint "USERS_pk"
		primary key (USER_ID)
);

create table IF NOT EXISTS FILMS
(
	FILM_ID      INTEGER auto_increment,
	FILM_NAME    CHARACTER VARYING    not null,
	DESCRIPTION  CHARACTER VARYING(200),
	RELEASE_DATE DATE                 not null,
	DURATION     INTEGER              not null,
	RATE		 INTEGER,
	MPA_ID       INTEGER              not null,
	constraint "FILMS_pk"
		primary key (FILM_ID),
	constraint "FILMS_MPA_ID_fk"
		foreign key (MPA_ID) references MPA
);

create table IF NOT EXISTS FILM_GENRES
(
	FILM_ID  INTEGER not null,
	GENRE_ID INTEGER not null,
	constraint "FILM_GENRES_pk"
		primary key (FILM_ID, GENRE_ID),
	constraint "FILM_GENRES_FILMS_FILM_ID_fk"
		foreign key (FILM_ID) references FILMS,
	constraint "FILM_GENRES_GENRES_GENRE_ID_fk"
		foreign key (GENRE_ID) references GENRES
);

create table IF NOT EXISTS FRIENDS
(
	USER_ID   INTEGER not null,
	FRIEND_ID INTEGER not null,
	IS_FRIEND BOOLEAN not null,
	constraint "FRIENDS_pk"
		primary key (USER_ID, FRIEND_ID),
	constraint "FRIENDS_USERS_USER_ID_fk"
		foreign key (USER_ID) references USERS,
	constraint "FRIENDS_USERS_USER_ID_fk2"
		foreign key (FRIEND_ID) references USERS
);

create table IF NOT EXISTS LIKES
(
	FILM_ID INTEGER not null,
	USER_ID INTEGER not null,
	constraint "LIKES_pk"
		primary key (USER_ID, FILM_ID),
	constraint "LIKES_FILMS_FILM_ID_fk"
		foreign key (FILM_ID) references FILMS,
	constraint "LIKES_USERS_USER_ID_fk"
		foreign key (USER_ID) references USERS
);