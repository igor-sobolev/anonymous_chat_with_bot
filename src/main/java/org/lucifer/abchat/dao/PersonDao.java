package org.lucifer.abchat.dao;

import org.lucifer.abchat.domain.User;

public interface PersonDao extends BaseDao<User> {
    boolean logIn(User user);

    boolean register(User user);

    User findByLogin(String userLogin);
}
