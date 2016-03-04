package org.lucifer.abchat.service;


import org.lucifer.abchat.domain.BotStrategy;
import org.lucifer.abchat.dto.MessageDTO;

import java.util.Set;

public interface BotStrategyService extends BaseService<BotStrategy> {
    Set<MessageDTO> messages(Long id);
}
