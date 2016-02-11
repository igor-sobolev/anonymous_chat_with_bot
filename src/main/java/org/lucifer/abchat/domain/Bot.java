package org.lucifer.abchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by PiCy on 2/8/2016.
 */

@Entity
@Table(name = "BOT")
public class Bot extends Identificator implements Serializable {
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "bot", cascade = CascadeType.ALL)
    private Cospeaker cospeaker;

    public  Bot() {

    }

    public Cospeaker getCospeaker() {
        return cospeaker;
    }

    public void setCospeaker(Cospeaker cospeaker) {
        this.cospeaker = cospeaker;
    }
}
