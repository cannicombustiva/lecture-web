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
public class User {

    public String email;
    public String password;
    public int id;
    public int roleId;
    public String roleName;

    public User(int id, String email, int roleId, String roleName) {
        this.email = email;
        this.id = id;
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public User(String email, String password, int id, int roleId) {
        this.email = email;
        this.password = password;
        this.id = id;
        this.roleId = roleId;
    }

}
