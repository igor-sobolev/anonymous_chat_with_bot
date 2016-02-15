package org.lucifer.abchat.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by PiCy on 2/15/2016.
 */
@Entity
@Table(name = "MESSAGE_LINK")
public class MessageLink extends  Identificator {
    @Column(name = "STIMULUS")
    private String stimulus;

    @Column(name = "MESSAGE")
    private String message;
}
