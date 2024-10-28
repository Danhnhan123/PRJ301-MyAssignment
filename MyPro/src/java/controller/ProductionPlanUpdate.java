/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import controller.accesscontrol.BaseRBACController;
import dal.DepartmentDBContext;
import dal.PlanDBContext;
import dal.ProductDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Plan;
import java.sql.Date;
import java.util.ArrayList;
import model.Department;
import model.PlanCampaign;
import model.Product;
import model.accesscontrol.User;

/**
 *
 * @author Ad
 */
public class ProductionPlanUpdate extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        PlanDBContext db = new PlanDBContext();
        DepartmentDBContext d = new DepartmentDBContext();
        ProductDBContext pr = new ProductDBContext();
        Plan p = db.get(id);
        if (p != null) {
            request.setAttribute("products", pr.list());
            request.setAttribute("depts", d.list());
            request.setAttribute("plan", p);
            if (p.getPc() != null) {
                request.setAttribute("planCampaign", p.getPc());
            }
            request.getRequestDispatcher("../view/productionplan/update.jsp").forward(request, response);
        } else {
            response.sendError(404, "plan does not exist!");
        }
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
        String[] pids = request.getParameterValues("prid");
        //read parameters
        String raw_id = request.getParameter("planId");
        String raw_start = request.getParameter("startTime");
        String raw_end = request.getParameter("endTime");
        String raw_did = request.getParameter("did");

        //validate parameters (do it your self)
        //object binding
        Plan p = new Plan();
        p.setId(Integer.parseInt(raw_id));
        p.setStartTime(Date.valueOf(raw_start));
        p.setEndTime(Date.valueOf(raw_end));

        Department d = new Department();
        d.setId(Integer.parseInt(raw_did));

        p.setD(d);

        p.setPc(new ArrayList<>());

        for (String pid : pids) {
            Product pr = new Product();
            pr.setId(Integer.parseInt(pid));
            PlanCampaign pc = new PlanCampaign();
            pc.setP(pr);
            String raw_quantity = request.getParameter("quantity" + pid);
            String raw_effort = request.getParameter("effort" + pid);

            pc.setQuantity(raw_quantity != null && raw_quantity.length() > 0 ? Integer.parseInt(raw_quantity) : 0);
            pc.setEffort(raw_effort != null && raw_effort.length() > 0 ? Float.parseFloat(raw_effort) : 0);
            pc.setPl(p);
            if (pc.getQuantity() != 0 && pc.getEffort() != 0) {
                p.getPc().add(pc);
            }

        }

        PlanDBContext db = new PlanDBContext();
        db.update(p);

        response.sendRedirect("list");
    }

}
