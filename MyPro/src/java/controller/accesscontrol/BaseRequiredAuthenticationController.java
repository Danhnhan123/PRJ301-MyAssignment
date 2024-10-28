/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.accesscontrol;

import model.accesscontrol.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Ad
 */
public abstract class BaseRequiredAuthenticationController extends HttpServlet {

    private boolean isAuthenticated(HttpServletRequest req) {
        return req.getSession().getAttribute("account") != null;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAuthenticated(request)) {
            //do business logic
            doPost(request, response, (User) request.getSession().getAttribute("account"));
        } else {
            response.sendError(403, "You do not have right to access this page.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAuthenticated(request)) {
            //do business logic
            doGet(request, response, (User) request.getSession().getAttribute("account"));
        } else {
            response.sendError(403, "You do not have right to access this page.");
        }
    }

    protected abstract void doGet(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException;

    protected abstract void doPost(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException;

}
