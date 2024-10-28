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
import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
import model.Plan;
import model.PlanCampaign;
import model.Schedule;
import model.accesscontrol.User;

/**
 *
 * @author Ad
 */
public class ProductionPlanDetail extends BaseRBACController {

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
    protected void doAuthorizedGet(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
        PlanDBContext db = new PlanDBContext();
        ProductDBContext dbp = new ProductDBContext();
        ScheduleDBContext sdb = new ScheduleDBContext();

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

    @Override
    protected void doAuthorizedPost(HttpServletRequest request, HttpServletResponse response, User loggeduser) throws ServletException, IOException {
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

        // Retrieve the date list
        ArrayList<Date> dateList = getDateList(Date.valueOf(request.getParameter("startDate")),
                Date.valueOf(request.getParameter("endDate")));

        PlanDBContext planDB = new PlanDBContext();
        Plan plan = planDB.get(planId);

        if (plan == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Plan not found");
            return;
        }

        ScheduleDBContext scheduleDB = new ScheduleDBContext();
        ArrayList<Schedule> schedulesToInsert = new ArrayList<>();  // Danh sách để lưu các Schedule cần chèn

        // Loop through the PlanCampaign in the plan
        for (PlanCampaign campaign : plan.getPc()) {
            for (Date date : dateList) {
                for (int shift = 1; shift <= 3; shift++) {
                    String quantityParam = "quantity_" + campaign.getP().getId() + "_" + date + "_K" + shift;
                    String quantityStr = request.getParameter(quantityParam);

                    if (quantityStr != null && !quantityStr.trim().isEmpty()) {
                        int quantity = Integer.parseInt(quantityStr);
                        if (quantity > 0) {
                            Schedule schedule = new Schedule();
                            schedule.setCam(campaign);
                            schedule.setDate(date);
                            schedule.setK("K" + shift);
                            schedule.setQuantity(quantity);
                            schedulesToInsert.add(schedule);  // Thêm schedule vào danh sách
                        }
                    }
                }
            }
        }

        // Gọi phương thức insertSchedules với danh sách Schedule đã chuẩn bị
        try {
            scheduleDB.insertSchedules(schedulesToInsert);  // Chèn tất cả các schedule cùng lúc
            response.sendRedirect("../productionplan/list");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}

/**
 * Returns a short description of the servlet.
 *
 * @return a String containing servlet description
 */
