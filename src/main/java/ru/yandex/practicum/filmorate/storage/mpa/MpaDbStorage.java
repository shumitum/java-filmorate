package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mapper.MpaMapper;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Mpa getMpaById(Long mpaId) {
        return jdbcTemplate.query("SELECT * FROM MPA WHERE ID=?", new MpaMapper(), mpaId)
                .stream()
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("MPA рейтинга с ID=" + mpaId + " не существует"));
    }

    @Override
    public List<Mpa> getListOfMpa() {
        return jdbcTemplate.query("SELECT * FROM MPA", new MpaMapper());
    }
}