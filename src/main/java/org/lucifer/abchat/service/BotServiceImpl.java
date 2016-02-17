package org.lucifer.abchat.service;

import org.lucifer.abchat.dao.MessageLinkDao;
import org.lucifer.abchat.domain.*;
import org.lucifer.abchat.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by PiCy on 2/16/2016.
 */
@Service
@Transactional
public class BotServiceImpl extends BaseServiceImpl<Bot> implements BotService {
    @Autowired
    MessageLinkDao messageLinkDao;

    @Autowired
    ChatService chatService;

    public void remember(String stimulus, String message) {
        MessageLink ml = new MessageLink(stimulus, message);
        messageLinkDao.save(ml);
    }

    public Message message(MessageDTO msg) {
        List<MessageLink> list = messageLinkDao.getAll();
        Chat chat = chatService.findById(msg.getChatId());
        Set<Cospeaker> cospeakers = chat.getCospeakers();
        Cospeaker source = null, target = null;
        for (Cospeaker csp : cospeakers) {
            if (csp.getBot() != null) {
                source = csp;
            } else {
                target = csp;
            }
        }
        if (Math.random() < 0.15) return null;
        Message m = new Message();
        m.setChat(chat);
        m.setSource(source);
        m.setTarget(target);
        if (list.size() == 0) {
            m.setMessage("Oops");
            return m;
        } else {                                                                //TODO not random
            m.setMessage(list.get((int) (Math.random() * list.size())).getMessage());
            return m;
        }
    }
}
