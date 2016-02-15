package org.lucifer.abchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by PiCy on 2/8/2016.
 */


@Entity
@Table(name = "COSPEAKER")
public class Cospeaker extends Identificator implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private Bot bot;

    @ManyToOne(cascade = CascadeType.ALL)
    private Chat chat;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "source")
    private Set<Message> sended;

    @JsonIgnore
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cospeaker cospeaker = (Cospeaker) o;

        return !(user != null ? !user.equals(cospeaker.user) : cospeaker.user != null);

    }

    @Override
    public int hashCode() {
        return user != null ? user.hashCode() : 0;
    }
}
