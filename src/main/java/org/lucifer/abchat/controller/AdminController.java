package org.lucifer.abchat.controller;

import org.lucifer.abchat.domain.User;
import org.lucifer.abchat.dto.AppGlobalDTO;
import org.lucifer.abchat.dto.MailDTO;
import org.lucifer.abchat.dto.StatisticsDTO;
import org.lucifer.abchat.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(method = RequestMethod.GET)
    public String admin() {
        return "admin";
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public @ResponseBody
    AppGlobalDTO getConfig() {
        return adminService.getConfig();
    }

    @RequestMapping(value = "/config", method = RequestMethod.POST)
    public @ResponseBody
    AppGlobalDTO setConfig(AppGlobalDTO dto) {
        return adminService.setConfig(dto);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    @ResponseBody
    boolean logIn(User user) {
        return adminService.logInAdmin(user);
    }

    @RequestMapping(value = "/mail", method = RequestMethod.POST)
    public
    @ResponseBody
    MailDTO broadMail(MailDTO mail) {
        return adminService.broadMail(mail);
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.POST)
    public
    @ResponseBody
    StatisticsDTO statistics(StatisticsDTO dto) {
        return adminService.statistics(dto);
    }
}
