/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import controller.accesscontrol.BaseRBACController;
import dal.EmployeeDBContext;
import dal.ProductDBContext;
import dal.ScheduleDBContext;
import dal.WokerScheduleDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Employee;
import model.Schedule;
import model.WokerSchedule;
import model.accesscontrol.User;

/**
 *
 * @author Ad
 */
public class WorkUpdate extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("Wsid"));
        int scheid = Integer.parseInt(request.getParameter("scheId3"));
        WokerScheduleDBContext ws = new WokerScheduleDBContext();
        EmployeeDBContext emps = new EmployeeDBContext();
        ProductDBContext pr = new ProductDBContext();
        ScheduleDBContext sdb = new ScheduleDBContext();
        request.setAttribute("schedule", sdb.get(scheid));
        request.setAttribute("work", ws.get(id));
        request.setAttribute("emps", emps.list());
        request.setAttribute("products", pr.list());
        request.getRequestDispatcher("../view/work/update.jsp").forward(request, response);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
        WokerSchedule ws = new WokerSchedule();

        int id = Integer.parseInt(request.getParameter("wsId"));
        ws.setId(id);
        Employee e = new Employee();
        e.setId(Integer.parseInt(request.getParameter("workerId")));
        ws.setE(e);
        Schedule sc = new Schedule();
        sc.setId(Integer.parseInt(request.getParameter("scheId")));
        ws.setSc(sc);
        if (request.getParameter("quantity") != null && !request.getParameter("quantity").isEmpty()) {
            ws.setQuantity(Integer.parseInt(request.getParameter("quantity")));
            WokerScheduleDBContext db = new WokerScheduleDBContext();
            db.update(ws);
        }

        response.sendRedirect("list");
    }

}
