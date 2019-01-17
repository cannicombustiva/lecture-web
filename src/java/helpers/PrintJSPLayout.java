/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author salva
 */
public class PrintJSPLayout {
    public static void print(
        HttpServletRequest request,
        HttpServletResponse response, 
        String title,
        String pageJSPName
    ) throws ServletException, IOException{
        request.setAttribute("title", title);
        request.setAttribute("page", pageJSPName);
        RequestDispatcher RequetsDispatcherObj =request.getRequestDispatcher("/layout.jsp");
        RequetsDispatcherObj.forward(request, response);
    }
}
