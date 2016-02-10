package org.lucifer.abchat.controller;

import org.lucifer.abchat.domain.Chat;
import org.lucifer.abchat.domain.Cospeaker;
import org.lucifer.abchat.domain.User;
import org.lucifer.abchat.dto.ChatDTO;
import org.lucifer.abchat.dto.UserDTO;
import org.lucifer.abchat.service.ChatService;
import org.lucifer.abchat.service.CospeakerService;
import org.lucifer.abchat.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by PiCy on 2/9/2016.
 */
@Controller
@RequestMapping(value = "/chat")
public class ChatController {
    @Autowired
    ChatService chatService;

    @Autowired
    PersonService personService;

    @Autowired
    CospeakerService cospeakerService;

    @RequestMapping(value = "/enter", method = RequestMethod.POST)
    public
    @ResponseBody
    ChatDTO enter(UserDTO usr) {
        User user = personService.findByLogin(usr.getLogin());
        Chat chat = chatService.getFreeRoom();
        Cospeaker cospeaker = cospeakerService.bind(chat, user);
        return new ChatDTO(chat.getId(), user.getId(), cospeaker.getId());
    }
}
