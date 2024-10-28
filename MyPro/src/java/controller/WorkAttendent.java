/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import controller.accesscontrol.BaseRBACController;
import dal.AttendentDBContext;
import dal.EmployeeDBContext;
import dal.PlanDBContext;
import dal.ProductDBContext;
import dal.ScheduleDBContext;
import dal.WokerScheduleDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Attendent;
import model.Schedule;
import model.WokerSchedule;
import model.accesscontrol.User;

/**
 *
 * @author Ad
 */
public class WorkAttendent extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
        WokerScheduleDBContext ws = new WokerScheduleDBContext();
        ScheduleDBContext sches = new ScheduleDBContext();
        EmployeeDBContext emps = new EmployeeDBContext();
        ProductDBContext pr = new ProductDBContext();
        PlanDBContext db = new PlanDBContext();

        request.setAttribute("plans", db.list());
        request.setAttribute("products", pr.list());
        request.setAttribute("emps", emps.list());
        request.setAttribute("workList", ws.list());
        int scheId = Integer.parseInt(request.getParameter("scheId1"));
        request.setAttribute("schedule", sches.get(scheId));
        request.getRequestDispatcher("../view/work/attendent.jsp").forward(request, response);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("scheId"));
        ArrayList<Attendent> attend = new ArrayList<>();
        WokerScheduleDBContext ws = new WokerScheduleDBContext();

        for (WokerSchedule worker : ws.list()) {
            if (worker.getSc().getId() == id) {
                Attendent at = new Attendent();
                if (request.getParameter("actualQuantity") != null && !request.getParameter("actualQuantity").isEmpty()) {
                    at.setQuantity(Integer.parseInt(request.getParameter("actualQuantity")));
                    WokerSchedule sche = new WokerSchedule();
                    sche.setId(Integer.parseInt(request.getParameter("scid")));
                    at.setWs(sche);
                    at.setAlpha(Float.parseFloat(request.getParameter("alpha2")));
                    attend.add(at);
                }
            }
        }

        AttendentDBContext db = new AttendentDBContext();
        db.insertAttends(attend);
        response.sendRedirect("../work/list");
    }

}
