package ru.yandex.practicum.filmorate.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Slf4j
public class FilmRepository implements FilmManager {
    private final Map<Integer, Film> films = new HashMap<>();
    private int filmId;

    @Override
    public void addFilm(Film film) {
        film.setId(++filmId);
        films.put(filmId, film);
    }

    @Override
    public void updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new ValidationException("Фильма с указанным ID не существует");
        }
    }

    @Override
    public List<Film> getListOfFilms() {
        return new ArrayList<>(films.values());
    }
}