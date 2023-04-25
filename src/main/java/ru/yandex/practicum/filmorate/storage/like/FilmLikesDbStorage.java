package ru.yandex.practicum.filmorate.storage.like;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FilmLikesDbStorage implements FilmLikesStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void updateFilmRateDb(Long filmId) {
        Long filmRate = jdbcTemplate.queryForObject("SELECT COUNT(USER_ID) FROM LIKES WHERE FILM_ID=?", Long.class, filmId);
        jdbcTemplate.update("UPDATE FILMS SET RATE=? WHERE FILM_ID=?", filmRate, filmId);
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        jdbcTemplate.update("INSERT INTO LIKES (FILM_ID, USER_ID) VALUES(?,?)", filmId, userId);
        updateFilmRateDb(filmId);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        jdbcTemplate.update("DELETE FROM LIKES WHERE FILM_ID=? AND USER_ID=?", filmId, userId);
        updateFilmRateDb(filmId);
    }
}
