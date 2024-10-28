/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import controller.accesscontrol.BaseRBACController;
import dal.WokerScheduleDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.WokerSchedule;
import model.accesscontrol.User;

/**
 *
 * @author Ad
 */
public class WorkDelete extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {

    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("wsId"));
        WokerSchedule sche = new WokerSchedule();
        sche.setId(id);
        WokerScheduleDBContext ws = new WokerScheduleDBContext();
        ws.delete(sche);
        response.sendRedirect("list");
    }

}
