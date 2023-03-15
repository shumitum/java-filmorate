package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import ru.yandex.practicum.filmorate.service.ValidateService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final ValidateService validateService;
    private final UserRepository userRepository;

    @PostMapping
    public User createUser(@RequestBody User user) {
        validateService.validateUser(user);
        userRepository.createUser(user);
        log.info("Пользователь: {} создан", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        validateService.validateUser(user);
        userRepository.updateUser(user);
        log.info("Пользователь: {} обновлён", user);
        return user;
    }

    @GetMapping
    public List<User> getListOfUsers() {
        return userRepository.getListOfUsers();
    }
}