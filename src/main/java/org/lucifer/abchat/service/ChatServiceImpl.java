package org.lucifer.abchat.service;

import org.lucifer.abchat.dao.ChatDao;
import org.lucifer.abchat.dao.CospeakerDao;
import org.lucifer.abchat.dao.MessageDao;
import org.lucifer.abchat.dao.UserAnswerDao;
import org.lucifer.abchat.domain.*;
import org.lucifer.abchat.dto.ChatDTO;
import org.lucifer.abchat.dto.MessageDTO;
import org.lucifer.abchat.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    UserAnswerDao userAnswerDao;

    @Autowired
    UserService userService;

    @Autowired
    BotService botService;

    public Cospeaker enter(UserDTO usr) {
        ChatDao dao = (ChatDao) this.dao;
        List<Long> free = dao.freeRooms();                                      //id of free rooms (1 cospeaker)
        User user = userService.findByLogin(usr.getLogin());
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

        Chat chat = new Chat();                                                 //new chatroom for only users
        userCospeaker.setChat(chat);
        cospeakerDao.save(userCospeaker);
        return userCospeaker;
    }

    public String cospeakerEntered(ChatDTO c) {
        ChatDao dao = (ChatDao) this.dao;
        Chat chat = dao.findById(c.getChatId());
        if (chat.getCospeakers().size() == 2) {
            return "Ok";                                                        //users can chat now
        }
        return "No";
    }

    public String message(MessageDTO msg) {
        ChatDao dao = (ChatDao) this.dao;
        Chat chat = dao.findById(msg.getChatId());
        Set<Cospeaker> cospeakers = chat.getCospeakers();
        Cospeaker source = null, target = null;
        for (Cospeaker csp : cospeakers) {                                      //find target and source for msg
            if (csp.getBot() == null) {
                if (csp.getUser().getLogin().equals(msg.getUserLogin())) {
                    source = csp;
                } else {
                    target = csp;
                }
            } else {
                target = csp;
            }
        }
        Message message = new Message();
        message.setChat(chat);
        message.setSource(source);
        message.setTarget(target);
        message.setMessage(msg.getMessage());
        messageDao.save(message);                                               //message is ready

        if (bothUsers(chat)) {
            botService.remember(msg.getStimulus(), msg.getMessage());
        } else {
            Message botMessage = botService.message(msg);
            if (botMessage != null) messageDao.save(botMessage);
        }
        return "Ok";
    }

    public List<MessageDTO> receive(ChatDTO ch) {                               //return list of message for user
        ChatDao dao = (ChatDao) this.dao;
        Chat chat = dao.findById(ch.getChatId());
        Set<Cospeaker> cospeakers = chat.getCospeakers();
        Set<Message> messages = null;
        for(Cospeaker csp : cospeakers) {
            if (csp.getBot() == null) {
                if (csp.getUser().getLogin().equals(ch.getUserLogin())) {
                    messages = csp.getReceived();
                }
            }
        }
        List <MessageDTO> list = new ArrayList<MessageDTO>();                   //place messages in DTO object and send to user
        for(Message m : messages) {
            if (m.getId() > ch.getMaxMessageId()) {
                MessageDTO md = new MessageDTO();
                md.setMessage(m.getMessage());
                md.setId(m.getId());
                list.add(md);
            }
        }
        return list;
    }

    public String bot(ChatDTO ch) {
        Chat chat = dao.findById(ch.getChatId());
        User user = userService.findByLogin(ch.getUserLogin());
        UserAnswer answer = new UserAnswer();
        answer.setChat(chat);
        answer.setUser(user);
        if (bothUsers(chat)) {
            answer.setAnswer(false);
            userAnswerDao.save(answer);
            userService.recountScore(user);
            return "No";
        }
        answer.setAnswer(true);
        userAnswerDao.save(answer);
        userService.recountScore(user);
        return "Yes";
    }

    public String nobot(ChatDTO ch) {
        Chat chat = dao.findById(ch.getChatId());
        User user = userService.findByLogin(ch.getUserLogin());
        UserAnswer answer = new UserAnswer();
        answer.setChat(chat);
        answer.setUser(user);
        if (bothUsers(chat)) {
            answer.setAnswer(true);
            userAnswerDao.save(answer);
            userService.recountScore(user);
            return "Yes";
        }
        answer.setAnswer(false);
        userAnswerDao.save(answer);
        userService.recountScore(user);
        return "No";
    }

    protected boolean bothUsers(Chat c) {
        if (c.getCospeakers().size() < 2) return false;
        Set<Cospeaker> cospeakers = c.getCospeakers();
        boolean both = true;
        for (Cospeaker csp : cospeakers) {
            if (csp.getUser() == null) {
                both = false;
            }
        }
        return both;
    }
}
