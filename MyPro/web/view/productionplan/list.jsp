<%-- 
    Document   : list
    Created on : Oct 23, 2024, 7:38:45 PM
    Author     : Ad
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Production Plan</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: Arial, sans-serif;
            }
            body {
                display: flex;
                align-items: center;
                justify-content: center;
                min-height: 100vh;
                background-image: url("../view/image/anh1.jpg"); 
                background-size: cover; 
                background-position: center; 
                background-repeat: no-repeat;
                background-color: #f4f4f9;
            }
            .container {
                max-width: 1200px;
                width: 100%;
                padding: 20px;
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            }
            h2 {
                color: #333;
                margin-bottom: 20px;
            }
            h3 {
                margin: 15px 0;
            }
            a {
                text-decoration: none;
                color: #007bff;
            }
            a:hover {
                color: #0056b3;
            }
            .table-container {
                overflow-x: auto;
            }
            table {
                border-collapse: collapse;
                width: 100%;
                margin-bottom: 20px;
            }
            tr:hover {
                background-color: #f1f1f1;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 12px;
                text-align: center;
            }
            th {
                background-color: #f2f2f2;
                color: #333;
            }
            td a {
                color: #007bff;
                text-decoration: none;
            }
            td a:hover {
                color: #0056b3;
            }
            .button {
                display: inline-block;
                padding: 10px 20px;
                margin-top: 10px;
                background-color: #28a745;
                color: white;
                font-weight: bold;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                text-align: center;
                text-decoration: none;
            }
            .button:hover {
                background-color: #218838;
            }
            .button-danger {
                background-color: #dc3545;
                color: white;
            }
            .button-danger:hover {
                background-color: #c82333;
            }
            .error {
                color: #d9534f;
                margin-top: 10px;
            }
        </style>
        <script>
            function deletePlan(id) {
                var result = confirm("Are you sure?");
                if (result) {
                    document.getElementById("formDelete" + id).submit();
                }
            }
        </script>
    </head>
    <body>
        <div class="container">
            <h2>Production Plan</h2>
            <h3><a href="../Home.html">Home</a></h3>

            <h3>General:</h3>
            <div class="error">${requestScope.error}</div>

            <div class="table-container">
                <table>
                    <tr>
                        <th>Plan Id</th>
                        <th>Start time</th>
                        <th>End time</th>
                        <th>Department</th>
                        <th>Actions</th>
                    </tr>
                    <c:forEach items="${requestScope.plans}" var="pl">
                        <tr>
                            <td><a href="list?id=${pl.id}">${pl.id}</a></td>
                            <td>${pl.startTime}</td>
                            <td>${pl.endTime}</td>
                            <c:forEach items="${requestScope.depts}" var="dept">
                                <c:if test="${pl.d.id == dept.id}">
                                    <td>${dept.name}</td>
                                </c:if>
                            </c:forEach>
                            <td>
                                <a href="update?id=${pl.id}" class="button">Edit</a>
                                <button class="button button-danger" onclick="deletePlan(${pl.id})">Delete</button>
                                <form action="delete" method="POST" id="formDelete${pl.id}" style="display: none;">
                                    <input type="hidden" name="id" value="${pl.id}"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <form action="create" method="GET">
                <input type="submit" class="button" value="Add new plan"/>
            </form>

            <c:if test="${not empty param.id}">
                <h3>Detail Plan for Plan: ${param.id}</h3>
                <div class="table-container">
                    <table>
                        <tr>
                            <th>Product Name</th>
                            <th>Quantity</th>
                            <th>Effort</th>
                        </tr>
                        <c:forEach items="${requestScope.plan.pc}" var="pc">
                            <tr>
                                <c:forEach items="${requestScope.products}" var="pr">
                                    <c:if test="${pc.p.id == pr.id}">
                                        <td>${pr.name}</td>
                                    </c:if>
                                </c:forEach>
                                <td>${pc.quantity}</td>
                                <td>${pc.effort}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:if>

            <c:if test="${not empty param.id}">
                <h3>Detail Plan for days with Plan: ${param.id}</h3>
                <div class="error">${requestScope.error1}</div>

                <div class="table-container">
                    <table>
                        <tr>
                            <th>Date</th>
                            <th>Shift</th>
                            <th>Product</th>
                            <th>Quantity</th>
                            <th>Actions</th>
                        </tr>
                        <c:forEach items="${requestScope.plan.pc}" var="pcl">
                            <c:forEach items="${requestScope.schedules}" var="sche">
                                <c:if test="${sche.cam.id == pcl.id}">
                                    <tr>
                                        <td>${sche.date}</td>
                                        <td>${sche.k}</td>
                                        <c:forEach items="${requestScope.products}" var="pr">
                                            <c:if test="${pcl.p.id == pr.id}">
                                                <td>${pr.name}</td>
                                            </c:if>
                                        </c:forEach>
                                        <td>${sche.quantity}</td>
                                        <td>
                                            <a href="detail/update?planId=${pcl.pl.id}&id=${sche.id}" class="button">Edit</a>
                                            <button class="button button-danger" onclick="deletePlan(${sche.id})">Delete</button>
                                            <form action="detail/delete" method="POST" id="formDelete${sche.id}" style="display: none;">
                                                <input type="hidden" name="scheId" value="${sche.id}"/>
                                            </form>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </c:forEach>
                    </table>
                </div>

                <form action="detail" method="GET">
                    <input type="hidden" name="id" value="${param.id}">
                    <input type="submit" class="button" value="Add new schedule"/>
                </form>
            </c:if>
        </div>
    </body>
</html>
