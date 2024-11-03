/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import controller.accesscontrol.BaseRBACController;
import dal.AttendentDBContext;
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
import model.Attendent;
import model.WokerSchedule;
import model.accesscontrol.User;

/**
 *
 * @author Ad
 */
public class WorkAttendentUpdate extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("atenid"));
        AttendentDBContext ats = new AttendentDBContext();
        EmployeeDBContext emps = new EmployeeDBContext();
        WokerScheduleDBContext db = new WokerScheduleDBContext();
        request.setAttribute("ws", db.list());
        request.setAttribute("attend", ats.get(id));
        request.setAttribute("emps", emps.list());
        request.getRequestDispatcher("/view/work/attendentUpdate.jsp").forward(request, response);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("wsid"));
        Attendent at = new Attendent();
        at.setId(id);
        WokerSchedule ws = new WokerSchedule();
        ws.setId(Integer.parseInt(request.getParameter("schid")));
        at.setWs(ws);
        if (request.getParameter("actualQuantity") != null && !request.getParameter("actualQuantity").isEmpty() && Integer.parseInt(request.getParameter("actualQuantity")) != 0) {
            if (Integer.parseInt(request.getParameter("actualQuantity")) < 0) {
                request.setAttribute("error", "Quantity must be positive");
                loadFormData(request,id);
                request.getRequestDispatcher("/view/work/attendentUpdate.jsp").forward(request, response);
            } else {
                at.setQuantity(Integer.parseInt(request.getParameter("actualQuantity")));
                at.setAlpha(Float.parseFloat(request.getParameter("alpha2")));

                AttendentDBContext db = new AttendentDBContext();
                db.update(at);
            }
        }
        response.sendRedirect("../list");
    }

    private void loadFormData(HttpServletRequest request, int id) {
        AttendentDBContext ats = new AttendentDBContext();
        EmployeeDBContext emps = new EmployeeDBContext();
        WokerScheduleDBContext db = new WokerScheduleDBContext();
        request.setAttribute("ws", db.list());
        request.setAttribute("attend", ats.get(id));
        request.setAttribute("emps", emps.list());
    }
}
