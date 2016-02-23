package org.lucifer.abchat.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.lucifer.abchat.dao.ChatDao;
import org.lucifer.abchat.domain.Chat;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatDaoImpl extends BaseDaoImpl<Chat> implements ChatDao {
    public List<Long> freeRooms() {
        Session session = getSession();
        /*
        "SELECT b.id FROM Cospeaker a, Chat b " +
                        "WHERE a.chat.id = b.id " +
                        "GROUP BY b.id " +
                        "HAVING COUNT(a.id)<2"
         */
        Query query = session.createQuery("SELECT c.id " +
                "FROM Chat c " +
                "WHERE (SELECT COUNT(co.id) " +
                "FROM Cospeaker co " +
                "WHERE co.chat.id = c.id) < 2");
        List<Long> result = (List<Long>) query.list();
        return result;
    }
}
