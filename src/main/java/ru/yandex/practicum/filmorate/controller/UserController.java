package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.ValidateService;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    private final ValidateService validateService;
    private final UserStorage userStorage;
    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody @Valid User user) {
        validateService.validateUser(user);
        userStorage.createUser(user);
        log.info("Пользователь создан: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        validateService.validateUser(user);
        userStorage.updateUser(user);
        log.info("Пользователь обновлён: {}", user);
        return user;
    }

    @GetMapping
    public List<User> getListOfUsers() {
        return userStorage.getListOfUsers();
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        return null;
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        return null;
    }

    @GetMapping("/{id}/friends")
    public List<User> getListOfFriends(@PathVariable Integer id) {
        return null;
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable Integer id, @PathVariable Integer friendId) {
        return null;
    }

}