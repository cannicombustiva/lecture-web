/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers.CustomExceptions;

/**
 *
 * @author salva
 */

// Vedi differenza con la NoUserException, passo direttamente il messaggio
public class WrongPasswordException extends Exception{

    public WrongPasswordException() {
        super("Password errata <a href='login.html'> Torna al login </a>");
    }
    
    
}
