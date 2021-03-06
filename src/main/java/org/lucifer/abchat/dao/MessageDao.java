package org.lucifer.abchat.dao;

import org.lucifer.abchat.domain.Message;

import java.util.Date;

public interface MessageDao extends BaseDao<Message> {
    Long countBetween(Date startDate, Date endDate);
}
