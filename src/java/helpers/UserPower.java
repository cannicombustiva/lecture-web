/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import javax.servlet.http.HttpSession;

/**
 *
 * @author salva
 */
public class UserPower {
    public static boolean isAdmin(HttpSession session) {
        int rolePower = (int) session.getAttribute("rolePower");
        return rolePower == 3;
    }
}
