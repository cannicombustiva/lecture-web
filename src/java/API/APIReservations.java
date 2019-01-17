/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import Models.ApiResponseCourseTeacher;
import Models.ApiResponseReservation;
import Models.CourseTeacher;
import Models.Reservation;
import com.google.gson.Gson;
import helpers.DB;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author salva
 */
@WebServlet(name = "APIReservations", urlPatterns = {"/Api/Reservations", "/Api/Reservations/History", "/Api/Reservations/Bookable"})
public class APIReservations extends HttpServlet {

    // https://stackoverflow.com/a/25617320
    private String getBody(HttpServletRequest request) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        return data;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ParseException {
        response.setContentType("application/json;charset=UTF-8");
        DB db = new DB();
        PrintWriter out = response.getWriter();
        String userIdParam = request.getParameter("userId");
        String dateParam = request.getParameter("date");

        if (userIdParam == null) {
            out.println("non implementato");
            return;
        }
        int userId = Integer.parseInt(request.getParameter("userId"));

        boolean isHistory = request.getRequestURI().contains("/History");
        if (isHistory) {
            List<Reservation> reservations = db.getUserReservationsHistory(userId);
            ApiResponseReservation apiResponse = new ApiResponseReservation(reservations);
            String json = new Gson().toJson(apiResponse);
            out.println(json);
            return;
        }

        Date dateValue = new Date(Long.MIN_VALUE);
        if (dateParam != null && !dateParam.isEmpty()) {
            try {
                dateValue = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ITALY).parse(dateParam);
            } catch (ParseException ex) {
                response.sendError(400, "Bad request: invalide date");
                throw ex;
            }

        }

        boolean isBookable = request.getRequestURI().contains("/Bookable");
        if (isBookable) {
            List<Reservation> reservation = db.getUserReservations(userId, dateValue);
            List<CourseTeacher> courseTeachers = db.getAllValidCoursesTeacher();
            List<CourseTeacher> filtered = courseTeachers.stream().filter(p -> {
                boolean bookedCourse = reservation.stream().anyMatch(r -> {
                    return r.courseTeacherId == p.id;
                });
                return !bookedCourse;
            }).collect(Collectors.toList());
            ApiResponseCourseTeacher apiResponse = new ApiResponseCourseTeacher(filtered);
            String json = new Gson().toJson(apiResponse);
            out.println(json);
            return;
        }

        List<Reservation> reservation = db.getUserReservations(userId, dateValue);
        ApiResponseReservation apiResponse = new ApiResponseReservation(reservation);
        String json = new Gson().toJson(apiResponse);
        out.println(json);
    }

    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("application/json;charset=UTF-8");
        String data = getBody(request);

        DB db = new DB();
        PrintWriter out = response.getWriter();
        Reservation reservation = new Gson().fromJson(data, Reservation.class);
        db.insertReservation(reservation);

        out.println("{\"success\": true}");
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(APIReservations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(APIReservations.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        } catch (SQLException ex) {
            Logger.getLogger(APIReservations.class.getName()).log(Level.SEVERE, null, ex);
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
