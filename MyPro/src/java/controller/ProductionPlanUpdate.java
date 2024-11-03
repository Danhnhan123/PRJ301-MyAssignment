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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Department;
import model.PlanCampaign;
import model.Product;
import model.accesscontrol.User;
import org.eclipse.jdt.core.compiler.InvalidInputException;

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
        Date raw_start = Date.valueOf(request.getParameter("startTime"));
        Date raw_end = Date.valueOf(request.getParameter("endTime"));
        if (raw_start.after(raw_end)) {
            // Set error attribute and return to JSP if 'from' is greater than 'to'
            request.setAttribute("error1", "'From' date cannot be after 'To' date. Please enter valid dates.");

            loadFormData(request, Integer.parseInt(raw_id));

            request.getRequestDispatcher("../view/productionplan/update.jsp").forward(request, response);
            return;
        }
        String raw_did = request.getParameter("did");

        //validate parameters (do it your self)
        //object binding
        Plan p = new Plan();
        p.setId(Integer.parseInt(raw_id));
        p.setStartTime(raw_start);
        p.setEndTime(raw_end);

        Department d = new Department();
        d.setId(Integer.parseInt(raw_did));

        p.setD(d);

        p.setPc(new ArrayList<>());
        StringBuilder errorBuilder = new StringBuilder();
        for (String pid : pids) {
            Product pr = new Product();
            pr.setId(Integer.parseInt(pid));
            PlanCampaign pc = new PlanCampaign();
            pc.setP(pr);
            try {
                String raw_quantity = request.getParameter("quantity" + pid);
                String raw_effort = request.getParameter("effort" + pid);

                // Parse values and set them to PlanCampaign
                int quantity = raw_quantity != null && raw_quantity.length() > 0 ? Integer.parseInt(raw_quantity) : 0;
                float effort = raw_effort != null && raw_effort.length() > 0 ? Float.parseFloat(raw_effort) : 0;

                if (quantity < 0 || effort < 0) {
                    throw new InvalidInputException("Quantity and Effort cannot be less than 0!");
                }

                pc.setQuantity(quantity);
                pc.setEffort(effort);
                pc.setPl(p);

                if (quantity != 0 && effort != 0) {
                    p.getPc().add(pc);
                }
            } catch (NumberFormatException e) {
                // If parsing fails, append an error message
                errorBuilder.append("Invalid input for quantity or effort!");
            } catch (InvalidInputException ex) {
                errorBuilder.append(ex.getMessage()).append(" ");
            }

        }
        if (errorBuilder.length() > 0) {
            loadFormData(request, Integer.parseInt(raw_id)); // Load products and departments for display
            request.setAttribute("error1", errorBuilder.toString());
            request.getRequestDispatcher("../view/productionplan/update.jsp").forward(request, response);
            return;
        }
        PlanDBContext db = new PlanDBContext();
        db.update(p);

        response.sendRedirect("list");
    }

    private void loadFormData(HttpServletRequest request, int id) {
        PlanDBContext db = new PlanDBContext();
        DepartmentDBContext d = new DepartmentDBContext();
        ProductDBContext pr = new ProductDBContext();
        Plan p = db.get(id);
        request.setAttribute("products", pr.list());
        request.setAttribute("depts", d.list());
        request.setAttribute("plan", p);
        request.setAttribute("planCampaign", p.getPc());
    }
}
