package org.lucifer.abchat.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MESSAGE_LINK")
public class MessageLink extends  Identificator {
    @Column(name = "STIMULUS")
    private String stimulus;

    @Column(name = "MESSAGE")
    private String message;

    public MessageLink() {

    }

    public MessageLink(String stimulus, String message) {
        this.stimulus = stimulus;
        this.message = message;
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
