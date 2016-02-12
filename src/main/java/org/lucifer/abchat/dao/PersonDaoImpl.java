package org.lucifer.abchat.dao;


import org.hibernate.Query;
import org.hibernate.Session;
import org.lucifer.abchat.domain.User;
import org.springframework.stereotype.Component;

@Component
public class PersonDaoImpl extends BaseDaoImpl<User> implements PersonDao {
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
}
