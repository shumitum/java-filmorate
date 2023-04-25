package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.like.FilmLikesStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final FilmLikesStorage filmLikesStorage;

    public void addFilm(Film film) {
        filmStorage.addFilm(film);
    }

    public void updateFilm(Film film) {
        filmStorage.updateFilm(film);
    }

    public List<Film> getListOfFilms() {
        return filmStorage.getListOfFilms();
    }

    public Film getFilmById(Long filmId) {
        return filmStorage.getFilmById(filmId);
    }

    public void deleteFilmById(Long filmId) {
        filmStorage.deleteFilmById(filmId);
    }

    public void addLike(Long filmId, Long userId) {
        userStorage.getUserById(userId);
        filmStorage.getFilmById(filmId);
        filmLikesStorage.addLike(filmId, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        userStorage.getUserById(userId);
        filmStorage.getFilmById(filmId);
        filmLikesStorage.deleteLike(filmId, userId);
    }

    public List<Film> getHighRatedFilms(Integer count) {
        return filmStorage.getListOfHighRatedFilms(count);
    }
}