package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.ValidateService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {
    private final ValidateService validateService;
    private final FilmService filmService;

    @PostMapping
    public Film addFilm(@RequestBody @Valid Film film) {
        validateService.validateFilm(film);
        filmService.addFilm(film);
        log.info("фильм добавлен: {}", film);
        return filmService.getFilmById(film.getId());
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        validateService.validateFilm(film);
        filmService.updateFilm(film);
        log.info("фильм обновлён: {}", film);
        return filmService.getFilmById(film.getId());
    }

    @GetMapping
    public List<Film> getListOfFilms() {
        log.info("Запрошен список всех фильмов");
        return filmService.getListOfFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable("id") Long filmId) {
        log.info("Запрошен фильм с ID=" + filmId);
        return filmService.getFilmById(filmId);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable("id") Long filmId, @PathVariable Long userId) {
        filmService.addLike(filmId, userId);
        log.info("Пользователь с ID=" + userId + " поставил лайк фильму с ID=" + filmId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") Long filmId, @PathVariable Long userId) {
        filmService.deleteLike(filmId, userId);
        log.info("Пользователь с ID=" + userId + " убрал лайк с фильма с ID=" + filmId);
    }

    @GetMapping("/popular")
    public List<Film> getListOfHighRatedFilms(@RequestParam(defaultValue = "10") Integer count) {
        log.info("Запрошен список из " + count + " фильмов с самым высоким рейтингом");
        return filmService.getHighRatedFilms(count);
    }

    @DeleteMapping("/{id}")
    public void deleteFilmById(@PathVariable("id") Long filmId) {
        filmService.deleteFilmById(filmId);
        log.info("Фильм с ID=" + filmId + " удалён");
    }
}