package org.lucifer.abchat.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.lucifer.abchat.domain.Chat;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatDaoImpl extends BaseDaoImpl<Chat> implements ChatDao{
    public List<Long> freeRooms() {
        Session session = getSession();
        Query query = session.createQuery(
                "SELECT b.id FROM Cospeaker a, Chat b " +
                        "WHERE a.chat.id = b.id " +
                        "GROUP BY b.id " +
                        "HAVING COUNT(a.id)<2");
        List<Long> result = (List<Long>) query.list();
        return result;
    }
}
