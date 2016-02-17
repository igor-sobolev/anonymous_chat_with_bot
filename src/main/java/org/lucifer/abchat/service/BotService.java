package org.lucifer.abchat.service;

import org.lucifer.abchat.domain.Bot;
import org.lucifer.abchat.domain.Message;
import org.lucifer.abchat.dto.MessageDTO;

public interface BotService extends BaseService<Bot> {
    void remember(String stimulus, String message);

    Message message(MessageDTO msg);
}
