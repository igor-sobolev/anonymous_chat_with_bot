package org.lucifer.abchat.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.lucifer.abchat.dao.MessageLinkDao;
import org.lucifer.abchat.domain.MessageLink;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MessageLinkDaoImpl extends BaseDaoImpl<MessageLink> implements MessageLinkDao {

    @Override
    public Long countBetween(Date startDate, Date endDate) {
        Session session = getSession();
        java.sql.Date start = new java.sql.Date(startDate.getTime());
        java.sql.Date end = new java.sql.Date(endDate.getTime());
        Query query = session.createQuery(
                "select count(*) " +
                        "from MessageLink where date between :startDate " +
                        "AND :endDate");
        query.setDate("startDate", start);
        query.setDate("endDate", end);
        return (Long) query.uniqueResult();
    }
}
