package org.lucifer.abchat.service;

import org.lucifer.abchat.domain.Bot;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by PiCy on 2/16/2016.
 */
@Service
@Transactional
public class BotServiceImpl extends BaseServiceImpl<Bot> implements BotService{
}
