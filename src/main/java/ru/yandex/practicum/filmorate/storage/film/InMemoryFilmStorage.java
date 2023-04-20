package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private Long filmId = 0L;

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
            throw new NoSuchElementException("Невозможно обновить. Фильма с ID=" + film.getId() + " не существует");
        }
    }

    @Override
    public List<Film> getListOfFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public List<Film> getListOfHighRatedFilms(int count) {
        return getListOfFilms().stream()
                .sorted(Comparator.comparingInt(f -> f.getRate() * -1))
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public Film getFilmById(Long filmId) {
        return films.values().stream()
                .filter(film -> film.getId() == filmId)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Фильма с ID=" + filmId + " не существует"));
    }

    @Override
    public void deleteFilmById(Long filmId) {
        if (films.containsKey(filmId)) {
            films.remove(filmId);
        } else {
            throw new NoSuchElementException("Невозможно удалить. Фильма с ID=" + filmId + " не существует");
        }
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        getFilmById(filmId).getLikedUserIds().add(userId);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        getFilmById(filmId).getLikedUserIds().remove(userId);
    }
}