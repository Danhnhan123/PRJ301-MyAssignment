<%-- 
    Document   : create
    Created on : Oct 22, 2024, 5:38:17 PM
    Author     : Ad
--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Worker Schedule</title>
        <!-- Bootstrap CSS -->
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <style>
            body {
                background-color: #f8f9fa;
                padding: 20px;
                background-image: url("../view/image/anh2.jpg");
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
        <div class="container form-container">
            <h3 class="text-center mb-4">Worker Schedule</h3>
            <form action="create" method="POST">
                <h2 class="text-center">Worker Schedule ID: 
                    ${requestScope.schedule.id}
                    <input type="hidden" name="scheId" value="${requestScope.schedule.id}">
                </h2>
                
                <c:if test="${not empty requestScope.error}">
                    <div class="alert alert-danger error-message">${requestScope.error}</div>
                </c:if>

                <div class="table-container">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Worker Id</th>
                                <th>Worker Name</th>
                                <th>Product Name</th>
                                <th>Shift</th>
                                <th>Order Quantity</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.depts}" var="dept">
                                <c:forEach var="worker" items="${requestScope.employees}" varStatus="loop">
                                    <c:if test="${worker.d.id == dept.id}">
                                        <tr>
                                            <td>
                                                ${worker.id}
                                                <input type="hidden" name="workerId" value="${worker.id}">
                                            </td>
                                            <td>${worker.name}</td>
                                            <td>
                                                <c:forEach var="pr" items="${requestScope.products}">
                                                    <c:if test="${requestScope.schedule.cam.p.id == pr.id}">
                                                        ${pr.name}
                                                    </c:if>
                                                </c:forEach>
                                            </td>
                                            <td>${requestScope.schedule.getK()}</td>
                                            <td><input type="number" name="quantity" class="form-control" placeholder="Quantity"></td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="text-center mt-4">
                    <button type="submit" class="btn btn-primary">Create Schedule</button>
                </div>
            </form>
        </div>

        <!-- Bootstrap JS, Popper.js, and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>

