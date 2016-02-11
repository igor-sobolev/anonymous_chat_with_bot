package org.lucifer.abchat.service;

import org.lucifer.abchat.domain.Chat;
import org.lucifer.abchat.dto.ChatDTO;
import org.lucifer.abchat.dto.UserDTO;

/**
 * Created by PiCy on 2/9/2016.
 */
public interface ChatService extends  BaseService<Chat> {
    ChatDTO enter(UserDTO usr);
}
