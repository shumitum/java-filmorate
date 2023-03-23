package ru.yandex.practicum.filmorate.service.user;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
public interface UserService {

    User getUserById(int userId);

    void addFriend(int id, int friendId);

    void deleteFriend(int id, int friendId);

    List<User> getListOfHisHerFriends(int friendId);

    List<User> getMutualFriends();
}