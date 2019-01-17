/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import Models.Course;
import Models.CourseTeacher;
import Models.Teacher;
import helpers.DB;
import helpers.PrintJSPLayout;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author salva
 */
// Aggiungo pure il path /Teacher/Delete per le operazioni di delete
@WebServlet(name = "Teacher", urlPatterns = {"/Admin/Teacher", "/Admin/Teacher/Delete"})
public class ControllerTeacher extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code>
     * method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            DB db = new DB();
            List<Teacher> teachers =  db.getAllTeachers();
            
            request.setAttribute("teachers", teachers);
            PrintJSPLayout.print(request, response, "Gestione insegnanti", "teacher");
        } catch (Exception ex) {
            PrintWriter out = response.getWriter();
            out.println(ex);
        }
                
    }
 
    
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String teacherName = request.getParameter("teacher");
        
        Teacher newTeacher = new Teacher(teacherName);
        
        try {
            boolean isDelete = request.getRequestURI().contains("/Delete");
            if(isDelete){
                processDeleteRequest(request, response);
                return;
            }
            DB db = new DB();
            db.insertTeacher(newTeacher);
            
            PrintWriter out = response.getWriter();  
            String homePage = request.getContextPath() + "/Admin/Teacher";
            response.setContentType("text/html;charset=UTF-8");
            response.setContentType("text/html;charset=UTF-8");
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Insert Page</title>");
                out.println("<link rel=\"stylesheet\" href=\"/LectureRes/WebPages/assets/css/style.css\">");
                out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" crossorigin=\"anonymous\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class=\"container-fluid\">");
                out.println("<h1>Inserimento riuscito</h1>");
                out.println("<h2>Ritorna alla gestione insegnanti</h2>");
                out.println("<button class=\"btn btn-primary\" id=\"myButton\" class=\"float-left submit-button\" >Back</button>");
                out.println("</div>");
                out.println("<script type=\"text/javascript\">document.getElementById(\"myButton\").onclick = function () {"
                            + "location.href =\"/LecturesRes/Admin/Teacher\";};"
                            + "</script>");
                out.println("<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" crossorigin=\"anonymous\"></script>\n" +
                          "        <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\" crossorigin=\"anonymous\"></script>\n" +
                          "        <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" crossorigin=\"anonymous\"></script>");
                out.println("</body>");
                out.println("</html>");
            
        } catch (SQLException ex) {
            PrintWriter out = response.getWriter();
            
            if (ex instanceof MySQLIntegrityConstraintViolationException){
                response.setContentType("text/html;charset=UTF-8");
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Already Existing Professor Page</title>");
                out.println("<link rel=\"stylesheet\" href=\"/LectureRes/WebPages/assets/css/style.css\">");
                out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" crossorigin=\"anonymous\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class=\"container-fluid\">");
                out.println("<h1>Professore '" + teacherName + "' già esistente</h1>");
                out.println("<h2>Ritorna alla gestione insegnanti</h2>");
                out.println("<button class=\"btn btn-primary\" id=\"myButton\" class=\"float-left submit-button\" >Back</button>");
                out.println("</div>");
                out.println("<script type=\"text/javascript\">document.getElementById(\"myButton\").onclick = function () {"
                            + "location.href =\"/LecturesRes/Admin/Teacher\";};"
                            + "</script>");
                out.println("<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" crossorigin=\"anonymous\"></script>\n" +
                          "        <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\" crossorigin=\"anonymous\"></script>\n" +
                          "        <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" crossorigin=\"anonymous\"></script>");
                out.println("</body>");
                out.println("</html>");
                return;
            }
            out.println(ex);
        }
                
    }
    
     protected void processDeleteRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Dal form con name = course mi arriva sia value(id)
        
        String[] splittedTeacher = request.getParameter("teacher").split("-");
        int teacherId = Integer.parseInt(splittedTeacher[0]);
        String teacherName = splittedTeacher[1];
        
        
        try {
            DB db = new DB();
            db.deleteTeacher(teacherId);
            
            PrintWriter out = response.getWriter();
            
            response.setContentType("text/html;charset=UTF-8");
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Delete Page</title>");
                out.println("<link rel=\"stylesheet\" href=\"/LectureRes/WebPages/assets/css/style.css\">");
                out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" crossorigin=\"anonymous\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class=\"container-fluid\">");
                out.println("<h1>Il professore " + teacherName + " è stato rimosso</h1>");
                out.println("<h2>Ritorna alla gestione insegnanti</h2>");
                out.println("<button class=\"btn btn-primary\" id=\"myButton\" class=\"float-left submit-button\" >Back</button>");
                out.println("</div>");
                out.println("<script type=\"text/javascript\">document.getElementById(\"myButton\").onclick = function () {"
                            + "location.href =\"/LecturesRes/Admin/Teacher\";};"
                            + "</script>");
                out.println("<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" crossorigin=\"anonymous\"></script>\n" +
                          "        <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\" crossorigin=\"anonymous\"></script>\n" +
                          "        <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" crossorigin=\"anonymous\"></script>");
                out.println("</body>");
                out.println("</html>");
            
            
        } catch (Exception ex) {
            PrintWriter out = response.getWriter();
            out.println(ex);
        }
                
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processPostRequest(request, response);
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processDeleteRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
