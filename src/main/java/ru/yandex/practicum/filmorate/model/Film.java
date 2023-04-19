package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private Long id;
    @NotBlank(message = "Название фильма не должно быть пустым")
    private String name;
    @Size(max = 200, message = "В названии фильма должны быть не более 200 символов)")
    private String description;
    private Date releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    private int duration;
    private int rate;
    private Mpa mpa;
    private final Set<Long> likedUserIds = new HashSet<>();
    private final Set<Genre> genres = new TreeSet<>(Comparator.comparingLong(Genre::getId));

    public int getRate() {
        return likedUserIds.size();
    }
}