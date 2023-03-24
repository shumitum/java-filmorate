package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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
            throw new NoSuchElementException("Фильма с ID=" + film.getId() + " не существует");
        }
    }

    @Override
    public List<Film> getListOfFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilmById(int filmId) {
        return films.values().stream()
                .filter(film -> film.getId() == filmId)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Фильма с ID=" + filmId + " не существует"));
    }

    @Override
    public void addLike(int filmId, int userId) {
        if (!films.containsKey(filmId)) {
            throw new NoSuchElementException("Фильма с ID=" + filmId + " не существует");
        } else {
            films.get(filmId).getLikedUserIds().add(userId);
            log.info("Пользователь с ID=" + userId + " поставил лайк фильму с ID=" + filmId);
        }
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        if (!films.containsKey(filmId)) {
            throw new NoSuchElementException("Фильма с ID=" + filmId + " не существует");
        } else if (!films.get(filmId).getLikedUserIds().contains(userId)) {
            throw new NoSuchElementException("Пользователь c ID=" + userId + " не ставил лайк фильму с ID=" + filmId);
        } else {
            films.get(filmId).getLikedUserIds().remove(userId);
            log.info("Пользователь с ID=" + userId + " убрал лайк с фильма с ID=" + filmId);
        }
    }

    @Override
    public List<Film> getHighRatedFilms(Integer count) {
        if (!films.isEmpty()) {
            return films.values().stream()
                    .sorted(Comparator.comparingInt(f -> f.getLikedUserIds().size() * -1))
                    .limit(count)
                    .collect(Collectors.toList());
        } else {
            throw new NoSuchElementException("Список фильмов пуст");
        }
    }
}