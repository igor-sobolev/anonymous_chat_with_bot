package org.lucifer.abchat.service;

import org.lucifer.abchat.domain.User;

public interface PersonService extends BaseService<User> {
    String logIn(User user);

    String register(User user);

    User findByLogin(String userLogin);
}
