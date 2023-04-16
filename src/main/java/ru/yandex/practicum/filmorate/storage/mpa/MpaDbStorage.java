package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Repository
@Primary
@RequiredArgsConstructor
@Slf4j
public class MpaDbStorage implements MpaStorage {
    @Override
    public Mpa getMpaById(Long mpaId) {
        return null;
    }

    @Override
    public List<Mpa> getListOfMpa() {
        return null;
    }
}
