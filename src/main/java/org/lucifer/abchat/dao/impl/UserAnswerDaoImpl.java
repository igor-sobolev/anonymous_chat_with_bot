package org.lucifer.abchat.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.lucifer.abchat.dao.UserAnswerDao;
import org.lucifer.abchat.domain.UserAnswer;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserAnswerDaoImpl extends BaseDaoImpl<UserAnswer> implements UserAnswerDao {
    @Override
    public Long correctAnswers(Date startDate, Date endDate) {
        Session session = getSession();
        java.sql.Date start = new java.sql.Date(startDate.getTime());
        java.sql.Date end = new java.sql.Date(endDate.getTime());
        Query query = session.createQuery(
                "select count(*) " +
                        "from UserAnswer where date between :startDate " +
                        "AND :endDate and answer=true");
        query.setDate("startDate", start);
        query.setDate("endDate", end);
        return (Long) query.uniqueResult();
    }

    @Override
    public Long countBetween(Date startDate, Date endDate) {
        Session session = getSession();
        java.sql.Date start = new java.sql.Date(startDate.getTime());
        java.sql.Date end = new java.sql.Date(endDate.getTime());
        Query query = session.createQuery(
                "select count(*) " +
                        "from UserAnswer where date between :startDate " +
                        "AND :endDate");
        query.setDate("startDate", start);
        query.setDate("endDate", end);
        return (Long) query.uniqueResult();
    }

    @Override
    public Long countBotChats(Date startDate, Date endDate) {
        Session session = getSession();
        java.sql.Date start = new java.sql.Date(startDate.getTime());
        java.sql.Date end = new java.sql.Date(endDate.getTime());
        Query query = session.createQuery(
                "select count(*) " +
                        "from UserAnswer where date between :startDate " +
                        "AND :endDate and chat in (" +
                        "select chat from Cospeaker where bot is not null" +
                        ")");
        query.setDate("startDate", start);
        query.setDate("endDate", end);
        return (Long) query.uniqueResult();
    }

    @Override
    public Long countFooledByBot(Date startDate, Date endDate) {
        Session session = getSession();
        java.sql.Date start = new java.sql.Date(startDate.getTime());
        java.sql.Date end = new java.sql.Date(endDate.getTime());
        Query query = session.createQuery(
                "select count(*) " +
                        "from UserAnswer where date between :startDate " +
                        "AND :endDate and answer=false and chat in (" +
                        "select chat from Cospeaker where bot is not null" +
                        ")");
        query.setDate("startDate", start);
        query.setDate("endDate", end);
        return (Long) query.uniqueResult();
    }
}
