<%-- 
    Document   : attendent
    Created on : Oct 23, 2024, 5:01:50 PM
    Author     : Ad
--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Attendance</title>
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
        <script>
            function updateAlpha(input, orderedQuantity, alphaCell, hiddenAlphaInput) {
                const actualQuantity = parseFloat(input.value);
                const alphaValue = actualQuantity / orderedQuantity;

                // Display the calculated alpha value in the Alpha cell
                alphaCell.innerText = isNaN(alphaValue) ? '' : alphaValue.toFixed(2);
                hiddenAlphaInput.value = isNaN(alphaValue) ? '' : alphaValue.toFixed(2);
            }
        </script>
    </head>
    <body>
        <form action="attendent" method="POST">
            <h2>Attendance in detail personal plan(Schedule): ${requestScope.schedule.id}
                <input type="hidden" name="scheId" value="${requestScope.schedule.id}">
            </h2>
            <table>
                <tr>
                    <th>Employee ID</th>
                    <th>Full Name</th>
                    <th>Product ID</th>
                    <th>Product Name</th>
                    <th>Ordered Quantity</th>
                    <th>Actual Quantity</th>
                    <th>Alpha</th>
                </tr>
                <c:forEach var="wl" items="${requestScope.workList}">
                    <c:if test="${wl.sc.id==requestScope.schedule.id}">
                        <input type="hidden" name="scid" value="${wl.id}"/>
                        <tr>
                            <c:forEach items="${requestScope.emps}" var="emp">
                                <c:if test="${wl.e.id==emp.id}">
                                    <td>${emp.id}</td>
                                    <td>${emp.name}</td>
                                </c:if>
                            </c:forEach >
                            <c:forEach items="${requestScope.products}" var="pr">
                                <c:if test="${requestScope.schedule.cam.p.id==pr.id}">
                                    <td>${pr.name}</td>
                                </c:if>
                            </c:forEach>
                            <td>${requestScope.schedule.cam.p.id}</td>
                            <td>${wl.quantity}</td>
                            <td>
                                <input type="number" name="actualQuantity" oninput="updateAlpha(this, ${wl.quantity}, this.parentElement.nextElementSibling,this.nextElementSibling)">
                                <input type="hidden" name="alpha2" value="">
                            </td>
                            <td></td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
            <input type="submit" value="Submit">
        </form>
    </body>
</html>

