package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    void createUser(User user);

    void updateUser(User user);

    List<User> getListOfUsers();

    User getUserById(Long userId);

    void deleteUserById(Long userId);

    void addFriend(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);

    List<Long> getListOfUserFriendsIds(Long userId);
}