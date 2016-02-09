package org.lucifer.abchat.dao;

import org.lucifer.abchat.domain.Chat;

import java.util.List;

/**
 * Created by PiCy on 2/9/2016.
 */
public interface ChatDao extends BaseDao<Chat> {
    List<Chat> freeRooms();
}
