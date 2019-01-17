<%-- 
    Document   : course-teacher
    Created on : 13-nov-2018, 23.04.16
    Author     : salva
--%>

<%@page import="Models.CourseTeacher"%>
<%@page import="Models.Course"%>
<%@page import="Models.Teacher"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="container">
    <h2>Assegnamento</h2>
    <form class="InlineForm Column" action='<%= request.getContextPath()%>/Admin/CourseTeacher' method='POST'>
        <select class="custom-select" name='teacher' required>
            <%   
                List<Teacher> teachers = (List<Teacher>)request.getAttribute("teachers");
                for(Teacher teacher : teachers){
                    out.println("<option value='" + teacher.id + "'>" + teacher.name + "</option>");
                }
            %> 
        </select><br>
        <select class="custom-select" name='course' required>
            <%   
                List<Course> courses = (List<Course>)request.getAttribute("courses");
                for(Course course : courses){
                    out.println("<option value='" + course.id + "'>" + course.name + "</option>");
                }
            %> 
        </select><br>
        <input class="form-control" type='datetime-local' name='date' required>
        <button class="btn btn-primary" type='submit'>Salva</button>
    </form>
    <h2>Elimina assegnamento</h2>
    <form class="InlineForm" action='<%= request.getContextPath()%>/Admin/CourseTeacher/Delete' method='POST'>
       <select name='courseTeacher' class="custom-select" style="display: inline-block;" required>
       <%   
           List<CourseTeacher> coursesTeachers = (List<CourseTeacher>)request.getAttribute("courseTeacher");
           for(CourseTeacher courseTeacher : coursesTeachers){
               out.println("<option value='" + courseTeacher.id + "'>" + courseTeacher.teacherName + " " + courseTeacher.courseName + " " + courseTeacher.date + "</option>");
           }
       %> 
        </select>
        <button class="btn btn-danger" type='submit'>Elimina</button>
    </form>
</div>