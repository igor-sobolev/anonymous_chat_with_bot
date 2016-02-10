package org.lucifer.abchat.service;

import org.lucifer.abchat.domain.Chat;
import org.lucifer.abchat.domain.Cospeaker;
import org.lucifer.abchat.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by PiCy on 2/10/2016.
 */

@Service
@Transactional
public class CospeakerServiceImpl extends BaseServiceImpl<Cospeaker> implements CospeakerService{

    public Cospeaker bind(Chat chat, User user) {
        //TODO realize
        return null;
    }
}
