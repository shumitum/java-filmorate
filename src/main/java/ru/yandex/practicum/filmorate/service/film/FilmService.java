package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {
    Film getFilmById(int filmId);

    void addLike(int filmId, int UserId);

    void deleteLike(int filmId, int UserId);

    List<Film> getHighRatedFilms(Integer count);
}