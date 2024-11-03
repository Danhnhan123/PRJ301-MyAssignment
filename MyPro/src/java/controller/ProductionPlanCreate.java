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
public class ProductionPlanCreate extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
        ProductDBContext db = new ProductDBContext();
        DepartmentDBContext dbd = new DepartmentDBContext();

        request.setAttribute("products", db.list());
        request.setAttribute("depts", dbd.get("workshop"));

        request.getRequestDispatcher("../view/productionplan/create.jsp").forward(request, response);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
        String[] pids = request.getParameterValues("pid");

        Plan plan = new Plan();
        if (request.getParameter("from") != null && !request.getParameter("from").isEmpty() && request.getParameter("to") != null && !request.getParameter("to").isEmpty()) {
            Date startTime = Date.valueOf(request.getParameter("from"));
            Date endTime = Date.valueOf(request.getParameter("to"));

            // Check if 'from' date is greater than 'to' date
            if (startTime.after(endTime)) {
                // Set error attribute and return to JSP if 'from' is greater than 'to'
                request.setAttribute("error", "'From' date cannot be after 'To' date. Please enter valid dates.");

                loadFormData(request);

                request.getRequestDispatcher("../view/productionplan/create.jsp").forward(request, response);
                return;
            }

            Department d = new Department();
            d.setId(Integer.parseInt(request.getParameter("did")));
            plan.setD(d);

            plan.setPc(new ArrayList<>());
            StringBuilder errorBuilder = new StringBuilder();
            for (String pid : pids) {
                Product p = new Product();
                p.setId(Integer.parseInt(pid));
                PlanCampaign pg = new PlanCampaign();
                pg.setP(p);

                try {
                    String raw_quantity = request.getParameter("quantity" + pid);
                    String raw_effort = request.getParameter("effort" + pid);

                    // Parse values and set them to PlanCampaign
                    int quantity = raw_quantity != null && raw_quantity.length() > 0 ? Integer.parseInt(raw_quantity) : 0;
                    float effort = raw_effort != null && raw_effort.length() > 0 ? Float.parseFloat(raw_effort) : 0;

                    if (quantity < 0 || effort < 0) {
                        throw new InvalidInputException("Quantity and Effort cannot be less than 0!");
                    }

                    pg.setQuantity(quantity);
                    pg.setEffort(effort);
                    pg.setPl(plan);

                    if (quantity != 0 && effort != 0) {
                        plan.getPc().add(pg);
                    }
                } catch (NumberFormatException e) {
                    // If parsing fails, append an error message
                    errorBuilder.append("Invalid input for quantity or effort!");
                } catch (InvalidInputException ex) {
                    errorBuilder.append(ex.getMessage()).append(" ");
                }
            }
            if (errorBuilder.length() > 0) {
                loadFormData(request); // Load products and departments for display
                request.setAttribute("error", errorBuilder.toString());
                request.getRequestDispatcher("../view/productionplan/create.jsp").forward(request, response);
                return;
            }
            String error = "Your plan was added!";
            String error1 = "Your plan did not have any campains!";
            if (plan.getPc().size() > 0) {
                PlanDBContext db = new PlanDBContext();
                db.insert(plan);
                response.sendRedirect("../productionplan/list");
            } else {
                ProductDBContext db = new ProductDBContext();
                DepartmentDBContext dbd = new DepartmentDBContext();

                request.setAttribute("products", db.list());
                request.setAttribute("depts", dbd.get("workshop"));
                request.setAttribute("error", error1);
                request.getRequestDispatcher("../view/productionplan/create.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Date can not empty!");
            loadFormData(request);
            request.getRequestDispatcher("../view/productionplan/create.jsp").forward(request, response);
        }
    }

    private void loadFormData(HttpServletRequest request) {
        ProductDBContext db = new ProductDBContext();
        DepartmentDBContext dbd = new DepartmentDBContext();
        request.setAttribute("products", db.list());
        request.setAttribute("depts", dbd.get("workshop"));
    }
}
