/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import controller.accesscontrol.BaseRBACController;
import dal.AttendentDBContext;
import dal.DepartmentDBContext;
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
import model.Plan;
import model.Schedule;
import model.accesscontrol.User;

/**
 *
 * @author Ad
 */
public class WorkList extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
        PlanDBContext db = new PlanDBContext();
        DepartmentDBContext dbp = new DepartmentDBContext();
        ProductDBContext dbd = new ProductDBContext();
        ScheduleDBContext sdb = new ScheduleDBContext();
        WokerScheduleDBContext wdb = new WokerScheduleDBContext();
        EmployeeDBContext emps = new EmployeeDBContext();
        AttendentDBContext at = new AttendentDBContext();

        ArrayList<Plan> plans = db.list();
        request.setAttribute("emps", emps.list());
        request.setAttribute("plans", plans);
        request.setAttribute("workList", wdb.list());
        request.setAttribute("depts", dbp.list());
        request.setAttribute("products", dbd.list());
        request.setAttribute("attends", at.list());

        // Fetch schedules
        ArrayList<Schedule> sches = sdb.list();
        if (!sches.isEmpty()) {
            request.setAttribute("schedules", sches);
        } else {
            request.setAttribute("error1", "Schedule not found");
        }

        ScheduleDBContext sdb1 = new ScheduleDBContext();
        if (request.getParameter("scheId") != null && !request.getParameter("scheId").isEmpty()) {
            request.setAttribute("schedule", sdb1.get(Integer.parseInt(request.getParameter("scheId"))));
        }
        PlanDBContext db1 = new PlanDBContext();
        // Check if a specific plan ID is provided and fetch plan details if available
        String planIdParam = request.getParameter("id");
        if (planIdParam != null && !planIdParam.isEmpty()) {
            try {
                int planId = Integer.parseInt(planIdParam);
                Plan selectedPlan = db1.get(planId);
                if (selectedPlan != null) {
                    request.setAttribute("plan", selectedPlan);
                } else {
                    request.setAttribute("error", "Plan not found");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid Plan ID");
            }
        }

        request.getRequestDispatcher("../view/work/list.jsp").forward(request, response);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {

    }

}
