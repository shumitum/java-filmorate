package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MpaDbStorageTest {

    @Autowired
    private final MpaStorage mpaStorage;

    @Test
    void getMpaByIdShouldNotThrowExceptionBecauseOfId() {
        assertDoesNotThrow(() -> mpaStorage.getMpaById(1L));
    }

    @Test
    void getMpaByIdShouldThrowExceptionBecauseOfId() {
        assertThrows(NoSuchElementException.class, () -> mpaStorage.getMpaById(99L));
    }

    @Test
    void mpaListShouldEqualFive() {
        assertEquals(5, mpaStorage.getListOfMpa().size());
    }

    @Test
    void mpaOneShouldEqualG() {
        Mpa expectedMpa = new Mpa(1L, "G");

        assertEquals(expectedMpa, mpaStorage.getListOfMpa().get(0));
    }
}