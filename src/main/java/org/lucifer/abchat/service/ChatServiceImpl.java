package org.lucifer.abchat.service;

import org.lucifer.abchat.dao.ChatDao;
import org.lucifer.abchat.domain.Chat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by PiCy on 2/9/2016.
 */

@Service
@Transactional
public class ChatServiceImpl extends BaseServiceImpl<Chat> implements ChatService {
    public Chat getFreeRoom() {
        ChatDao dao = (ChatDao) this.dao;
        List<Integer> free = dao.freeRooms();
        if (free.size() != 0) {
            Integer randomRoomId = free.get((int)(Math.random() * free.size()));
            Chat randomRoom = dao.findById(randomRoomId.longValue());
            return randomRoom;
        }
        Chat chat = new Chat();
        return dao.save(chat);
    }

}
