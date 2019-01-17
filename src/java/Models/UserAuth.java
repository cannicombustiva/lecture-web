/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author salva
 */
public class UserAuth {

    public User user;
    public String token;
    public Exception ex;
    private String exMessage;

    public UserAuth(Exception ex) {
        this.ex = ex;
        this.exMessage = ex.getMessage();
    }

    public UserAuth(User user, String token) {
        this.user = user;
        this.token = token;
    }
}
