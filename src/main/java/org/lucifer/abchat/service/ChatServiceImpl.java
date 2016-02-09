package org.lucifer.abchat.service;

import org.lucifer.abchat.dao.ChatDao;
import org.lucifer.abchat.domain.Bot;
import org.lucifer.abchat.domain.Chat;
import org.lucifer.abchat.domain.Cospeaker;
import org.lucifer.abchat.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * Created by PiCy on 2/9/2016.
 */

@Service
@Transactional
public class ChatServiceImpl extends BaseServiceImpl<Chat> implements ChatService {
    public Chat enter(User user) {
        Random r = new Random();                                                //TODO logic
        try {
            Thread.sleep(r.nextInt(3) * 1000);                                  //random delay for imitation real situation
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ChatDao dao = (ChatDao) this.dao;
        List<Chat> free = dao.freeRooms();
        int cospeaker = r.nextInt(2);                                           //bot(0) or human(1)
        Cospeaker u = new Cospeaker(user, null);
        switch (cospeaker) {
            case 0:
                Cospeaker b = new Cospeaker(null, new Bot());
                Chat chat = new Chat(u, b);
                return dao.save(chat);
            case 1:
                for (Chat c : free) {
                    if (c.getCospeaker1() == null) {
                        c.setCospeaker2(u);
                        return dao.save(c);
                    } else {
                        c.setCospeaker1(u);
                        return dao.save(c);
                    }
                }
                if (free.size() == 0) {                                         //if no free room create new
                    Chat newChat = new Chat(u, null);
                    return dao.save(newChat);
                }
                break;
        }
        return null;
    }

}
