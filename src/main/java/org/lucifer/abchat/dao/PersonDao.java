package org.lucifer.abchat.dao;

import org.lucifer.abchat.domain.User;

public interface PersonDao extends BaseDao<User>{
    public boolean logIn(User user);
    public boolean register(User user);
}
