package org.lucifer.abchat.controller;

import org.lucifer.abchat.domain.User;
import org.lucifer.abchat.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    PersonService personService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public
    @ResponseBody
    String register(User user) {
        return personService.register(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    @ResponseBody
    String login(User user) {
        return personService.logIn(user);
    }
}
