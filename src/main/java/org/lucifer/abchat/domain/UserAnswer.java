package org.lucifer.abchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.persistence.criteria.Fetch;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "USER_ANSWER")
public class UserAnswer extends Identificator implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private Chat chat;

    @Column(name = "ANSWER")
    private Boolean answer;

    @Column(name = "DATE")
    @Temporal(value=TemporalType.DATE)
    private Date date;

    public UserAnswer() {
        this.date = new Date();
    }

    public UserAnswer(User user, Chat chat, Boolean answer) {
        this();
        this.user = user;
        this.chat = chat;
        this.answer = answer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }
}
