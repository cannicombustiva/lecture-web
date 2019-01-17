/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

import Models.UserAuth;
import com.google.gson.Gson;
import helpers.DB;
import helpers.UserPower;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author salva
 */
@WebFilter(filterName = "/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        String isAppHeader = req.getHeader("AppLecture");
        boolean isApp = isAppHeader != null && !isAppHeader.isEmpty();

        try {
            HttpSession session = req.getSession(false);
            DB db = new DB();
            boolean isLogged = db.isLogged(session, req);

            boolean isLoginPageRequested = uri.endsWith("Login");
            boolean isAdminPageRequested = uri.contains("/Admin");
            String homePage = req.getContextPath();

            if (isLogged) {
                // Se loggato e prova ad andare nella pagina di login redirect alla home
                if (isLoginPageRequested) {
                    res.sendRedirect(homePage);
                    return;
                }
                if (!isApp) {
                    // Se richeide la pag di admin e npn è admin redirect alla home
                    boolean isAdmin = UserPower.isAdmin(session);
                    if (isAdminPageRequested && !isAdmin) {
                        res.sendRedirect(homePage);
                        return;
                    }
                }

                // Se  è loggato e prova ad andare in qualsiasi pagina, tranne login, continua
                chain.doFilter(request, response);
                return;
            }

            // NOT LOGGED YET
            // Se non è loggato e prova ad andare in qualsiasi pagina, tranne login, redirect a login
            if (!isLoginPageRequested) {
                if (isApp) {
                    PrintWriter out = response.getWriter();
                    UserAuth userAuth = new UserAuth(new Exception("Utente non autenticato"));
                    String json = new Gson().toJson(userAuth);
                    out.println(json);
                    return;
                }
                res.sendRedirect(homePage + "/Login");
                return;
            }

            // Se non loggato e prova ad andare nella pagina di login, continua
            chain.doFilter(request, response);
        } catch (ParseException | SQLException ex) {
            Logger.getLogger(AuthenticationFilter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void destroy() {
        //close any resources here
    }

}
