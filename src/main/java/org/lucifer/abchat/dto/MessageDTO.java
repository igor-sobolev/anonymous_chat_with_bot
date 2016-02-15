package org.lucifer.abchat.dto;

/**
 * Created by PiCy on 2/15/2016.
 */
public class MessageDTO {
    private Long id;
    private Long chatId;
    private String userLogin;
    private String stimulus;
    private String message;

    public MessageDTO() {

    }

    public Long getChatId() {
        return chatId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStimulus() {
        return stimulus;
    }

    public void setStimulus(String stimulus) {
        this.stimulus = stimulus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
