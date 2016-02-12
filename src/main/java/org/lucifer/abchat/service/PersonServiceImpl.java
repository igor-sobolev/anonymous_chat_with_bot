package org.lucifer.abchat.service;


import org.lucifer.abchat.dao.PersonDao;
import org.lucifer.abchat.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PersonServiceImpl extends BaseServiceImpl<User> implements PersonService {
    public String logIn(User user) {
        PersonDao dao = (PersonDao) this.dao;
        if (dao.logIn(user)) {
            return "Ok";
        }
        return "Error";
    }

    public String register(User user) {
        PersonDao dao = (PersonDao) this.dao;
        if (dao.register(user)) {
            dao.save(user);
            return "Ok";
        }
        return "Error";
    }

    public User findByLogin(String userLogin) {
        PersonDao dao = (PersonDao) this.dao;
        return dao.findByLogin(userLogin);
    }
}
