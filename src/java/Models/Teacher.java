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
public class Teacher {
    public String name;
    public int id;

    public Teacher(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Teacher(String teacherName) {
        this.name = teacherName;
    }
}
