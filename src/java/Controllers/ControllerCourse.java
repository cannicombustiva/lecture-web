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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author salva
 */

@WebServlet(name = "Course", urlPatterns = {"/Admin/Course", "/Admin/Course/Delete"})
public class ControllerCourse extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    /** Nella servelet ci sono metodi di GET e di POST
       che richiamano un metodo di processRequest, questo lo modifico 
       a mio piacimento. Con i processRequest di GET mi creo un'istanza del mio DB
       e una dell'oggetto da cui voglio ricavare certe informazioni. Con quello di POST
       invece prendo le info dal mio form con method = POST, le salvo in un'opportuna variabile
       mi istanzio l'oggetto che contiene quel tipo di variabile e richiamandomi il DB
       applico i metodi creati nel mio oggetto DB.  
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try {    
            DB db = new DB();
            List<Course> courses =  db.getAllCourses();           
            request.setAttribute("courses", courses);                    
            PrintJSPLayout.print(request, response, "Gestione corsi", "course");
            
        } catch (Exception ex) {
             PrintWriter out = response.getWriter();
             out.println(ex);       
        }
                
    }
    
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String courseName = request.getParameter("course");
        
        Course newCourse = new Course(courseName);
        
        try {
            boolean isDelete = request.getRequestURI().contains("/Delete");
            if(isDelete){
                processDeleteRequest(request, response);
                return;
            }
 
            DB db = new DB();
            db.insertCourse(newCourse);
            
            PrintWriter out = response.getWriter();
            
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
            out.println("<h1>Inserimento corso riuscito</h1>");
            out.println("<h2>Ritorna alla gestione corsi</h2>");
            out.println("<button class=\"btn btn-primary\" id=\"myButton\" class=\"float-left submit-button\" >Back</button>");
            out.println("</div>");
            out.println("<script type=\"text/javascript\">document.getElementById(\"myButton\").onclick = function () {"
                        + "location.href =\"/LecturesRes/Admin/Course\";};"
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
                out.println("<title>Insert Page</title>");
                out.println("<link rel=\"stylesheet\" href=\"/LectureRes/WebPages/assets/css/style.css\">");
                out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" crossorigin=\"anonymous\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class=\"container-fluid\">");
                out.println("<h1>Il corso di " + courseName + " è già stato inserito</h1>");
                out.println("<h2>Ritorna alla gestione corsi</h2>");
                out.println("<button class=\"btn btn-primary\" id=\"myButton\" class=\"float-left submit-button\" >Back</button>");
                out.println("</div>");
                out.println("<script type=\"text/javascript\">document.getElementById(\"myButton\").onclick = function () {"
                            + "location.href =\"/LecturesRes/Admin/Course\";};"
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
        
        String[] splittedCourse = request.getParameter("course").split("-");
        int courseId = Integer.parseInt(splittedCourse[0]);
        String courseName = splittedCourse[1];
        
        
        try {
            DB db = new DB();
            db.deleteCourse(courseId);
            
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
                out.println("<h1>Il corso di " + courseName + " è stato rimosso</h1>");
                out.println("<h2>Ritorna alla gestione corsi</h2>");
                out.println("<button class=\"btn btn-primary\" id=\"myButton\" class=\"float-left submit-button\" >Back</button>");
                out.println("</div>");
                out.println("<script type=\"text/javascript\">document.getElementById(\"myButton\").onclick = function () {"
                            + "location.href =\"/LecturesRes/Admin/Course\";};"
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
