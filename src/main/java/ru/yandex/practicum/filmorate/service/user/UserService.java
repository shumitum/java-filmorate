package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        User potentialFriend = userStorage.getUserById(friendId);
        userStorage.getUserById(userId).getFriendIds().add(friendId);
        potentialFriend.getFriendIds().add(userId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        User friend = userStorage.getUserById(friendId);
        userStorage.getUserById(userId).getFriendIds().remove(friendId);
        friend.getFriendIds().remove(userId);
    }

    public List<User> getListOfHisHerFriends(Long userId) {
        return userStorage.getUserById(userId).getFriendIds().stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }

    public List<User> getMutualFriends(Long userId, Long otherId) {
        Set<Long> mutualFriendIds = new HashSet<>(userStorage.getUserById(userId).getFriendIds());
        mutualFriendIds.retainAll(userStorage.getUserById(otherId).getFriendIds());
        return mutualFriendIds.stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }
}