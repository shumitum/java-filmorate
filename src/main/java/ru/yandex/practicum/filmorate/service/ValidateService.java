package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Component
@Slf4j
public class ValidateService {
    public void validateUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("В качестве имени пользователя будет использован Логин: {}", user);
        }
    }

    public void validateFilm(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Некорректна дата релиза фильма: {}", film);
            throw new ValidationException("Некорректно заполнено поле Дата релиза (не раньше 28 декабря 1895 года)");
        }
    }
}