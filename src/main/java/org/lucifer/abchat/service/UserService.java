package org.lucifer.abchat.service;

import org.lucifer.abchat.domain.User;

import java.util.List;

public interface UserService extends BaseService<User> {
    String logIn(User user);

    String register(User user);

    User findByLogin(String userLogin);

    void recountScore(User user);

    List<User> top();
}
