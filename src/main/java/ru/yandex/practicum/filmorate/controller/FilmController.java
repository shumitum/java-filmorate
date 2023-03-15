package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.service.ValidateService;

import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {
    private final ValidateService validateService;
    private final FilmRepository filmRepository;

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        validateService.validateFilm(film);
        filmRepository.addFilm(film);
        log.info("фильм: {} добавлен", film);
        return film;
    }

    @PutMapping
    public Film FilmupdateFilm(@RequestBody Film film) {
        validateService.validateFilm(film);
        filmRepository.updateFilm(film);
        log.info("фильм: {} обновлён", film);
        return film;
    }

    @GetMapping
    public List<Film> getListOfFilms() {
        return filmRepository.getListOfFilms();
    }
}