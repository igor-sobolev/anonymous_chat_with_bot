package org.lucifer.abchat.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MESSAGE")
public class Message extends Identificator implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    private Chat chat;

    @Column(name = "MESSAGE")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cospeaker source;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cospeaker target;
    
    public Message() {

    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Cospeaker getSource() {
        return source;
    }

    public void setSource(Cospeaker source) {
        this.source = source;
    }

    public Cospeaker getTarget() {
        return target;
    }

    public void setTarget(Cospeaker target) {
        this.target = target;
    }
}
