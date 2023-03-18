package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmManager;
import ru.yandex.practicum.filmorate.service.ValidateService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {
    private final ValidateService validateService;
    private final FilmManager filmManager;

    @PostMapping
    public Film addFilm(@RequestBody @Valid Film film) {
        validateService.validateFilm(film);
        filmManager.addFilm(film);
        log.info("фильм добавлен: {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        validateService.validateFilm(film);
        filmManager.updateFilm(film);
        log.info("фильм обновлён: {}", film);
        return film;
    }

    @GetMapping
    public List<Film> getListOfFilms() {
        return filmManager.getListOfFilms();
    }
}