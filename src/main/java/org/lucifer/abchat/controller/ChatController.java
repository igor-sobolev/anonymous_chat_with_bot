package org.lucifer.abchat.controller;

import org.lucifer.abchat.domain.Cospeaker;
import org.lucifer.abchat.dto.ChatDTO;
import org.lucifer.abchat.dto.MessageDTO;
import org.lucifer.abchat.dto.UserDTO;
import org.lucifer.abchat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @RequestMapping(value = "/enter", method = RequestMethod.POST)
    public
    @ResponseBody
    Cospeaker enter(UserDTO usr) {
        return chatService.enter(usr);
    }

    @RequestMapping(value = "/cospeaker/entered", method = RequestMethod.POST)
    public
    @ResponseBody
    boolean cospeakerEntered(ChatDTO chat) {
        return chatService.cospeakerEntered(chat);
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public
    @ResponseBody
    void message(MessageDTO msg) {
        chatService.message(msg);
    }

    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public
    @ResponseBody
    List<MessageDTO> receive(ChatDTO ch) {
        return chatService.receive(ch);
    }

    @RequestMapping(value = "/bot", method = RequestMethod.POST)
    public
    @ResponseBody
    boolean bot(ChatDTO ch) {
        return chatService.bot(ch);
    }

    @RequestMapping(value = "/nobot", method = RequestMethod.POST)
    public
    @ResponseBody
    boolean noBot(ChatDTO ch) {
        return chatService.noBot(ch);
    }
}
