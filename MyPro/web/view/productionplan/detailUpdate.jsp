<%-- 
    Document   : detailUpdate
    Created on : Oct 26, 2024, 2:17:19 PM
    Author     : Ad
--%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            table {
                border-collapse: collapse;
                width: 100%;
            }
            th, td {
                border: 1px solid black;
                padding: 5px;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <form action="update" method="POST">
            <input type="hidden" name="id" value="${param.id != null ? param.id : requestScope.id}">
            <h2>Workshop: 
                ${requestScope.plan.id}<input type="hidden" name="planId" value="${requestScope.plan.id}">
            </h2>
            ${requestScope.error}
            <table>
                <tr>
                    <th>Products</th>
                    <th>Date</th>
                    <th>Shift</th>
                    <th>Quantity</th>
                </tr>

                <c:forEach items="${requestScope.schedules}" var="sc">
                    <c:if test="${param.id==sc.id}">
                        <input type="hidden" name="scheId" value="${sc.id}">
                        <tr>
                            <c:forEach items="${requestScope.planCampaigns}" var="pc">
                                <c:if test="${sc.cam.id==pc.id}">
                                    <td>${pc.p.name}<input type="hidden" name="camid" value="${pc.id}"></td>
                                    </c:if>
                                </c:forEach>
                            <td>
                                ${sc.date}<input type="hidden" name="date" value="${sc.date}">
                            </td>
                            <td> 
                                ${sc.k}<input type="hidden" name="k" value="${sc.k}">
                            </td>
                            <td>
                                <input type="number" name="quantity" value="${sc.quantity}">
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
            <br>
            <!-- Submit button -->
            <input type="submit" value="Update Quantities">
        </form>
    </body>
</html>
