package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    void addFilm(Film film);

    void updateFilm(Film film);

    List<Film> getListOfFilms();

    List<Film> getListOfHighRatedFilms(int count);

    Film getFilmById(Long filmId);

    void deleteFilmById(Long filmId);

    void addLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);
}