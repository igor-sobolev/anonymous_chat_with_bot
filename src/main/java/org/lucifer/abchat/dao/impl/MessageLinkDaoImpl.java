package org.lucifer.abchat.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.lucifer.abchat.dao.MessageLinkDao;
import org.lucifer.abchat.domain.MessageLink;
import org.springframework.stereotype.Component;

@Component
public class MessageLinkDaoImpl extends BaseDaoImpl<MessageLink> implements MessageLinkDao {
    public Long count() {
        Session session = getSession();
        Query query = session.createQuery(
                "select count(*) from MessageLink");
        long result = (Long) query.uniqueResult();
        return result;
    }
}
