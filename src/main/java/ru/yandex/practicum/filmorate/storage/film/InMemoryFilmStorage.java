package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import java.util.*;
import java.util.stream.Collectors;


@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage, FilmService {
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
            throw new NotFoundException("Фильма с указанным ID не существует");
        }
    }

    @Override
    public List<Film> getListOfFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilmById(int filmId) {
        if (films.containsKey(filmId)) {
            return films.get(filmId);
        } else {
            throw new NotFoundException("Фильма с указанным ID не существует");
        }
    }

    @Override
    public void addLike(int filmId, int UserId) {
        films.get(filmId).getLikedUserIds().add(UserId);
    }

    @Override
    public void deleteLike(int filmId, int UserId) {
        films.get(filmId).getLikedUserIds().remove(UserId);
    }

    @Override
    public List<Film> getHighRatedFilms(Integer count) {
        if (!films.isEmpty()) {
            return films.values().stream()
                    .sorted(Comparator.comparingInt(f -> f.getLikedUserIds().size() * -1))
                    .limit(count) //.limit(count == null ? 10 : count)
                    .collect(Collectors.toList());
        } else {
            throw new NotFoundException("Список фильмов пуст");
        }
    }
}