<%-- 
    Document   : update
    Created on : Oct 25, 2024, 8:57:23 AM
    Author     : Ad
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Plan</title>
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
            <table>
                <tr>
                    <th>Plan Id</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Department</th>
                </tr>
                <tr>
                    <td>${requestScope.plan.id}<input type="hidden" name="planId" value="${requestScope.plan.id}"/></td>
                    <td><input type="date" name="startTime" value="${requestScope.plan.startTime}"></td>
                    <td><input type="date" name="endTime" value="${requestScope.plan.endTime}"></td>
                    <td>
                        <select name="did">
                            <c:forEach items="${requestScope.depts}" var="d">
                                <option ${requestScope.plan.d.id eq d.id ?"selected=\"selected\"":""} value="${d.id}">${d.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table><br/>

            <c:if test="${not empty requestScope.plan.pc}">
                <table>
                    <tr>
                        <th>Product Name</th>
                        <th>Quantity</th>
                        <th>Effort</th>
                    </tr>
                    <c:forEach items="${requestScope.planCampaign}" var="pc">
                        <tr>
                            <c:forEach items="${requestScope.products}" var="pr">
                                <c:if test="${pc.p.id==pr.id}">
                                    <td>
                                        <input type="hidden" name="prid" value="${pr.id}"/>${pr.name}
                                    </td>
                                    <td><input type="text" name="quantity${pr.id}" value="${pc.quantity}"/></td>
                                    <td><input type="text" name="effort${pr.id}" value="${pc.effort}"/></td>
                                    </c:if>
                                </c:forEach>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            <input type="submit" value="Update Plan"/>
        </form>
    </body>
</html>
