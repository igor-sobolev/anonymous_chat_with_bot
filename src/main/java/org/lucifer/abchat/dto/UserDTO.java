package org.lucifer.abchat.dto;

/**
 * Created by PiCy on 2/10/2016.
 */
public class UserDTO {
    private String login;
    private String email;

    public UserDTO(){

    }

    public UserDTO(String login, String email) {
        this.login = login;
        this.email = email;
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
}
