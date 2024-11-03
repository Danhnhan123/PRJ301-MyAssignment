<%-- 
    Document   : update
    Created on : Oct 27, 2024, 3:43:35 PM
    Author     : Ad
--%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Worker Schedule</title>
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
            <h2>Worker Schedule: ${requestScope.schedule.id}
                <input type="hidden" name="wsId" value="${requestScope.work.id}"/>
                <input type="hidden" name="scheId" value="${requestScope.schedule.id}"/>
            </h2><br/>
            ${requestScope.error}
            <table>
                <tr>
                    <th>Worker Id</th>
                    <th>Worker Name</th>
                    <th>Order Quantity</th>
                </tr>

                <tr>
                    <c:forEach items="${requestScope.emps}" var="emp">
                        <c:if test="${emp.id==work.e.id}">
                            <td>
                                ${emp.id}
                                <input type="hidden" name="workerId" value="${emp.id}"/>
                            </td>
                            <td>
                                ${emp.name}
                            </td>
                        </c:if>
                    </c:forEach>

                            <td><input type="number" name="quantity" value="${requestScope.work.quantity}"></td>
                </tr>

            </table>
            <button type="submit">Update Schedule</button>
        </form>
    </body>
</html>
