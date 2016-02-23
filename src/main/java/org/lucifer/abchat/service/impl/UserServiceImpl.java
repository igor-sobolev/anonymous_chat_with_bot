package org.lucifer.abchat.service.impl;


import org.lucifer.abchat.dao.UserDao;
import org.lucifer.abchat.domain.User;
import org.lucifer.abchat.domain.UserAnswer;
import org.lucifer.abchat.dto.UserDTO;
import org.lucifer.abchat.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    public static final int WIN_POINTS = 10;
    public static final int LOSE_POINTS = 15;

    public UserDTO logIn(User user) {
        UserDao dao = (UserDao) this.dao;
        if (dao.logIn(user)) {
            return new UserDTO(user.getLogin(), user.getEmail(), user.getScore());
        }
        return null;
    }

    public boolean register(User user) {
        UserDao dao = (UserDao) this.dao;
        if (dao.register(user)) {
            dao.save(user);
            return true;
        }
        return false;
    }

    public User findByLogin(String userLogin) {
        UserDao dao = (UserDao) this.dao;
        return dao.findByLogin(userLogin);
    }

    public void recountScore(User user) {
        Long score = 0L;
        for (UserAnswer ans : user.getAnswers()) {
            if (ans.getAnswer().equals(true)) {
                score += WIN_POINTS;
            } else {
                score -= LOSE_POINTS;
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

    public UserDTO get(String login) {
        User user = findByLogin(login);
        return new UserDTO(user.getLogin(), user.getEmail(), user.getScore());
    }
}
