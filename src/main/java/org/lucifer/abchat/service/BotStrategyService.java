package org.lucifer.abchat.service;


import org.lucifer.abchat.domain.BotStrategy;
import org.lucifer.abchat.domain.StdMessage;
import org.lucifer.abchat.dto.MessageDTO;

import java.util.List;

public interface BotStrategyService extends BaseService<BotStrategy> {
    List<MessageDTO> messages(Long id);

    MessageDTO addMessage(Long id, StdMessage message);

    MessageDTO message(Long id, Long msgId);

    MessageDTO deleteMessage(Long id);

    BotStrategy getRandomStrategy();
}
