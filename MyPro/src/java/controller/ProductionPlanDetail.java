/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.PlanDBContext;
import dal.ProductDBContext;
import dal.ScheduleDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
import model.Plan;
import model.PlanCampaign;
import model.Schedule;

/**
 *
 * @author Ad
 */
public class ProductionPlanDetail extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public ArrayList<Date> getDateList(Date startDate, Date endDate) {
        ArrayList<Date> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while (!calendar.getTime().after(endDate)) {
            dateList.add(new Date(calendar.getTimeInMillis()));
            calendar.add(Calendar.DATE, 1); // Tăng ngày
        }

        return dateList;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PlanDBContext db = new PlanDBContext();
        ProductDBContext dbp = new ProductDBContext();

        Plan p = db.get(Integer.parseInt(request.getParameter("id")));

        if (p != null) {
            ArrayList<Date> dateList = getDateList(p.getStartTime(), p.getEndTime());
            request.setAttribute("dateList", dateList);
            request.setAttribute("products", dbp.list());
            request.setAttribute("plan", p);
            request.getRequestDispatcher("../view/productionplan/detail.jsp").forward(request, response);
        } else {
            response.sendError(404, "plan does not exist!");
        }
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
        String planIdStr = request.getParameter("planId");
        if (planIdStr == null || planIdStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing plan ID");
            return;
        }

        int planId;
        try {
            planId = Integer.parseInt(planIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid plan ID");
            return;
        }

        // Lấy danh sách ngày (chúng ta đã có danh sách này trong requestScope khi gọi doGet)
        ArrayList<Date> dateList = getDateList(Date.valueOf(request.getParameter("startDate")),
                Date.valueOf(request.getParameter("endDate")));

        PlanDBContext planDB = new PlanDBContext();
        Plan plan = planDB.get(planId);


        // Lấy dữ liệu sản phẩm và số lượng từ form
        ScheduleDBContext scheduleDB = new ScheduleDBContext();

        for (int i = 0; i < dateList.size(); i++) {
            Date currentDate = dateList.get(i);

            for (int shift = 1; shift <= 3; shift++) {
                String quantityParam = "quantity" + i + "_" + shift;

                int quantity = Integer.parseInt(request.getParameter(quantityParam));

                for (PlanCampaign campaign : plan.getPc()) {
                    Schedule schedule = new Schedule();
                    schedule.setCam(campaign);
                    schedule.setDate(currentDate);
                    schedule.setK("K" + shift);  
                    schedule.setQuantity(quantity);

                    scheduleDB.insert(schedule);
                }
            }

        }
        response.sendRedirect("detail?id=" + planId);
    }
}

/**
 * Returns a short description of the servlet.
 *
 * @return a String containing servlet description
 */
