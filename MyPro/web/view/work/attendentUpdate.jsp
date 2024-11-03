<%-- 
    Document   : attendentUpdate
    Created on : Oct 27, 2024, 5:03:45 PM
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
        <form action="update" method="POST">
            <h2>Attendance in detail personal plan</h2>
            ${requestScope.error}
            <table>
                <tr>
                    <th>Employee ID</th>
                    <th>Full Name</th>
                    <th>Ordered Quantity</th>
                    <th>Actual Quantity</th>
                    <th>Alpha</th>
                </tr>

                <tr>
                <input type="hidden" name="wsid" value="${requestScope.attend.id}">
                <c:forEach items="${requestScope.ws}" var="w">
                    <c:if test="${w.id==requestScope.attend.ws.id}">
                        <c:forEach items="${requestScope.emps}" var="emp">
                            <c:if test="${w.e.id==emp.id}">
                                <td>${emp.id}<input type="hidden" name="schid" value="${w.id}"></td>
                                <td>${emp.name}</td>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </c:forEach>
                <c:forEach items="${requestScope.ws}" var="w">
                    <c:if test="${w.id==requestScope.attend.ws.id}">
                        <td>${w.quantity}</td>
                        <td>
                            <input type="number" name="actualQuantity" oninput="updateAlpha(this, ${w.quantity}, this.parentElement.nextElementSibling,this.nextElementSibling)">
                            <input type="hidden" name="alpha2" value="">
                        </td>
                    </c:if>
                </c:forEach>
                <td></td>
                </tr>
            </table>
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
