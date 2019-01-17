/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Course;
import Models.CourseTeacher;
import Models.Teacher;
import helpers.DB;
import helpers.PrintJSPLayout;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author salva
 */
@WebServlet(name = "CourseTeacher", urlPatterns = {"/Admin/CourseTeacher", "/Admin/CourseTeacher/Delete"})
public class ControllerCourseTeacher extends HttpServlet {

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
            List<Course> courses = db.getAllCourses();
            List<Teacher> teachers = db.getAllTeachers();
            List<CourseTeacher> courseTeacher = db.getAllCoursesTeacher();
            
            request.setAttribute("courseTeacher", courseTeacher);
            request.setAttribute("courses", courses);
            request.setAttribute("teachers", teachers);
            PrintJSPLayout.print(request, response, "Visualizza lezioni", "course-teacher");
            
        } catch (Exception ex) {
            PrintWriter out = response.getWriter();
            out.println(ex);
        }
                
    }
    
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        
        try {
            boolean isDelete = request.getRequestURI().contains("/Delete");
            if(isDelete){
                processDeleteRequest(request, response);
                return;
            }
            
            String date = request.getParameter("date").replace("T", "");
            int teacherId = Integer.parseInt(request.getParameter("teacher"));
            int courseId = Integer.parseInt(request.getParameter("course"));
            CourseTeacher newCourseTeacher = new CourseTeacher(date, teacherId, courseId);
             
            DB db = new DB();
            db.insertCourseTeacher(newCourseTeacher);
         
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
                out.println("<h1>L'assegnamento con id " + teacherId + " - " + courseId + " del " + date + " è stato salvato</h1>");
                out.println("<h2>Ritorna agli assegnamenti</h2>");
                out.println("<a href=\"/LecturesRes/Admin/CourseTeacher\" class=\"btn btn-primary\" class=\"float-left submit-button\" >Back</a>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            
        } catch (SQLException ex) {
            PrintWriter out = response.getWriter();
            out.println(ex);
        }
                
    }
    
    protected void processDeleteRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int courseTeacherId = Integer.parseInt(request.getParameter("courseTeacher"));
        
        try {
            DB db = new DB();
            db.deleteCourseTeacher(courseTeacherId);
            
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
                out.println("<h1>L'assegnamento con id " + courseTeacherId + " è stato rimosso</h1>");
                out.println("<h2>Ritorna agli assegnamenti</h2>");
                out.println("<a href=\"/LecturesRes/Admin/CourseTeacher\" class=\"btn btn-primary\" class=\"float-left submit-button\" >Back</a>");
                out.println("</div>");
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
        try {
            processPostRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(CourseTeacher.class.getName()).log(Level.SEVERE, null, ex);
        }
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
