package org.lucifer.abchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "USER")
@SuppressWarnings("JpaAttributeTypeInspection")
public class User extends Identificator implements Serializable {
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_USER = "user";

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SCORE")
    private Long score;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "REGISTER_DATE")
    @Temporal(value = TemporalType.DATE)
    private Date registerDate;

    @Column(name = "ACTIVITY_DATE")
    @Temporal(value = TemporalType.DATE)
    private Date activityDate;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Cospeaker> cospeakers;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<UserAnswer> answers;

    public User() {
        this.score = 0L;
        this.registerDate = new Date();
        this.activityDate = new Date();
        this.role = ROLE_USER;
    }

    public User(String login, String password, String email) {
        this();
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
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

    public Set<UserAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<UserAnswer> answers) {
        this.answers = answers;
    }

    public Set<Cospeaker> getCospeakers() {
        return cospeakers;
    }

    public void setCospeakers(Set<Cospeaker> cospeakers) {
        this.cospeakers = cospeakers;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return !(email != null ? !email.equals(user.email) : user.email != null);

    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
