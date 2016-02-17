package org.lucifer.abchat.service;

import org.lucifer.abchat.domain.Chat;
import org.lucifer.abchat.domain.Cospeaker;
import org.lucifer.abchat.dto.ChatDTO;
import org.lucifer.abchat.dto.MessageDTO;
import org.lucifer.abchat.dto.UserDTO;

import java.util.List;

public interface ChatService extends  BaseService<Chat> {
    Cospeaker enter(UserDTO usr);

    String cospeakerEntered(ChatDTO chat);

    String message(MessageDTO msg);

    List<MessageDTO> receive(ChatDTO ch);

    String bot(ChatDTO ch);

    String nobot(ChatDTO ch);
}
