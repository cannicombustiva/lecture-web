/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Session;
import Models.User;
import Models.UserAuth;
import com.google.gson.Gson;
import helpers.CustomExceptions.NoUserException;
import helpers.CustomExceptions.WrongPasswordException;
import helpers.DB;
import helpers.TokenGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author salva
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class ControllerLogin extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

        // GUARDA QUI CHE BELLA COSA
        RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/login.jsp");
        RequetsDispatcherObj.forward(request, response);
    }

    private void processPostRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String isAppHeader = request.getHeader("AppLecture");
        boolean isApp = isAppHeader != null && !isAppHeader.isEmpty();
        if (isApp) {
            processAppPostRequest(request, response);
            return;
        }

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            userLogin(email, password, request.getSession());
            response.sendRedirect(request.getContextPath());
        } catch (Exception ex) {
            PrintWriter out = response.getWriter();
            if (ex instanceof NoUserException) {
                response.setStatus(400);
                out.println(ex.getMessage() + " <a href='login.html'> Torna al login </a>");
                return;
            }
            response.setStatus(500);
            out.println(ex.getMessage());
        }
    }

    private void processAppPostRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            UserAuth userAuth = userAppLogin(email, password);
            String json = new Gson().toJson(userAuth);
            out.println(json);
        } catch (Exception ex) {
            if (ex instanceof NoUserException) {
                response.setStatus(400);
                String json = new Gson().toJson(new UserAuth(ex));
                out.println(json);
                return;
            }

            out.println(ex.getMessage());
            return;
        }
    }

    private void userLogin(String email, String password, HttpSession httpSession) throws SQLException, NoUserException, WrongPasswordException {

        DB db = new DB();
        User user = db.getUserByEmail(email);
        int rolePower = db.getUserRolePower(user.roleId);
        String roleName = db.getRoleName(user.roleId);
        // DON'T DO THIS AT HOME
        if (user.password.equals(password)) {

            String token = TokenGenerator.generate();
            Session session = new Session(user.id, token);
            db.insertSession(session);
            httpSession.setAttribute("user", user.email);
            httpSession.setAttribute("token", token);
            httpSession.setAttribute("rolePower", rolePower);
            httpSession.setAttribute("roleName", roleName);
            httpSession.setAttribute("userId", user.id);

            return;
        }

        throw new WrongPasswordException();

    }

    private UserAuth userAppLogin(String email, String password) throws SQLException, NoUserException, WrongPasswordException {

        DB db = new DB();
        User user = db.getUserByEmail(email);
        int rolePower = db.getUserRolePower(user.roleId);
        String roleName = db.getRoleName(user.roleId);
        // DON'T DO THIS AT HOME
        if (user.password.equals(password)) {
            String token = TokenGenerator.generate();
            Session session = new Session(user.id, token);
            db.insertSession(session);
            UserAuth userAuth = new UserAuth(user, token);
            return userAuth;
        }

        throw new WrongPasswordException();

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
            Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
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
        processPostRequest(request, response);
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
