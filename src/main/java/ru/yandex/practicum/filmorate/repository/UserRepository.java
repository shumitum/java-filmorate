package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserRepository implements UserManager {
    private final Map<Integer, User> users = new HashMap<>();
    private int userId;

    @Override
    public void createUser(User user) {
        user.setId(++userId);
        users.put(userId, user);
    }

    @Override
    public void updateUser(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            throw new ValidationException("Пользователя с указанным ID не существует");
        }
    }

    @Override
    public List<User> getListOfUsers() {
        return new ArrayList<>(users.values());
    }
}