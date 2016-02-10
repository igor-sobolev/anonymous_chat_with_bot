package org.lucifer.abchat.service;

import org.lucifer.abchat.domain.Chat;
import org.lucifer.abchat.domain.Cospeaker;
import org.lucifer.abchat.domain.User;

/**
 * Created by PiCy on 2/10/2016.
 */
public interface CospeakerService extends BaseService<Cospeaker> {
    Cospeaker bind(Chat chat, User user);
}
