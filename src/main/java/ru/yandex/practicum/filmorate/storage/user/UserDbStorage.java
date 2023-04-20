package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Repository
@Primary
@RequiredArgsConstructor
@Slf4j
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void createUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO USERS (EMAIL, LOGIN, USER_NAME, BIRTHDAY) VALUES(?,?,?,?)",
                    new String[]{"USER_ID"});
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getName());
            ps.setDate(4, user.getBirthday());
            return ps;
        }, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public void updateUser(User user) {
        getUserById(user.getId());
        jdbcTemplate.update("UPDATE USERS SET EMAIL=?, LOGIN=?, USER_NAME=?, BIRTHDAY=? WHERE USER_ID=?",
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
    }

    @Override
    public List<User> getListOfUsers() {
        return jdbcTemplate.query("SELECT * FROM USERS", new UserMapper());
    }

    @Override
    public User getUserById(Long userId) {
        return jdbcTemplate.query("SELECT * FROM USERS WHERE USER_ID=?", new UserMapper(), userId)
                .stream()
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Пользователя с ID=" + userId + " не существует"));
    }

    @Override
    public void deleteUserById(Long userId) {
        jdbcTemplate.update("DELETE FROM USERS WHERE USER_ID=?", userId);
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        jdbcTemplate.update("INSERT INTO FRIENDS (USER_ID, FRIEND_ID) VALUES(?,?)", userId, friendId);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        jdbcTemplate.update("DELETE FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?", userId, friendId);
    }

    @Override
    public List<Long> getListOfUserFriendsIds(Long userId) {
        return jdbcTemplate.queryForList("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=?", Long.class, userId);
    }
}