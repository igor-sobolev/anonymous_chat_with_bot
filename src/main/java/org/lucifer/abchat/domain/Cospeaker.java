package org.lucifer.abchat.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by PiCy on 2/8/2016.
 */


@Entity
@Table(name = "COSPEAKER")
public class Cospeaker extends Identificator implements Serializable {
    @OneToOne
    private User user;

    @OneToOne
    private Bot bot;

    @OneToOne
    private Chat chat;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "source")
    private Set<Message> sended;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "target")
    private Set<Message> received;

    public Cospeaker() {

    }

    public Cospeaker(User user, Bot bot) {
        this.user = user;
        this.bot = bot;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Set<Message> getSended() {
        return sended;
    }

    public void setSended(Set<Message> sended) {
        this.sended = sended;
    }

    public Set<Message> getReceived() {
        return received;
    }

    public void setReceived(Set<Message> received) {
        this.received = received;
    }
}
