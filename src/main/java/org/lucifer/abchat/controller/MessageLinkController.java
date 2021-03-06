package org.lucifer.abchat.controller;

import org.lucifer.abchat.domain.MessageLink;
import org.lucifer.abchat.service.MessageLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/mLink")
public class MessageLinkController {
    @Autowired
    private MessageLinkService messageLinkService;

    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<MessageLink> mlPage(@PathVariable(value = "page")Long page) {
        return messageLinkService.getPage(page);
    }

    @RequestMapping(value = "/page/count", method = RequestMethod.GET)
    public
    @ResponseBody
    Long pageCount() {
        return messageLinkService.countPages();
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public
    @ResponseBody
    Long mlCount() {
        return messageLinkService.count();
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<MessageLink> getAll() {
        return messageLinkService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    MessageLink get(@PathVariable(value = "id") Long id) {
        return messageLinkService.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    MessageLink delete(@PathVariable(value = "id") Long id) {
        return messageLinkService.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    MessageLink save(MessageLink messageLink) {
        return messageLinkService.save(messageLink);
    }
}
