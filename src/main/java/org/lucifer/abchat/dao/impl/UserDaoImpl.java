package org.lucifer.abchat.dao.impl;


import org.hibernate.Query;
import org.hibernate.Session;
import org.lucifer.abchat.dao.UserDao;
import org.lucifer.abchat.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    public boolean logIn(User user) {
        Session session = getSession();
        Query query = session.createQuery(
                "select count(*) from User where login='"
                        + user.getLogin() + "' and password='"
                        + user.getPassword() + "'");
        long result = (Long) query.uniqueResult();
        return result != 0;
    }

    public boolean register(User user) {
        Session session = getSession();
        Query query = session.createQuery(
                "select count(login) from User where login=:login");
        query.setParameter("login", user.getLogin());
        long result = (Long) query.uniqueResult();
        return result == 0;
    }

    public User findByLogin(String userLogin) {
        Session session = getSession();
        Query query = session.createQuery(
                "from User where login=:login");
        query.setParameter("login", userLogin);
        User result = (User) query.uniqueResult();
        return result;
    }

    public List<User> top() {
        Session session = getSession();
        Query query = session.createQuery(
                "from User order by score desc");
        final int topCount = 10;
        final int start = 0;
        query.setFirstResult(start);
        query.setMaxResults(topCount);
        List<User> result = (List<User>) query.list();
        return result;
    }
}
