package org.lucifer.abchat.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by PiCy on 2/8/2016.
 */

@Entity
@Table(name = "BOT")
public class Bot extends Identificator implements Serializable {
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "bot")
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
