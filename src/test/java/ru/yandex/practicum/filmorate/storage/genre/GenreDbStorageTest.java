package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GenreDbStorageTest {

    @Autowired
    private final GenreStorage genreStorage;

    @Test
    void getGenreByIdShouldNotThrowExceptionBecauseOfId() {
        assertDoesNotThrow(() -> genreStorage.getGenreById(1L));
    }

    @Test
    void getGenreByIdShouldThrowExceptionBecauseOfId() {
        assertThrows(NoSuchElementException.class, () -> genreStorage.getGenreById(99L));
    }

    @Test
    void genresListShouldEqualSix() {
        assertEquals(6, genreStorage.getListOfGenres().size());
    }

    @Test
    void genreOneShouldEqualG() {
        Genre newGenre = new Genre(1L, "Комедия");

        assertEquals(newGenre, genreStorage.getListOfGenres().get(0));
    }
}