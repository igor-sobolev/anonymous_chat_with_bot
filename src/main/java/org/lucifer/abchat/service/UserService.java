package org.lucifer.abchat.service;

import org.lucifer.abchat.domain.User;
import org.lucifer.abchat.dto.UserDTO;

import java.util.List;

public interface UserService extends BaseService<User> {
    UserDTO logIn(User user);

    boolean register(User user);

    User findByLogin(String userLogin);

    void recountScore(User user);

    List<User> top();

    UserDTO get(String login);
}
