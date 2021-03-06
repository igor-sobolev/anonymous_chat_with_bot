package org.lucifer.abchat.service.impl;

import org.lucifer.abchat.config.AppGlobal;
import org.lucifer.abchat.domain.User;
import org.lucifer.abchat.dto.AppGlobalDTO;
import org.lucifer.abchat.dto.MailDTO;
import org.lucifer.abchat.dto.StatisticsDTO;
import org.lucifer.abchat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageLinkService messageLinkService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private MailService mailService;

    @Override
    public boolean logInAdmin(User user) {
        return userService.logInAdmin(user);
    }

    @Override
    public AppGlobalDTO getConfig() {
        return new AppGlobalDTO();
    }

    @Override
    public AppGlobalDTO setConfig(AppGlobalDTO dto) {
        AppGlobal.setAddBotAfter(dto.isAddBotAfter());
        AppGlobal.setBotProbability(dto.getBotProbability() / 100);
        AppGlobal.setPointsLoose(dto.getPointsLoose());
        AppGlobal.setPointsWin(dto.getPointsWin());
        AppGlobal.setRetries(dto.getRetries());
        return new AppGlobalDTO();
    }

    @Override
    public MailDTO broadMail(MailDTO mail) {
        int sended = 0;
        for (int i = 0; i < userService.countPages(); i++) {
            for (User user : userService.getPage((long) i)) {
                if (mailService.validateEmail(user.getEmail())) {
                    mailService.send(user.getEmail(), mail.getSubject(),
                            mail.getMessage());
                    sended++;
                }
            }
        }
        mail.setCopies(sended);
        return mail;
    }

    @Override
    public StatisticsDTO statistics(StatisticsDTO dto) {
        Long userCount = userService.count();
        Long registered = userService.countBetween(dto.getStartDate(),
                dto.getEndDate());
        Long activity = userService.countActive(dto.getStartDate(),
                dto.getEndDate());
        Long messages = chatService.countMessages(dto.getStartDate(),
                dto.getEndDate());
        Integer correctPercentage = chatService
                .countCorrectAnswersPercentage(dto.getStartDate(),
                        dto.getEndDate());
        Integer fooledByBotPercentage = chatService
                .countFooledByBotPercentage(dto.getStartDate(),
                        dto.getEndDate());
        Long sizeOfCrowdSource = messageLinkService.count();
        Long newRecords = messageLinkService
                .countBetween(dto.getStartDate(), dto.getEndDate());
        Long botChats = chatService.countBotChats(dto.getStartDate(),
                dto.getEndDate());
        Long chats = chatService.countChats(dto.getStartDate(),
                dto.getEndDate());
        dto.setUsersCount(userCount);
        dto.setRegistered(registered);
        dto.setUserActivity(activity);
        dto.setMessagesCount(messages);
        dto.setCorrectPercentage(correctPercentage);
        dto.setFooledByBotPercentage(fooledByBotPercentage);
        dto.setSizeOfCrowdSource(sizeOfCrowdSource);
        dto.setNewCrowdSourceRecords(newRecords);
        dto.setBotChatsCount(botChats);
        dto.setChatCount(chats);
        return dto;
    }
}
