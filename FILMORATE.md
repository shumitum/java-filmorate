classDiagram
direction BT
class FILMS {
   character varying FILM_NAME
   character varying(200) DESCRIPTION
   date RELEASE_DATE
   integer DURATION
   integer RATE
   bigint MPA_ID
   bigint FILM_ID
}
class FILM_GENRES {
   bigint FILM_ID
   bigint GENRE_ID
}
class FRIENDS {
   bigint USER_ID
   bigint FRIEND_ID
}
class GENRES {
   character varying NAME
   bigint ID
}
class LIKES {
   bigint FILM_ID
   bigint USER_ID
}
class MPA {
   character varying(5) NAME
   bigint ID
}
class USERS {
   character varying EMAIL
   character varying(20) LOGIN
   character varying USER_NAME
   date BIRTHDAY
   bigint USER_ID
}

FILMS  -->  MPA : MPA_ID:ID
FILM_GENRES  -->  FILMS : FILM_ID
FILM_GENRES  -->  GENRES : GENRE_ID:ID
FRIENDS  -->  USERS : FRIEND_ID:USER_ID
FRIENDS  -->  USERS : USER_ID
LIKES  -->  FILMS : FILM_ID
LIKES  -->  USERS : USER_ID
