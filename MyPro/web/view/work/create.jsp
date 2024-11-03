<%-- 
    Document   : create
    Created on : Oct 22, 2024, 5:38:17 PM
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
        <form action="create" method="POST">
            <h2>Worker Schedule: ${requestScope.schedule.id}<input type="hidden" name="scheId" value="${requestScope.schedule.id}"/></h2><br/>
            ${requestScope.error}
            <table>
                <tr>
                    <th>Worker Id</th>
                    <th>Worker Name</th>
                    <th>Product Name</th>
                    <th>Shift</th>
                    <th>Order Quantity</th>
                </tr>
                <c:forEach items="${requestScope.depts}" var="dept">
                    <c:forEach var="worker" items="${requestScope.employees}" varStatus="loop">
                        <c:if test="${worker.d.id == dept.id}">
                            <tr>
                                <td>
                                    ${worker.id}
                                    <input type="hidden" name="workerId" value="${worker.id}"/>
                                </td>
                                <td>
                                    ${worker.name}
                                </td>


                                <td>
                                    <c:forEach var="pr" items="${requestScope.products}">
                                        <c:if test="${requestScope.schedule.cam.p.id == pr.id}">
                                            ${pr.name}
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td>
                                    ${requestScope.schedule.getK()}
                                </td>

                                <td><input type="number" name="quantity"></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </table>
            <button type="submit">Create Schedule</button>
        </form>
    </body>
</html>

