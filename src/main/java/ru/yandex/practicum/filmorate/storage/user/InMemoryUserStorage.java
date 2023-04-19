package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private Long userId = 0L;

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
            throw new NoSuchElementException("Невозможно обновить данные. Пользователя с ID=" + user.getId() + " не существует");
        }
    }

    @Override
    public List<User> getListOfUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(Long userId) {
        return users.values().stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Пользователя с ID=" + userId + " не существует"));
    }

    @Override
    public void deleteUserById(Long userId) {
        if (users.containsKey(userId)) {
            users.remove(userId);
        } else {
            throw new NoSuchElementException("Невозможно удалить. Пользователя с ID=" + userId + " не существует");
        }
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        getUserById(userId).getFriendIds().add(friendId);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        getUserById(userId).getFriendIds().remove(friendId);
    }

    @Override
    public List<Long> getListOfUserFriendsIds(Long userId) {
        return new ArrayList<>(getUserById(userId).getFriendIds());
    }
}