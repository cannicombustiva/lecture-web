<%-- 
    Document   : layout
    Created on : 10-nov-2018, 17.22.35
    Author     : salva
--%>

<%@page import="helpers.UserPower"%>
<%@page import="helpers.DB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String pageJSP = request.getAttribute("page").toString();
    String title = request.getAttribute("title").toString();
    DB db = new DB();
    boolean isLogged = db.isLogged(request.getSession(), request);
    String userName = (String)request.getSession().getAttribute("user");
    String roleName = (String)request.getSession().getAttribute("roleName");
    Object userId = request.getSession().getAttribute("userId");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">

        <title><%= title %></title>
        
        <script>
            window.MAIN_PATH = "<%= request.getContextPath() %>";
            window.USER_ID = "<%= userId %>";
        </script>
    </head>
    <body>
        <header>
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <a class="navbar-brand" href="<%= request.getContextPath()%>">🔖</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                  <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-between" id="navbarNavAltMarkup">
                  <div class="navbar-nav">
                    <a class="nav-item nav-link <% if(pageJSP == "course-teacher" || pageJSP == "admin") out.println("active"); %>" href="<%= request.getContextPath()%>">Home</a>
                    <%// Se l'utente è admin faccio vedere queste voci di menu (menu admin)%>
                    <% if(UserPower.isAdmin(request.getSession())) {%>
                        <a class="nav-item nav-link <% if(pageJSP == "course") out.println("active"); %>" href="<%= request.getContextPath()%>/Admin/Course">Gestisci corsi</a>
                        <a class="nav-item nav-link <% if(pageJSP == "teacher") out.println("active"); %>" href="<%= request.getContextPath()%>/Admin/Teacher">Gestisci insegnanti</a>
                        <a class="nav-item nav-link <% if(pageJSP == "course-teacher") out.println("active"); %>" href="<%= request.getContextPath()%>/Admin/CourseTeacher">Fai assegnamenti</a>
                    <%}%>
                    
                  </div>
                    
                    <div class="nav ">
                        <span>
                            <div><%= userName %></div>
                            <div><%= roleName %></div>
                        </span>
                        <a class="nav-item nav-link" href="<%= request.getContextPath()%>/Logout">Logout</a>
                    </div>
                </div>
            </nav>
        </header>
    
        <jsp:include page='<%= pageJSP + ".jsp" %>' flush="true" />
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" crossorigin="anonymous"></script>

    </body>
</html>
