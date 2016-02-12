package org.lucifer.abchat.service;

import org.lucifer.abchat.dao.ChatDao;
import org.lucifer.abchat.dao.CospeakerDao;
import org.lucifer.abchat.dao.MessageDao;
import org.lucifer.abchat.domain.Bot;
import org.lucifer.abchat.domain.Chat;
import org.lucifer.abchat.domain.Cospeaker;
import org.lucifer.abchat.domain.User;
import org.lucifer.abchat.dto.ChatDTO;
import org.lucifer.abchat.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ChatServiceImpl extends BaseServiceImpl<Chat> implements ChatService {
    @Autowired
    CospeakerDao cospeakerDao;

    @Autowired
    MessageDao messageDao;

    @Autowired
    PersonService personService;

    public Cospeaker enter(UserDTO usr) {
        ChatDao dao = (ChatDao) this.dao;
        List<Long> free = dao.freeRooms();                                      //id of free rooms (1 cospeaker)
        User user = personService.findByLogin(usr.getLogin());
        Cospeaker userCospeaker = new Cospeaker(user, null);                    //cospeaker for entering user

        if (Math.random() > 0.5) {                                              //give bot for user
            Chat chat = new Chat();
            userCospeaker.setChat(chat);
            Bot bot = new Bot();
            Cospeaker botCospeaker = new Cospeaker(null, bot);
            botCospeaker.setChat(chat);
            cospeakerDao.save(userCospeaker);
            cospeakerDao.save(botCospeaker);
            return userCospeaker;
        }
        boolean oneInChat = true;                                               //if user only 1 in free chatrooms create new
        for (Long fid : free) {
            Chat room = dao.findById(fid);
            Set<Cospeaker> cospeakerSet = room.getCospeakers();
            for (Cospeaker c : cospeakerSet) {
                if (!c.equals(userCospeaker)) {
                    oneInChat = false;
                }
            }
        }
        if (free.size() != 0 && !oneInChat) {
            Chat randomRoom;
            do {
                Long randomRoomId = free.get((int) (Math.random() * free.size()));
                randomRoom = dao.findById(randomRoomId);
            } while (randomRoom.getCospeakers().contains(userCospeaker));
            userCospeaker.setChat(randomRoom);
            cospeakerDao.save(userCospeaker);
            return userCospeaker;
        }

        Chat chat = new Chat();
        userCospeaker.setChat(chat);
        cospeakerDao.save(userCospeaker);
        return userCospeaker;
    }

    public String cospeakerEntered(ChatDTO c) {
        ChatDao dao = (ChatDao) this.dao;
        Chat chat = dao.findById(c.getChatId());
        if (chat.getCospeakers().size() == 2) {
            return "Ok";
        }
        return "No";
    }

    public String online(ChatDTO ch) {
        User user = personService.findByLogin(ch.getUserLogin());
        Chat chat = dao.findById(ch.getChatId());
        Set<Cospeaker> cospeakers = chat.getCospeakers();
        for (Cospeaker csp : cospeakers) {
            if (user.equals(csp.getUser())) {
                csp.setOnline(true);
                cospeakerDao.save(csp);
            } else {
                if (csp.getUser() == null) {
                    return "Online";
                }
                if (csp.getOnline().equals(false)) {
                    return "Offline";
                }
                csp.setOnline(false);
                cospeakerDao.save(csp);
            }
        }
        return "Online";
    }

}
