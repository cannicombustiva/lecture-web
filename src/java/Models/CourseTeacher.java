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
public class CourseTeacher {
    public String courseName;
    public String teacherName;
    public String date;
    public int teacherId;
    public int courseId;
    public int id;

    public CourseTeacher(String date, int teacherId, int courseId) {
        this.date = date;
        this.teacherId = teacherId;
        this.courseId = courseId;
    }

    public CourseTeacher(int id, String courseName, String teacherName, String date, int teacherId, int courseId) {
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.date = date;
        this.teacherId = teacherId;
        this.courseId = courseId;
        this.id = id;
    }

    public CourseTeacher(String courseName, String teacherName, String date) {
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.date = date;
    }
    

    @Override
    public String toString() {
        return "Nome Corso: " + courseName + " Nome Insegnante: " + teacherName + "Data Lezione: " + date;
    }
}
