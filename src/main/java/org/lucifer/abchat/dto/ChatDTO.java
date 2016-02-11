package org.lucifer.abchat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by PiCy on 2/10/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatDTO {
    private Long chatId;
    private Long userId;

    public ChatDTO() {

    }

    public ChatDTO(Long chatId, Long userId) {
        this.chatId = chatId;
        this.userId = userId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
