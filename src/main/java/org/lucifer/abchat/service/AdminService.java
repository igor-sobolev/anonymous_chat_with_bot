package org.lucifer.abchat.service;


import org.lucifer.abchat.domain.User;
import org.lucifer.abchat.dto.AppGlobalDTO;
import org.lucifer.abchat.dto.MailDTO;
import org.lucifer.abchat.dto.StatisticsDTO;

public interface AdminService {
    boolean logInAdmin(User user);

    AppGlobalDTO getConfig();

    AppGlobalDTO setConfig(AppGlobalDTO dto);

    MailDTO broadMail(MailDTO mail);

    StatisticsDTO statistics(StatisticsDTO dto);
}
