package org.lucifer.abchat.dao;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lucifer.abchat.dao.impl.UserDaoImpl;
import org.lucifer.abchat.domain.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UserDaoTest {
    private UserDao userDao = mock(UserDaoImpl.class);

    User u = new User();

    @Before
    public void setupData() {
        u.setRole(User.ROLE_ADMIN);
        u.setId(1L);
        u.setScore(10L);
        u.setEmail("example@mail.com");
        u.setLogin("login");
        u.setPassword("password");
        userDao.save(u);

        when(userDao.save(u)).thenReturn(u);
        when(userDao.delete(1L)).thenReturn(u);
        when(userDao.count()).thenReturn(1L);
        when(userDao.findByLogin("login")).thenReturn(u);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testCount() {
        assertEquals(userDao.count().equals(1L), true);
    }

    @Test
    public void testFindByLogin() {
        assertEquals(userDao.findByLogin("login"), u);
    }

    @Test
    public void save() {
        User user = new User();
        user.setPassword("password");
        user.setLogin("login");
        user.setId(1L);
        user.setEmail("example@mail.com");
        user.setRole(User.ROLE_USER);
        assertEquals(userDao.save(user), u);
    }
}
