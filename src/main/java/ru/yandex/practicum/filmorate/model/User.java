package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    private int id;
    @Email
    @NotBlank(message = "Поле email не должно быть пустым")
    private String email;
    @NonNull
    @Pattern(regexp = "^[a-zA-Z]{1,20}$",
            message = "Поле Логин (не более 20 символов) должно состоять только из символов английского алфавита")
    private String login;
    private String name;
    @NonNull
    @PastOrPresent(message = "День рождения не может быть в будущем")
    private LocalDate birthday;
}