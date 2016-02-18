package org.lucifer.abchat.controller;

import org.lucifer.abchat.domain.User;
import org.lucifer.abchat.dto.UserDTO;
import org.lucifer.abchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/get/{login}", method = RequestMethod.GET)
    public
    @ResponseBody
    UserDTO getUser(@PathVariable(value = "login") String login) {
        return userService.get(login);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public
    @ResponseBody
    boolean register(User user) {
        return userService.register(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    @ResponseBody
    UserDTO login(User user) {
        return userService.logIn(user);
    }

    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public
    @ResponseBody
    List<User> getTop() {
        return userService.top();
    }
}
