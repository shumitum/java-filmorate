package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.ValidateService;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final ValidateService validateService;
    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody @Valid User user) {
        validateService.validateUser(user);
        userService.createUser(user);
        log.info("Пользователь создан: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        validateService.validateUser(user);
        userService.updateUser(user);
        log.info("Пользователь обновлён: {}", user);
        return user;
    }

    @GetMapping
    public List<User> getListOfUsers() {
        log.info("Запрошен список всех пользователей");
        return userService.getListOfUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long userId) {
        log.info("Запрошен пользователь с ID=" + userId);
        return userService.getUserById(userId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") Long userId, @PathVariable Long friendId) {
        userService.addFriend(userId, friendId);
        log.info("Пользователь с ID=" + userId + " добавил в друзья пользователя с ID=" + friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") Long userId, @PathVariable Long friendId) {
        userService.deleteFriend(userId, friendId);
        log.info("Пользователь с ID=" + userId + " удалил из друзей пользователя с ID=" + friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getListOfHisHerFriends(@PathVariable("id") Long userId) {
        log.info("Запрошен список друзей пользователя с ID=" + userId);
        return userService.getListOfHisHerFriends(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable("id") Long userId, @PathVariable Long otherId) {
        log.info("Пользователь ID=" + userId + " запросил список общих друзей с пользователем ID=" + otherId);
        return userService.getMutualFriends(userId, otherId);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long userId) {
        userService.deleteUserById(userId);
        log.info("Пользователь с ID=" + userId + " удалён");
    }
}