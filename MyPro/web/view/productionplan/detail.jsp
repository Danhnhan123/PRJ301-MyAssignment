<%-- 
    Document   : detail
    Created on : Oct 16, 2024, 5:36:42 PM
    Author     : Ad
--%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Plan</title>
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
        <form action="detail" method="POST">
            <h2>Workshop: 
                ${requestScope.plan.id}<input type="hidden" name="planId" value="${requestScope.plan.id}">
                <input type="hidden" name="startDate" value="${requestScope.plan.startTime}">
                <input type="hidden" name="endDate" value="${requestScope.plan.endTime}"></h2>
                ${requestScope.error}
            <table>
                <tr>
                    <th rowspan="3">Products</th>
                    <th colspan="${fn:length(requestScope.dateList)*3}">Date</th>
                </tr>

                <tr>
                    <c:forEach items="${requestScope.dateList}" var="date">
                        <th colspan="3">${date}</th> <!-- Each date will have 3 sub-columns -->
                        </c:forEach>
                </tr>
                <tr>
                    <c:forEach items="${requestScope.dateList}" var="date">
                        <td>K1</td>
                        <td>K2</td>
                        <td>K3</td>
                    </c:forEach>
                </tr>
                <c:forEach items="${requestScope.plan.pc}" var="pr">
                    <tr>
                        <td>${pr.p.name}</td>
                        <c:forEach items="${requestScope.dateList}" var="date" varStatus="loop">
                            <td>
                                <!-- Input for K1 -->
                                <input type="number" name="quantity_${pr.p.id}_${date}_K1" placeholder="Quantity K1">
                            </td>
                            <td> 
                                <!-- Input for K2 -->
                                <input type="number" name="quantity_${pr.p.id}_${date}_K2" placeholder="Quantity K2">
                            </td>
                            <td>
                                <!-- Input for K3 -->
                                <input type="number" name="quantity_${pr.p.id}_${date}_K3" placeholder="Quantity K3">
                            </td>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </table>
            <br>
            <input type="submit" value="Submit Quantities">
        </form>
    </body>
</html>

