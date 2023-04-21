package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.Date;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SqlGroup({
        @Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = {"classpath:test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
class FilmDbStorageTest {

    private Film film;
    private Mpa mpa;

    @Autowired
    private final FilmStorage filmDbStorage;

    @BeforeEach
    void setUp() {
        mpa = new Mpa();
        mpa.setId(1L);
        film = new Film();
        film.setName("Movie");
        film.setDescription("This movie is about...");
        film.setReleaseDate(Date.valueOf("2000-01-01"));
        film.setDuration(120);
        film.setRate(3);
        film.setMpa(mpa);
    }

    @AfterEach
    void tearDown() {
        film = null;
        mpa = null;
    }

    @Test
    void shouldAddFilmToDb() {
        assertDoesNotThrow(() -> filmDbStorage.addFilm(film));
        assertEquals(2, filmDbStorage.getListOfFilms().size());
    }

    @Test
    void shouldUpdateFilmInDb() {
        mpa.setId(2L);
        mpa.setName("PG");
        Film updatedFilm = new Film(1L,
                "New movie",
                "New movie is about...",
                Date.valueOf("1999-04-30"),
                180,
                0,
                mpa);

        assertDoesNotThrow(() -> filmDbStorage.updateFilm(updatedFilm));
        assertEquals(updatedFilm, filmDbStorage.getFilmById(1L));
    }

    @Test
    void sizeOfFilmsListShouldBeEqualsOne() {
        assertEquals(1, filmDbStorage.getListOfFilms().size());
    }

    @Test
    void sizeOfFilmsListShouldBeEqualsTwo() {
        filmDbStorage.addFilm(film);
        assertEquals(2, filmDbStorage.getListOfFilms().size());
    }

    @Test
    void sizeOfListOfHighRatedFilmsShouldBeEqualsOne() {
        assertEquals(1, filmDbStorage.getListOfHighRatedFilms(1).size());
    }

    @Test
    void sizeOfListOfHighRatedFilmsShouldBeEqualsTwo() {
        filmDbStorage.addFilm(film);
        filmDbStorage.addLike(1L, 1L);

        assertEquals(2, filmDbStorage.getListOfHighRatedFilms(2).size());
        assertEquals(1, filmDbStorage.getListOfHighRatedFilms(2).get(0).getId());
        assertEquals(2, filmDbStorage.getListOfHighRatedFilms(2).get(1).getId());
        assertEquals(1, filmDbStorage.getListOfHighRatedFilms(2).get(0).getRate());
        assertEquals(0, filmDbStorage.getListOfHighRatedFilms(2).get(1).getRate());
    }

    @Test
    void getFilmByIdShouldThrowExceptionBecauseOfWrongId() {
        assertThrows(NoSuchElementException.class, () -> filmDbStorage.getFilmById(999L));
    }

    @Test
    void shouldReturnCorrectFilmFromDb() {
        Film expectedFilm = new Film(1L,
                "New film",
                "New film about friends",
                Date.valueOf("1999-04-30"),
                120,
                5,
                new Mpa(1L, "G"));

        assertDoesNotThrow(() -> filmDbStorage.getFilmById(1L));
        assertEquals(expectedFilm, filmDbStorage.getFilmById(1L));
    }

    @Test
    void shouldDeleteFilmById() {
        assertEquals(1, filmDbStorage.getListOfFilms().size());
        filmDbStorage.deleteFilmById(1L);

        assertEquals(0, filmDbStorage.getListOfFilms().size());
    }

    @Test
    void shouldAddLikeToFilm() {
        assertEquals(5, filmDbStorage.getFilmById(1L).getRate());
        filmDbStorage.addLike(1L, 1L);

        assertEquals(1, filmDbStorage.getFilmById(1L).getRate());
    }

    @Test
    void shouldDeleteLikeFromFilm() {
        filmDbStorage.addLike(1L, 1L);
        assertEquals(1, filmDbStorage.getFilmById(1L).getRate());

        filmDbStorage.deleteLike(1L, 1L);
        assertEquals(0, filmDbStorage.getFilmById(1L).getRate());
    }
}