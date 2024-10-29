<%-- 
    Document   : Schedule
    Created on : Oct 28, 2024, 2:14:55 PM
    Author     : Ad
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <!-- code year, month get from chatGPT -->
            <select id="year" name="year">
                <%
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    for (int i = currentYear - 24; i <= currentYear + 10; i++) {
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

                 for (int day = 1; day <= daysInMonth; day++) {
                    out.print("<td><a href='schedule?date=" + new Date(calendar.getTimeInMillis()) + "'>" + day + "</a></td>");
                    if ((day + firstDayOfWeek - 1) % 7 == 0) { out.print("</tr><tr>"); }
                    calendar.add(Calendar.DATE, 1);
                }

                // Close the table
                out.print("</tr></table>");
            }
        %>

        <c:if test="${not empty param.date}">

            <h2>Employee :</h2>
            <table>
                <tr>
                    <th>Date</th>
                    <th>Shift</th>
                    <th>Product</th>
                    <th>Quantity</th>
                </tr>

                <c:forEach items="${requestScope.schedules}" var="sche">
                        <c:if test="${param.date eq sche.date}">
                            <tr>
                                <td>${sche.date}</td>
                                <td>${sche.k}</td>
                                <c:forEach items="${requestScope.plans}" var="pl">
                                    <c:forEach items="${pl.pc}" var="pc">
                                        <c:if test="${pc.id==sche.cam.id}">
                                            <td>${pc.p.name}</td>
                                        </c:if>
                                    </c:forEach>
                                </c:forEach>

                                    <td></td>

                            </tr>
                        </c:if>                      
                </c:forEach>

            </table>

        </c:if>
    </body>
</html>

