package ru.yandex.practicum.filmorate.storage.friend;

import java.util.List;

public interface FriendStorage {
    void addFriend(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);

    List<Long> getListOfUserFriendsIds(Long userId);
}
