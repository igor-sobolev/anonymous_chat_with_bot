package org.lucifer.abchat.controller;

import org.lucifer.abchat.domain.User;
import org.lucifer.abchat.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Игорь on 19.06.2015.
 */
@Controller
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    PersonService personService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody String register(User user) {
        if (!personService.register(user)) {
            return "Error";
        }
        personService.save(user);
        return "Ok";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody String login(User user) {
        if (personService.logIn(user)) {
            return "Ok";
        }
        return "Error";
    }
}
