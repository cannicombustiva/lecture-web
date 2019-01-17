<%-- 
    Document   : insert
    Created on : 10-nov-2018, 17.30.05
    Author     : salva
--%>

<%@page import="Models.Course"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="Container">
    <h3>Inserisci corso</h3>
    <form class="InlineForm" method="post" action="<%= request.getContextPath()%>/Admin/Course"> 
        <input class="form-control" type="text" name="course" minlength="2" required>
        <button class="btn btn-primary" type="submit" name="save">Save</button>
    </form>
        
    <h3> Elimina corso </h3>
    <form  class="InlineForm" action='<%= request.getContextPath()%>/Admin/Course/Delete' method='POST'>
        <select name='course' class="custom-select" style="display: inline-block;" required>
            <%   
                List<Course> courses = (List<Course>)request.getAttribute("courses");
                for(Course course : courses){
                    out.println("<option value='" + course.id + "-" + course.name +"'>" + course.name + "</option>");
                }
            %> 
        </select>

        <button class="btn btn-danger" type='submit'>Elimina</button>
    </form>
</div>

    


