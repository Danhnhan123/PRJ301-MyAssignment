/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Ad
 */
public class EmployeeSchedule extends HttpServlet {

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
            calendar.add(Calendar.DATE, 1); // Increment day
        }

        return dateList;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("../view/Employee/Schedule.jsp").forward(request, response);
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
        int month = Integer.parseInt(request.getParameter("month"));
        int year = Integer.parseInt(request.getParameter("year"));

        // Create start and end dates for the month
        Calendar startCal = Calendar.getInstance();
        startCal.set(year, month, 1);
        Date startDate = new Date(startCal.getTimeInMillis());

        Calendar endCal = Calendar.getInstance();
        endCal.set(year, month, startCal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = new Date(endCal.getTimeInMillis());

        // Get the list of dates for the selected month
        ArrayList<Date> dateList = getDateList(startDate, endDate);

        // Set the date list in request attribute
        request.setAttribute("dateList", dateList);

        // Forward to the JSP to display the calendar
        request.getRequestDispatcher("../view/Employee/Schedule.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
}
