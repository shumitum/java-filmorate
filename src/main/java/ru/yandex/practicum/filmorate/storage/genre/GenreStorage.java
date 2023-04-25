package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {
    Genre getGenreById(Long genreId);

    List<Genre> getListOfGenres();

    void updateFilmsGenresDb(Film film);

    void updateFilmsGenreList(Film film);
}