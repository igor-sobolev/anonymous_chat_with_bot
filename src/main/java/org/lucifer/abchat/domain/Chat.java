package org.lucifer.abchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by PiCy on 2/8/2016.
 */

@Entity
@Table(name = "CHAT")
public class Chat extends Identificator implements Serializable {
    @OneToOne(mappedBy = "chat")
    private Cospeaker cospeaker1;

    @OneToOne(mappedBy = "chat")
    private Cospeaker cospeaker2;

    @OneToOne(mappedBy = "chat")
    private UserAnswer answer;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chat")
    private Set<Message> messages;

    public Chat() {

    }

    public Chat(Cospeaker sp1, Cospeaker sp2) {
        cospeaker1 = sp1;
        cospeaker2 = sp2;
    }

    public Cospeaker getCospeaker1() {
        return cospeaker1;
    }

    public void setCospeaker1(Cospeaker cospeaker1) {
        this.cospeaker1 = cospeaker1;
    }

    public Cospeaker getCospeaker2() {
        return cospeaker2;
    }

    public void setCospeaker2(Cospeaker cospeaker2) {
        this.cospeaker2 = cospeaker2;
    }

    public UserAnswer getAnswer() {
        return answer;
    }

    public void setAnswer(UserAnswer answer) {
        this.answer = answer;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
