package org.lucifer.abchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "MESSAGE")
public class Message extends Identificator implements Serializable {
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Chat chat;

    @Column(name = "MESSAGE")
    private String message;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Cospeaker source;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Cospeaker target;

    @Column(name = "DATE")
    @Temporal(value=TemporalType.DATE)
    private Date date;
    
    public Message() {
        this.date = new Date();
    }

    public Message(Chat chat, String message, Cospeaker source, Cospeaker target) {
        this();
        this.chat = chat;
        this.message = message;
        this.source = source;
        this.target = target;
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
