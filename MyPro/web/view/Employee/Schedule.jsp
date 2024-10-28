<%-- 
    Document   : Schedule
    Created on : Oct 28, 2024, 2:14:55 PM
    Author     : Ad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Calendar Page</title>
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
            }
            th, td {
                border: 1px solid #000;
                text-align: center;
                padding: 10px;
            }
            a {
                text-decoration: none;
                color: blue;
            }
            a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <h1>Calendar View</h1>

        <form method="POST" action="schedule">
            <label for="year">Year:</label>
            <select id="year" name="year">
                <%
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    for (int i = currentYear - 10; i <= currentYear + 10; i++) {
                %>
                <option value="<%=i%>" <%= (i == currentYear) ? "selected" : "" %>><%=i%></option>
                <%
                    }
                %>
            </select>

            <label for="month">Month:</label>
            <select id="month" name="month">
                <%
                    for (int i = 0; i < 12; i++) {
                %>
                <option value="<%=i%>" <%= (i == (request.getParameter("month") != null ? Integer.parseInt(request.getParameter("month")) : Calendar.getInstance().get(Calendar.MONTH))) ? "selected" : "" %>><%= new java.text.DateFormatSymbols().getMonths()[i] %></option>
                <%
                    }
                %>
            </select>

            <input type="submit" value="Show Calendar" />
        </form>

        <br/><br/>

        <%
            // Retrieve the date list from the request
            ArrayList<Date> dateList = (ArrayList<Date>) request.getAttribute("dateList");
            if (dateList != null) {
                // Prepare the calendar view
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateList.get(0)); // Get the first date
                int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                out.println("<table>");
                out.println("<tr>");
                out.println("<th>Sun</th>");
                out.println("<th>Mon</th>");
                out.println("<th>Tue</th>");
                out.println("<th>Wed</th>");
                out.println("<th>Thu</th>");
                out.println("<th>Fri</th>");
                out.println("<th>Sat</th>");
                out.println("</tr><tr>");

                // Print empty cells for the days before the first day of the month
                for (int i = 1; i < firstDayOfWeek; i++) {
                    out.print("<td></td>");
                }

                // Print each day of the month
                for (int day = 1; day <= daysInMonth; day++) {
                    out.print("<td><a href='#'>" + day + "</a></td>");

                    // Start a new row after Saturday
                    if ((day + firstDayOfWeek - 1) % 7 == 0) {
                        out.print("</tr><tr>");
                    }
                }

                // Close the table
                out.print("</tr></table>");
            }
        %>
    </body>
</html>

