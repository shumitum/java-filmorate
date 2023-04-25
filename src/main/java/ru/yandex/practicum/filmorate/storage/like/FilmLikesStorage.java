package ru.yandex.practicum.filmorate.storage.like;

public interface FilmLikesStorage {

    void updateFilmRateDb(Long filmId);

    void addLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);
}
