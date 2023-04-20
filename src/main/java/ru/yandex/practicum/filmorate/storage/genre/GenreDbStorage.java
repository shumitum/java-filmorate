package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
@Primary
@RequiredArgsConstructor
@Slf4j
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Genre getGenreById(Long genreId) {
        return jdbcTemplate.query("SELECT * FROM GENRES WHERE ID=?", new GenreMapper(), genreId)
                .stream()
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Жанра с ID=" + genreId + " не существует"));
    }

    @Override
    public List<Genre> getListOfGenres() {
        return jdbcTemplate.query("SELECT * FROM GENRES", new GenreMapper());
    }
}