package org.lucifer.abchat.service;

import org.lucifer.abchat.domain.Chat;

/**
 * Created by PiCy on 2/9/2016.
 */
public interface ChatService extends  BaseService<Chat> {
    Chat getFreeRoom();
}
