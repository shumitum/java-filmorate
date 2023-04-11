package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class User {
    private Long id;
    @Email
    @NotBlank(message = "Поле email не должно быть пустым")
    private String email;
    @NotBlank(message = "Поле Логин не должно быть пустым")
    @Pattern(regexp = "^[a-zA-Z]{1,20}$",
            message = "Поле Логин (не более 20 символов) должно состоять только из символов английского алфавита")
    private String login;
    private String name;
    @PastOrPresent(message = "День рождения не может быть в будущем")
    private LocalDate birthday;
    private final Set<Long> friendIds = new HashSet<>();
}