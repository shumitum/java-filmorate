package ru.yandex.practicum.filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class FilmorateApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmorateApplication.class, args);

/*        InMemoryFilmStorage film = new InMemoryFilmStorage();
        film.addFilm(new Film(1, "Movie", "This movie is about...", LocalDate.of(2000, 1, 1), 120));
        film.addFilm(new Film(2, "Movie", "This movie is about...", LocalDate.of(2000, 1, 1), 120));
        film.addFilm(new Film(3, "Movie", "This movie is about...", LocalDate.of(2000, 1, 1), 120));
        film.addFilm(new Film(4, "Movie", "This movie is about...", LocalDate.of(2000, 1, 1), 120));
        film.addFilm(new Film(5, "Movie", "This movie is about...", LocalDate.of(2000, 1, 1), 120));
        film.addFilm(new Film(6, "Movie", "This movie is about...", LocalDate.of(2000, 1, 1), 120));
        film.addFilm(new Film(7, "Movie", "This movie is about...", LocalDate.of(2000, 1, 1), 120));
        film.addFilm(new Film(8, "Movie", "This movie is about...", LocalDate.of(2000, 1, 1), 120));
        film.addFilm(new Film(9, "Movie", "This movie is about...", LocalDate.of(2000, 1, 1), 120));
        film.addFilm(new Film(10, "Movie", "This movie is about...", LocalDate.of(2000, 1, 1), 120));
        film.addFilm(new Film(11, "Movie", "This movie is about...", LocalDate.of(2000, 1, 1), 120));

        film.addLike(1,1);
        film.addLike(1,2);
        film.addLike(1,3);
        film.addLike(2,1);
        film.addLike(7,3);
        film.addLike(7,1);

        System.out.println(film.getListOfFilms());
        System.out.println(film.getHighRatedFilms(0));*/
    }
}