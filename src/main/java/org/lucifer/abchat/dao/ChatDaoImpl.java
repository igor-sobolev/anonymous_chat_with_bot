package org.lucifer.abchat.dao;

import org.lucifer.abchat.domain.Chat;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Created by PiCy on 2/9/2016.
 */
@Component
public class ChatDaoImpl extends BaseDaoImpl<Chat> implements ChatDao{
    public List<Chat> freeRooms() {
        return Collections.emptyList();// TODO free rooms
    }
}
