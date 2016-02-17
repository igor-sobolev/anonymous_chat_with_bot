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
    @JsonIgnore
    @OneToMany(mappedBy = "chat")
    private Set<Cospeaker> cospeakers;

    @JsonIgnore
    @OneToMany(mappedBy = "chat")
    private Set<UserAnswer> answers;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chat")
    private Set<Message> messages;

    public Chat() {

    }

    public Set<Cospeaker> getCospeakers() {
        return cospeakers;
    }

    public void setCospeakers(Set<Cospeaker> cospeakers) {
        this.cospeakers = cospeakers;
    }

    public Set<UserAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<UserAnswer> answers) {
        this.answers = answers;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
