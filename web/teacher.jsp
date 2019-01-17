<%-- 
    Document   : teacher
    Created on : 10-nov-2018, 17.37.25
    Author     : salva
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Models.Teacher"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="Container">
    <h2>Inserisci insegnante</h2>
    <form class="InlineForm" method="post" action="<%= request.getContextPath()%>/Admin/Teacher"> 
        <input class="form-control" type="text" name="teacher" minlength="3" required><br/>
        <button class="btn btn-primary" type="submit" name="save">save</button>
    </form>

    <h2>Elimina insegnante</h2>
    <form class="InlineForm" action='<%= request.getContextPath()%>/Admin/Teacher/Delete' method='POST'>
        <select name='teacher' class="custom-select" style="display: inline-block;" required>
        <%   
            List<Teacher> teachers = (List<Teacher>)request.getAttribute("teachers");
            for(Teacher teacher : teachers){
                out.println("<option value='" + teacher.id + "-" + teacher.name +"'>" + teacher.name + "</option>");
            }
        %> 
    </select>
    <button class="btn btn-danger" type='submit'>Elimina</button>
    </form>
</div>
        