package org.lucifer.abchat.service;


import org.lucifer.abchat.dao.UserDao;
import org.lucifer.abchat.domain.User;
import org.lucifer.abchat.domain.UserAnswer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    public String logIn(User user) {
        UserDao dao = (UserDao) this.dao;
        if (dao.logIn(user)) {
            return "Ok";
        }
        return "Error";
    }

    public String register(User user) {
        UserDao dao = (UserDao) this.dao;
        if (dao.register(user)) {
            dao.save(user);
            return "Ok";
        }
        return "Error";
    }

    public User findByLogin(String userLogin) {
        UserDao dao = (UserDao) this.dao;
        return dao.findByLogin(userLogin);
    }

    public void recountScore(User user) {
        Long score = 0L;
        for (UserAnswer ans : user.getAnswers()) {
            if (ans.getAnswer().equals(true)) {
                score += 10;
            } else {
                score -= 15;
            }
        }
        if (score < 0) score = 0L;
        user.setScore(score);
        dao.save(user);
    }

    public List<User> top() {
        UserDao dao = (UserDao) this.dao;
        return dao.top();
    }
}
