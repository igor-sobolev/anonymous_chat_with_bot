package org.lucifer.abchat.service;


import org.lucifer.abchat.dao.PersonDao;
import org.lucifer.abchat.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PersonServiceImpl extends BaseServiceImpl<User> implements PersonService{
    public boolean logIn(User user) {
        PersonDao dao = (PersonDao) this.dao;
        if (dao.logIn(user)) return true;
        return false;
    }

    public boolean register(User user) {
        PersonDao dao = (PersonDao) this.dao;
        if (dao.register(user)) return true;
        return false;
    }
}
