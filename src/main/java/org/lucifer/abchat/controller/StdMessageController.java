package org.lucifer.abchat.controller;

import org.lucifer.abchat.service.StdMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class StdMessageController {
    @Autowired
    private StdMessageService stdMessageService;


}
