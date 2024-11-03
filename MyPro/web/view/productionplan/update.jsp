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
        <!-- Thêm Bootstrap -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            body {
                background-color: #f8f9fa;
                padding: 20px;
                background-image: url("../view/image/anh1.jpg");
                background-size: cover;
                background-position: center;
                background-repeat: no-repeat;
            }
            .form-container {
                background-color: #ffffff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
                max-width: 800px;
                margin: auto;
            }
            .table-container {
                margin-top: 20px;
            }
            .error-message {
                color: red;
                font-weight: bold;
                margin-bottom: 10px;
            }
            .table thead th {
                background-color: #007bff;
                color: #fff;
            }
            .back-button {
                position: absolute;
                top: 20px;
                left: 20px;
                background-color: #007bff;
                color: #fff;
            }
        </style>
    </head>
    <body>
        <a href="javascript:history.back()" class="btn btn-secondary back-button">Back</a>
        <div class="form-container">
            <h3 class="text-center">Update Plan</h3>

            <!-- Hiển thị lỗi nếu có -->
            <c:if test="${not empty requestScope.error1}">
                <div class="alert alert-danger" role="alert">
                    ${requestScope.error1}
                </div>
            </c:if>

            <!-- Form cập nhật kế hoạch -->
            <form action="update" method="POST">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Plan Id</th>
                            <th>Start Time</th>
                            <th>End Time</th>
                            <th>Department</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>${requestScope.plan.id}<input type="hidden" name="planId" value="${requestScope.plan.id}"/></td>
                            <td><input type="date" class="form-control" name="startTime" value="${requestScope.plan.startTime}"></td>
                            <td><input type="date" class="form-control" name="endTime" value="${requestScope.plan.endTime}"></td>
                            <td>
                                <select name="did" class="form-control">
                                    <c:forEach items="${requestScope.depts}" var="d">
                                        <option ${requestScope.plan.d.id eq d.id ? "selected=\"selected\"" : ""} value="${d.id}">${d.name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </tbody>
                </table>

                <c:if test="${not empty requestScope.plan.pc}">
                    <div class="table-container">
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Product Name</th>
                                    <th>Quantity</th>
                                    <th>Effort</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestScope.planCampaign}" var="pc">
                                    <tr>
                                        <c:forEach items="${requestScope.products}" var="pr">
                                            <c:if test="${pc.p.id == pr.id}">
                                                <td>
                                                    <input type="hidden" name="prid" value="${pr.id}"/>${pr.name}
                                                </td>
                                                <td><input type="text" class="form-control" name="quantity${pr.id}" value="${pc.quantity}"/></td>
                                                <td><input type="text" class="form-control" name="effort${pr.id}" value="${pc.effort}"/></td>
                                                </c:if>
                                            </c:forEach>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>

                <!-- Nút submit -->
                <div class="text-center">
                    <button type="submit" class="btn btn-primary">Update Plan</button>
                </div>
            </form>
        </div>

        <!-- Thêm Bootstrap JS và jQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
