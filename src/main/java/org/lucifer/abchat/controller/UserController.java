package org.lucifer.abchat.controller;

import org.lucifer.abchat.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Игорь on 19.06.2015.
 */
@Controller
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    PersonService personService;

}
