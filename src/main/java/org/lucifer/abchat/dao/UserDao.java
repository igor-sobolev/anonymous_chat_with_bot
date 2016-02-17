package org.lucifer.abchat.dao;

import org.lucifer.abchat.domain.User;

import java.util.List;

public interface UserDao extends BaseDao<User> {
    boolean logIn(User user);

    boolean register(User user);

    User findByLogin(String userLogin);

    List<User> top();
}
