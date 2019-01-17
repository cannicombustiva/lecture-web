/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import Models.Reservation;
import Models.Session;
import Models.User;
import com.google.gson.Gson;
import helpers.CustomExceptions.NoTokenException;
import helpers.CustomExceptions.NoUserException;
import helpers.DB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "APIUser", urlPatterns = {"/Api/Me"})
public class APIUser extends HttpServlet {

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
            throws ServletException, IOException, SQLException, NoTokenException, NoUserException {
        response.setContentType("application/json;charset=UTF-8");
        String appToken = request.getHeader("Authorization");
        DB db = new DB();
        PrintWriter out = response.getWriter();
        Session session = db.getSession(appToken);

        User user = db.getUserById(session.userId);
        String json = new Gson().toJson(user);
        out.println(json);
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
            Logger.getLogger(APIUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoTokenException ex) {
            Logger.getLogger(APIUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoUserException ex) {
            Logger.getLogger(APIUser.class.getName()).log(Level.SEVERE, null, ex);
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
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(APIUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoTokenException ex) {
            Logger.getLogger(APIUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoUserException ex) {
            Logger.getLogger(APIUser.class.getName()).log(Level.SEVERE, null, ex);
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
