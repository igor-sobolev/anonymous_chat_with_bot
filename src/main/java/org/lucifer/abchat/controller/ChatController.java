package org.lucifer.abchat.controller;

import org.lucifer.abchat.domain.Cospeaker;
import org.lucifer.abchat.dto.ChatDTO;
import org.lucifer.abchat.dto.UserDTO;
import org.lucifer.abchat.service.ChatService;
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

    @RequestMapping(value = "/enter", method = RequestMethod.POST)
    public
    @ResponseBody
    Cospeaker enter(UserDTO usr) {
        return chatService.enter(usr);
    }

    @RequestMapping(value = "/cospeaker_entered", method = RequestMethod.POST)
    public
    @ResponseBody
    String cospeakerEntered(ChatDTO chat) {
        return chatService.cospeakerEntered(chat);
    }

    @RequestMapping(value = "/online", method = RequestMethod.POST)
    public
    @ResponseBody
    String online(ChatDTO chat) {
        return chatService.online(chat);
    }
}
