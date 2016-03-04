package org.lucifer.abchat.service.impl;

import org.lucifer.abchat.domain.BotStrategy;
import org.lucifer.abchat.domain.StdMessage;
import org.lucifer.abchat.dto.MessageDTO;
import org.lucifer.abchat.service.BotStrategyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class BotStrategyServiceImpl extends BaseServiceImpl<BotStrategy> implements BotStrategyService {
    public Set<MessageDTO> messages(Long id) {
        BotStrategy strategy = dao.findById(id);
        Set<MessageDTO> result = new HashSet<MessageDTO>();
        for (StdMessage message : strategy.getMessages()) {
            MessageDTO msg = new MessageDTO();
            msg.setId(message.getId());
            msg.setMessage(message.getMessage());
            result.add(msg);
        }
        return result;
    }
}
