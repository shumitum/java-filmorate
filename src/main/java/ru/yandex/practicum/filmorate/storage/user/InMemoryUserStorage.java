package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage, UserService {
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

    @Override
    public User getUserById(int userId) {
        return null;
    }

    @Override
    public void addFriend(int id, int friendId) {

    }

    @Override
    public void deleteFriend(int id, int friendId) {

    }

    @Override
    public List<User> getListOfHisHerFriends(int friendId) {
        return null;
    }

    @Override
    public List<User> getMutualFriends() {
        return null;
    }
}