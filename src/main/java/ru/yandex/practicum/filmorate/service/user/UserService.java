package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public void createUser(User user) {
        userStorage.createUser(user);
    }

    public void updateUser(User user) {
        userStorage.updateUser(user);
    }

    public List<User> getListOfUsers() {
        return userStorage.getListOfUsers();
    }

    public User getUserById(Long userId) {
        return userStorage.getUserById(userId);
    }

    public void deleteUserById(Long userId) {
        userStorage.deleteUserById(userId);
    }

    public void addFriend(Long userId, Long friendId) {
        userStorage.getUserById(userId);
        userStorage.getUserById(friendId);
        userStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        userStorage.getUserById(userId);
        userStorage.getUserById(friendId);
        userStorage.deleteFriend(userId, friendId);
    }

    public List<User> getListOfUserFriends(Long userId) {
        userStorage.getUserById(userId);
        return userStorage.getListOfUserFriendsIds(userId)
                .stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }

    public List<User> getMutualFriends(Long userId, Long otherId) {
        List<Long> mutualFriendIds = userStorage.getListOfUserFriendsIds(userId);
        mutualFriendIds.retainAll(userStorage.getListOfUserFriendsIds(otherId));
        return mutualFriendIds.stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }
}