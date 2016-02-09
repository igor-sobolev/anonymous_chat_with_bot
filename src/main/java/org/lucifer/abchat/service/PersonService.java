package org.lucifer.abchat.service;

import org.lucifer.abchat.domain.User;

public interface PersonService extends BaseService<User> {
    boolean logIn(User user);

    boolean register(User user);

    User findByLogin(String userLogin);
}
