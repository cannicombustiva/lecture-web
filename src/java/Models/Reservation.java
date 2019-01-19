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
public class Reservation {

    public int id;
    public int userId;
    public int courseTeacherId;
    public String courseName;
    public String teacherName;
    public String courseTeacherDate;
    public String date;

    public Reservation(int id, int userId, int courseTeacherId, String courseName, String teacherName, String courseTeacherDate, String date) {
        this.id = id;
        this.userId = userId;
        this.courseTeacherId = courseTeacherId;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.courseTeacherDate = courseTeacherDate;
        this.date = date;
    }

    public Reservation(int userId, int courseTeacherId) {
        this.userId = userId;
        this.courseTeacherId = courseTeacherId;
    }

    public Reservation(int id, int userId, int courseTeacherId, String date) {
        this.id = id;
        this.userId = userId;
        this.courseTeacherId = courseTeacherId;
        this.date = date;
    }
}
