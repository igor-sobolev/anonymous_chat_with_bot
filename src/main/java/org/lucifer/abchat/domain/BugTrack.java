package org.lucifer.abchat.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "BUG_TRACK")
public class BugTrack extends Identificator implements Serializable {
    @Column(name = "LOGIN")
    private String login;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;

    public BugTrack() {
        this.date = new Date();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
