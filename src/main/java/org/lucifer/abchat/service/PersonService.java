package org.lucifer.abchat.service;

import org.lucifer.abchat.domain.User;

public interface PersonService extends BaseService<User>{
    public boolean logIn(User user);
    public boolean register(User user);
}
