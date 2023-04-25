package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
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

    @Override
    public void updateFilmsGenresDb(Film film) {
        jdbcTemplate.update("DELETE FROM FILM_GENRES WHERE FILM_ID=?", film.getId());
        if (!film.getGenres().isEmpty()) {
            film.getGenres().forEach((genre ->
                    jdbcTemplate.update("INSERT INTO FILM_GENRES (FILM_ID, GENRE_ID) VALUES(?,?)",
                            film.getId(), genre.getId())));
        }
    }

    @Override
    public void updateFilmsGenreList(Film film) {
        jdbcTemplate.queryForList("SELECT GENRE_ID FROM FILM_GENRES WHERE FILM_ID=?", Long.class, film.getId())
                .stream()
                .map(this::getGenreById)
                .forEach(genre -> film.getGenres().add(genre));
    }
}