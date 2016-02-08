package org.lucifer.abchat.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "USER")
@SuppressWarnings("JpaAttributeTypeInspection")
public class User extends Identificator implements Serializable {

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Cospeaker cospeaker;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<UserAnswer> answers;

    public User() {

    }

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cospeaker getCospeaker() {
        return cospeaker;
    }

    public void setCospeaker(Cospeaker cospeaker) {
        this.cospeaker = cospeaker;
    }

    public Set<UserAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<UserAnswer> answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!login.equals(user.login)) return false;
        if (!password.equals(user.password)) return false;
        return email.equals(user.email);

    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
