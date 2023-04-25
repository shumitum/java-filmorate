package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;


import java.sql.Date;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SqlGroup({
        @Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = {"classpath:test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
class UserDbStorageTest {

    @Autowired
    private final UserStorage userStorage;
    @Autowired
    private final FriendStorage friendStorage;
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("gmail@gmail.com");
        user.setLogin("login");
        user.setName("Name");
        user.setBirthday(Date.valueOf("2000-01-15"));
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    @Test
    void shouldCreateNewUserInDb() {
        assertDoesNotThrow(() -> userStorage.createUser(user));
        assertEquals(2, userStorage.getListOfUsers().size());
    }

    @Test
    void shouldUpdateUserInDb() {
        User updatedUser = new User(1L,
                "new@email.com",
                "newLogin",
                "New Name",
                Date.valueOf("1999-01-16"));

        assertDoesNotThrow(() -> userStorage.updateUser(updatedUser));
        assertEquals(updatedUser, userStorage.getUserById(1L));
    }

    @Test
    void shouldReturnListOfUsers() {
        assertEquals(1, userStorage.getListOfUsers().size());
    }

    @Test
    void sizeOfUsersListShouldBeEqualsTwo() {
        userStorage.createUser(user);
        assertEquals(2, userStorage.getListOfUsers().size());
    }

    @Test
    void getUserByIdShouldThrowExceptionBecauseOfWrongId() {
        assertThrows(NoSuchElementException.class, () -> userStorage.getUserById(999L));
    }

    @Test
    void shouldReturnCorrectUserFromDb() {
        User expectedUser = new User(1L,
                "mail@mail.ru",
                "dolore",
                "Nick Name",
                Date.valueOf("1946-08-20"));

        assertDoesNotThrow(() -> userStorage.getUserById(1L));
        assertEquals(expectedUser, userStorage.getUserById(1L));
    }

    @Test
    void shouldDeleteUserById() {
        assertEquals(1, userStorage.getListOfUsers().size());
        userStorage.deleteUserById(1L);

        assertEquals(0, userStorage.getListOfUsers().size());
    }

    @Test
    void shouldAddFriendByUserId() {
        userStorage.createUser(user);
        Long numberOfFriends = jdbcTemplate.queryForObject("SELECT COUNT(FRIEND_ID) FROM FRIENDS WHERE USER_ID=?",
                Long.class, 1);
        assertEquals(0, numberOfFriends);

        friendStorage.addFriend(1L, 2L);

        Long updNumberOfFriends = jdbcTemplate.queryForObject("SELECT COUNT(FRIEND_ID) FROM FRIENDS WHERE USER_ID=?",
                Long.class, 1);
        assertEquals(1, updNumberOfFriends);

        Long friendId = jdbcTemplate.queryForObject("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=?",
                Long.class, 1);
        assertEquals(2, friendId);
    }

    @Test
    void shouldDeleteFriendFromDb() {
        userStorage.createUser(user);
        friendStorage.addFriend(1L, 2L);
        Long numberOfFriends = jdbcTemplate.queryForObject("SELECT COUNT(FRIEND_ID) FROM FRIENDS WHERE USER_ID=?",
                Long.class, 1);
        assertEquals(1, numberOfFriends);

        friendStorage.deleteFriend(1L, 2L);

        Long updNumberOfFriends = jdbcTemplate.queryForObject("SELECT COUNT(FRIEND_ID) FROM FRIENDS WHERE USER_ID=?",
                Long.class, 1);
        assertEquals(0, updNumberOfFriends);
    }

    @Test
    void getListOfUserFriendsIds() {
        userStorage.createUser(user);
        friendStorage.addFriend(1L, 2L);

        assertEquals(1, friendStorage.getListOfUserFriendsIds(1L).size());
        assertEquals(2, friendStorage.getListOfUserFriendsIds(1L).get(0));
    }
}