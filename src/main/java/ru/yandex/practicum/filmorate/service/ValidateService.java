package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Component
@Slf4j
public class ValidateService {
    public void validateUser(User user) {
        if (user != null) {
            if (user.getEmail() == null || !user.getEmail().contains("@")) {
                log.warn("Пустое имя пользователя: {}", user);
                throw new ValidationException("Не заполнено поле email");
            }
            if (user.getLogin() == null || user.getLogin().contains(" ")) {
                log.warn("Некорректно заполнено поле Логин пользователя: {}", user.getName());
                throw new ValidationException("Некорректно заполнено поле Логин");
            }
            if (user.getName() == null || user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            if (user.getBirthday().isAfter(LocalDate.now())) {
                log.warn("Некорректно заполнено поле День Рождения пользователя: {}", user.getName());
                throw new ValidationException("Некорректно заполнено поле День Рождения");
            }
        } else {
            log.warn("Переданный объект User = NULL");
            throw new ValidationException("Переданный объект User = NULL");
        }
    }

    public void validateFilm(Film film) {
        if (film != null) {
            if (film.getName() == null || film.getName().isBlank()) {
                log.warn("Пустое название фильма: {}", film);
                throw new ValidationException("Не заполнено поле Название фильма");
            }
            if (film.getDescription().length() > 200) {
                log.warn("Более 200 символов в описании фильма: {}", film.getName());
                throw new ValidationException("Некорректно заполнено поле Описание (не более 200 символов)");
            }
            if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
                log.warn("Некорректна дата релиза фильма: {}", film.getName());
                throw new ValidationException("Некорректно заполнено поле Дата релиза (не раньше 28 декабря 1895 года)");
            }
            if (film.getDuration() <= 0) {
                log.warn("Некорректная продолжительность фильма: {}", film.getName());
                throw new ValidationException("Некорректно заполнено поле Продолжительность фильма");
            }
        } else {
            log.warn("Переданный объект Film = NULL");
            throw new ValidationException("Переданный объект Film = NULL");
        }
    }
}
