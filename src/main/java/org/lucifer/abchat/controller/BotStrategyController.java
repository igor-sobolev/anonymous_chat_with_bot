package org.lucifer.abchat.controller;

import org.lucifer.abchat.domain.BotStrategy;
import org.lucifer.abchat.dto.MessageDTO;
import org.lucifer.abchat.service.BotStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/bot/strategy")
public class BotStrategyController {
    @Autowired
    private BotStrategyService botStrategyService;

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<BotStrategy> getAll() {
        return botStrategyService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    BotStrategy get(@PathVariable(value = "id") Long id) {
        return botStrategyService.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    BotStrategy delete(@PathVariable(value = "id") Long id) {
        return botStrategyService.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    BotStrategy save(BotStrategy botStrategy) {
        return botStrategyService.save(botStrategy);
    }

    @RequestMapping(value = "messages/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Set<MessageDTO> messages(@PathVariable(value = "id") Long id) {
        return botStrategyService.messages(id);
    }
}
