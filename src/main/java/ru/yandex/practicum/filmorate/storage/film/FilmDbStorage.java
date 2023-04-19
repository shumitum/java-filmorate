package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.entity.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

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
    private final GenreStorage genreStorage;

    @Override
    public void addFilm(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO FILMS (FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATE, MPA_ID) VALUES(?,?,?,?,?,?)",
                    new String[]{"FILM_ID"});
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, film.getReleaseDate());
            ps.setInt(4, film.getDuration());
            ps.setInt(5, film.getRate());
            ps.setLong(6, film.getMpa().getId());
            return ps;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        updateFilmsGenresDb(film);
    }

    @Override
    public void updateFilm(Film film) {
        getFilmById(film.getId());
        jdbcTemplate.update(
                "UPDATE FILMS SET FILM_NAME=?, DESCRIPTION=?, RELEASE_DATE=?, DURATION=?, RATE=?, MPA_ID=? WHERE FILM_ID=?",
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                film.getMpa().getId(),
                film.getId());
        updateFilmsGenresDb(film);
    }

    @Override
    public List<Film> getListOfFilms() {
        List<Film> films = jdbcTemplate.query("SELECT * FROM FILMS JOIN MPA M on FILMS.MPA_ID = M.ID", new FilmMapper());
        films.forEach(this::updateFilmsGenreList);
        films.forEach(this::updateLikesList);
        return films;
    }

    @Override
    public List<Film> getListOfHighRatedFilms(int count) {
        List<Film> films = jdbcTemplate.query("SELECT * FROM FILMS JOIN MPA M on FILMS.MPA_ID = M.ID ORDER BY RATE DESC LIMIT ?",
                new FilmMapper(), count);
        films.forEach(this::updateFilmsGenreList);
        films.forEach(this::updateLikesList);
        return films;
    }

    @Override
    public Film getFilmById(Long filmId) {
        Film film = jdbcTemplate.query("SELECT * FROM FILMS JOIN MPA AS M on FILMS.MPA_ID = M.ID WHERE FILM_ID=?",
                        new FilmMapper(), filmId)
                .stream()
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Фильма с ID=" + filmId + " не существует"));
        updateFilmsGenreList(film);
        updateLikesList(film);
        return film;
    }

    @Override
    public void deleteFilmById(Long filmId) {
        getFilmById(filmId);
        jdbcTemplate.update("DELETE FROM FILMS WHERE FILM_ID=?", filmId);
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

    private void updateFilmsGenresDb(Film film) {
        jdbcTemplate.update("DELETE FROM FILM_GENRES WHERE FILM_ID=?", film.getId());
        if (!film.getGenres().isEmpty()) {
            film.getGenres().forEach((genre ->
                    jdbcTemplate.update("INSERT INTO FILM_GENRES (FILM_ID, GENRE_ID) VALUES(?,?)",
                            film.getId(), genre.getId())));
        }
    }

    private void updateFilmsGenreList(Film film) {
        jdbcTemplate.queryForList("SELECT GENRE_ID FROM FILM_GENRES WHERE FILM_ID=?", Long.class, film.getId())
                .stream()
                .map(genreStorage::getGenreById)
                .forEach(genre -> film.getGenres().add(genre));
    }

    private void updateLikesList(Film film) {
        jdbcTemplate.queryForList("SELECT USER_ID FROM LIKES WHERE FILM_ID=?", Long.class, film.getId())
                .forEach(userId -> film.getLikedUserIds().add(userId));
    }

    private void updateFilmRateDb(Long filmId) {
        Long filmRate = jdbcTemplate.queryForObject("SELECT COUNT(USER_ID) FROM LIKES WHERE FILM_ID=?", Long.class, filmId);
        jdbcTemplate.update("UPDATE FILMS SET RATE=? WHERE FILM_ID=?", filmRate, filmId);
    }
}