package ru.yandex.practicum.filmorate.storage.friend;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(Long userId, Long friendId) { //to friends DAO
        jdbcTemplate.update("INSERT INTO FRIENDS (USER_ID, FRIEND_ID) VALUES(?,?)", userId, friendId);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) { //to friends DAO
        jdbcTemplate.update("DELETE FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?", userId, friendId);
    }

    @Override
    public List<Long> getListOfUserFriendsIds(Long userId) { //to friends DAO
        return jdbcTemplate.queryForList("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=?", Long.class, userId);
    }
}
