package org.lucifer.abchat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by PiCy on 2/10/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatDTO {
    private Long chatId;
    private String userLogin;

    public ChatDTO() {

    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}
