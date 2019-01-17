/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.List;

/**
 *
 * @author salva
 */
public class ApiResponseCourseTeacher {

    public List<CourseTeacher> items;

    public ApiResponseCourseTeacher(List<CourseTeacher> items) {
        this.items = items;
    }
}
