<%-- 
    Document   : create
    Created on : Oct 16, 2024, 3:51:41 PM
    Author     : Ad
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="create" method="POST">
            FROM:<input type="date" name="from"/>
            TO:<input type="date" name="to"/><br/>
            Workshop:<select name="did">
                <c:forEach items="${requestScope.depts}" var="d">
                    <option value="${d.id}">
                        ${d.name}
                    </option>
                </c:forEach>
            </select><br/>
            ${requestScope.error}
            <table border = "1px">
                <tr>
                <th>Product</th>
                <th>Quantity</th>
                <th>Effort</th>
                </tr>
                <c:forEach items="${requestScope.products}" var="p">
                    <tr>
                        <td>${p.name}<input type="hidden" name="pid" value="${p.id}"/></td>
                        <td><input type="text" name="quantity${p.id}"/></td>
                        <td><input type="text" name="effort${p.id}"/></td>
                    </tr>
                </c:forEach>
            </table>
            <input type="submit" name="Save"/>
        </form>
    </body>
</html>
