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

@Service
@Transactional
public class ChatServiceImpl extends BaseServiceImpl<Chat> implements ChatService {
    @Autowired
    CospeakerDao cospeakerDao;

    @Autowired
    MessageDao messageDao;

    @Autowired
    PersonService personService;

    public ChatDTO enter(UserDTO usr) {
        ChatDao dao = (ChatDao) this.dao;
        List<Long> free = dao.freeRooms();
        User user = personService.findByLogin(usr.getLogin());
        Cospeaker userCospeaker = new Cospeaker(user, null);

        if (Math.random() > 0.5) {
            Chat chat = new Chat();
            userCospeaker.setChat(chat);
            Bot bot = new Bot();
            Cospeaker botCospeaker = new Cospeaker(null, bot);
            botCospeaker.setChat(chat);
            cospeakerDao.save(userCospeaker);
            cospeakerDao.save(botCospeaker);
            return new ChatDTO(chat.getId(), user.getId());
        }

        if (free.size() != 0) {
            Long randomRoomId = free.get((int)(Math.random() * free.size()));
            Chat randomRoom = dao.findById(randomRoomId);
            userCospeaker.setChat(randomRoom);
            cospeakerDao.save(userCospeaker);
            return new ChatDTO(randomRoom.getId(), user.getId());
        }

        Chat chat = new Chat();
        userCospeaker.setChat(chat);
        cospeakerDao.save(userCospeaker);
        return new ChatDTO(chat.getId(), user.getId());
    }

}
