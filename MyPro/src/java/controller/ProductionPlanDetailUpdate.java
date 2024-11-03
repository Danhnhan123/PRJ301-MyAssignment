/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import controller.accesscontrol.BaseRBACController;
import dal.PlanDBContext;
import dal.ProductDBContext;
import dal.ScheduleDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import model.Plan;
import model.PlanCampaign;
import model.Schedule;
import model.accesscontrol.User;

/**
 *
 * @author Ad
 */
public class ProductionPlanDetailUpdate extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
        PlanDBContext db = new PlanDBContext();
        ScheduleDBContext sdb = new ScheduleDBContext();

        Plan p = db.get(Integer.parseInt(request.getParameter("planId")));

        if (p != null) {
            request.setAttribute("plan", p);
            request.setAttribute("schedules", sdb.list());
            request.setAttribute("planCampaigns", p.getPc());
            request.getRequestDispatcher("/view/productionplan/detailUpdate.jsp").forward(request, response);
        } else {
            response.sendError(404, "plan does not exist!");
        }
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
        Schedule sche = new Schedule();

        sche.setId(Integer.parseInt(request.getParameter("scheId")));
        PlanCampaign pc = new PlanCampaign();
        pc.setId(Integer.parseInt(request.getParameter("camid")));
        sche.setCam(pc);
        sche.setDate(Date.valueOf(request.getParameter("date")));
        sche.setK(request.getParameter("k"));
        if (request.getParameter("quantity")!=null && !request.getParameter("quantity").isEmpty() && Integer.parseInt(request.getParameter("quantity"))!=0) {
            sche.setQuantity(Integer.parseInt(request.getParameter("quantity")));

            ScheduleDBContext sches = new ScheduleDBContext();
            sches.update(sche);
        }

        response.sendRedirect("../list");
    }

}
