package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
@Slf4j
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable("id") Long genreId) {
        log.info("Запрошен жанр с ID=" + genreId);
        return genreService.getGenreById(genreId);
    }

    @GetMapping
    public List<Genre> getListOfMpa() {
        log.info("Запрошен список всех жанров");
        return genreService.getListOfGenres();
    }
}