package org.lucifer.abchat.dto;

/**
 * Created by PiCy on 2/10/2016.
 */
public class UserDTO {
    private String login;
    private String email;
    private Long score;

    public UserDTO(){

    }

    public UserDTO(String login, String email, Long score) {
        this.login = login;
        this.email = email;
        this.score = score;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
