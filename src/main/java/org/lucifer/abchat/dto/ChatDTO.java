package org.lucifer.abchat.dto;

/**
 * Created by PiCy on 2/10/2016.
 */
public class ChatDTO {
    private Long chatId;
    private Long userId;
    private Long cospeakerId;

    public ChatDTO() {

    }

    public ChatDTO(Long chatId, Long userId, Long cospeakerId) {
        this.chatId = chatId;
        this.userId = userId;
        this.cospeakerId = cospeakerId;
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

    public Long getCospeakerId() {
        return cospeakerId;
    }

    public void setCospeakerId(Long cospeakerId) {
        this.cospeakerId = cospeakerId;
    }
}
