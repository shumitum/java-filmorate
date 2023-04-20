package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.mpa.MpaService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
@Slf4j
public class MpaController {
    private final MpaService mpaService;

    @GetMapping("/{id}")
    public Mpa getMpaById(@PathVariable("id") Long mpaId) {
        log.info("Запрошен MPA-рейтинг с ID=" + mpaId);
        return mpaService.getMpaById(mpaId);
    }

    @GetMapping
    public List<Mpa> getListOfMpa() {
        log.info("Запрошен список категорий MPA-рейтинга");
        return mpaService.getListOfMpa();
    }
}