package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class ValidateServiceTest {
    private User user;
    private Film film;
    private final ValidateService validateService = new ValidateService();

    @BeforeEach
    void setUp() {
        user = new User(1L, "email@test.com", "Login", "Name", Date.valueOf("2000-1-1"));
        film = new Film();
        film.setId(1L);
        film.setName("Movie");
        film.setDescription("This movie is about...");
        film.setReleaseDate(Date.valueOf("2000-1-1"));
        film.setDuration(120);
        film.setRate(0);
    }

    @AfterEach
    void tearDown() {
        user = null;
        film = null;
    }

    @Test
    void userValidationShouldNotThrowAnyExceptions() {
        assertDoesNotThrow(() -> validateService.validateUser(user));
    }

    @Test
    void shouldUseLoginAsUserName() {
        user.setName(null);
        validateService.validateUser(user);

        assertEquals(user.getLogin(), user.getName(), "Логин и Имя пользователя должны совпадать");
    }

    @Test
    void filmValidationShouldNotThrowAnyExceptions() {
        assertDoesNotThrow(() -> validateService.validateFilm(film));
    }

    @Test
    void shouldNotThrowExceptionsBecauseOfDescription() {
        film.setDescription("В этом описании ровно 200 знаков. В этом описании ровно 200 знаков. " +
                "В этом описании ровно 200 знаков. В этом описании ровно 200 знаков " +
                "В этом описании ровно 200 знаков В этом описании ровно 200 знаков");

        assertEquals(200, film.getDescription().length(), "В описании должно быть 200 знаков");
        assertDoesNotThrow(() -> validateService.validateFilm(film));
    }

    @Test
    void shouldThrowExceptionBecauseOfWrongReleaseDate() {
        film.setReleaseDate(Date.valueOf("1895-12-27")/*LocalDate.of(1895, 12, 27)*/);
        assertThrows(ValidationException.class, () -> validateService.validateFilm(film));
    }

    @Test
    void shouldNotThrowExceptionsBecauseOfReleaseDate() {
        film.setReleaseDate(Date.valueOf("1895-12-28"));
        assertDoesNotThrow(() -> validateService.validateFilm(film));
    }

    @Test
    void shouldNotThrowExceptionsBecauseOfDuration() {
        film.setDuration(1);
        assertDoesNotThrow(() -> validateService.validateFilm(film));
    }
}