/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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

/**
 *
 * @author Ad
 */
public class ProductionPlanCreate extends HttpServlet {

    
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDBContext db = new ProductDBContext();
        DepartmentDBContext dbd = new DepartmentDBContext();

        request.setAttribute("products", db.list());
        request.setAttribute("depts", dbd.get("workshop"));

        request.getRequestDispatcher("../view/productionplan/create.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] pids = request.getParameterValues("pid");

        Plan plan = new Plan();
        plan.setStartTime(Date.valueOf(request.getParameter("from")));
        plan.setEndTime(Date.valueOf(request.getParameter("to")));

        Department d = new Department();
        d.setId(Integer.parseInt(request.getParameter("did")));
        plan.setD(d);

        plan.setPc(new ArrayList<>());
        for (String pid : pids) {
            Product p = new Product();
            p.setId(Integer.parseInt(pid));
            PlanCampaign pg = new PlanCampaign();
            pg.setP(p);

            String raw_quantity = request.getParameter("quantity" + pid);
            String raw_effort = request.getParameter("effort" + pid);

            pg.setQuantity(raw_quantity != null && raw_quantity.length() > 0 ? Integer.parseInt(raw_quantity) : 0);
            pg.setEffort(raw_effort != null && raw_effort.length() > 0 ? Float.parseFloat(raw_effort) : 0);
            pg.setPl(plan);
            if (pg.getQuantity() != 0 && pg.getEffort() != 0) {
                plan.getPc().add(pg);
            }
        }

        if (plan.getPc().size() > 0) {
            PlanDBContext db = new PlanDBContext();
            db.insert(plan);
            response.getWriter().println("created a new plan!");
        } else {
            response.getWriter().println("your plan did not have any campains");
        }
    }

}
