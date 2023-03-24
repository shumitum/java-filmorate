package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage, UserService {
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
            throw new NoSuchElementException("Пользователя с ID=" + user.getId() + " не существует");
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
    public void addFriend(Long userId, Long friendId) {
        if (!users.containsKey(userId)) {
            throw new NoSuchElementException("Пользователя с ID=" + userId + " не существует");
        } else if (!users.containsKey(friendId)) {
            throw new NoSuchElementException("Пользователя с ID=" + friendId + " не существует");
        } else {
            users.get(userId).getFriendIds().add(friendId);
            users.get(friendId).getFriendIds().add(userId);
            log.info("Пользователь с ID=" + userId + " добавил в друзья пользователя с ID=" + friendId);
        }
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        if (!users.containsKey(userId)) {
            throw new NoSuchElementException("Пользователя с ID=" + userId + " не существует");
        } else if (!users.containsKey(friendId)) {
            throw new NoSuchElementException("Пользователя с ID=" + friendId + " не существует");
        } else {
            users.get(userId).getFriendIds().remove(friendId);
            users.get(friendId).getFriendIds().remove(userId);
            log.info("Пользователь с ID=" + userId + " удалил из друзей пользователя с ID=" + friendId);
        }
    }

    @Override
    public List<User> getListOfHisHerFriends(Long friendId) {
        List<User> friendList = new LinkedList<>();
        if (users.containsKey(friendId)) {
            for (Long id : users.get(friendId).getFriendIds()) {
                if (users.containsKey((id))) {
                    friendList.add(users.get(id));
                }
            }
        } else {
            throw new NoSuchElementException("Пользователя с ID=" + friendId + " не существует");
        }
        return friendList;
    }

    @Override
    public List<User> getMutualFriends(Long userId, Long otherId) {
        List<User> friendList = new LinkedList<>();
        if (!users.containsKey(userId)) {
            throw new NoSuchElementException("Пользователя с ID=" + userId + " не существует");
        } else if (!users.containsKey(otherId)) {
            throw new NoSuchElementException("Пользователя с ID=" + otherId + " не существует");
        } else {
            Set<Long> mutualFriendIds = new HashSet<>(users.get(userId).getFriendIds());
            mutualFriendIds.retainAll(users.get(otherId).getFriendIds());
            for (Long mutualFriendId : mutualFriendIds) {
                if (users.containsKey((mutualFriendId))) {
                    friendList.add(users.get(mutualFriendId));
                }
            }
        }
        return friendList;
    }
}