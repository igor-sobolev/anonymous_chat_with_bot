package org.lucifer.abchat.dao;

import org.lucifer.abchat.domain.Chat;

import java.util.List;

public interface ChatDao extends BaseDao<Chat> {
    List<Long> freeRooms();
}
