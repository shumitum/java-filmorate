package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Repository
@Primary
@RequiredArgsConstructor
@Slf4j
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFilm(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO FILMS (FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID) VALUES(?,?,?,?,?)",
                    new String[]{"FILM_ID"});
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, film.getReleaseDate());
            ps.setLong(4, film.getDuration());
            ps.setLong(5, film.getMpa().getId());
            return ps;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public void updateFilm(Film film) {
        getFilmById(film.getId());
        jdbcTemplate.update("UPDATE FILMS SET FILM_NAME=?, DESCRIPTION=?, RELEASE_DATE=?, DURATION=?, RATE=?, MPA_ID=? WHERE FILM_ID=?",
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                film.getMpa().getId(),
                film.getId());
    }

    @Override
    public List<Film> getListOfFilms() {
        return jdbcTemplate.query("SELECT * FROM FILMS JOIN MPA AS M on FILMS.MPA_ID = M.ID", new FilmMapper());
    }

    @Override
    public Film getFilmById(Long filmId) {
        return jdbcTemplate.query("SELECT * FROM FILMS JOIN MPA AS M on FILMS.MPA_ID = M.ID WHERE FILM_ID=?", new FilmMapper(), filmId)
                .stream()
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Фильма с ID=" + filmId + " не существует"));
    }

    @Override
    public void deleteFilmById(Long filmId) {
        getFilmById(filmId);
        jdbcTemplate.update("DELETE FROM FILMS WHERE FILM_ID=?", filmId);
    }

    private boolean checkFilmExistenceById(Film film) {
        //jdbcTemplate.query("SELECT FILM_ID FROM FILMS WHERE=?", film.getId(), new Long().);
        return false;
    }
}
