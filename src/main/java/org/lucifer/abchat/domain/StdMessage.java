package org.lucifer.abchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "STD_MESSAGE")
public class StdMessage extends Identificator implements Serializable{
    @Column(name = "MESSAGE")
    private String message;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST})
    private BotStrategy strategy;

    public StdMessage() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BotStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(BotStrategy strategy) {
        this.strategy = strategy;
    }
}
