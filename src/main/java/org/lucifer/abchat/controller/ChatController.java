package org.lucifer.abchat.controller;

import org.lucifer.abchat.domain.Chat;
import org.lucifer.abchat.domain.User;
import org.lucifer.abchat.service.ChatService;
import org.lucifer.abchat.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping(value = "/enter", method = RequestMethod.POST)
    public
    @ResponseBody
    Chat enter(@RequestBody String userLogin) {                         //TODO fucking =
        User user = personService.findByLogin(userLogin);
        return chatService.enter(user);
    }
}
