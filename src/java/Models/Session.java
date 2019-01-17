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
public class Session {
    public int userId;
    public String token;
    public int id;
    public String date;

    public Session(int userId, String token, int id, String date) {
        this.userId = userId;
        this.token = token;
        this.id = id;
        this.date = date;
    }
    

    public Session(int userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
