<%-- 
    Document   : detail
    Created on : Oct 16, 2024, 5:36:42 PM
    Author     : Ad
--%>
<%@ taglib uri="/WEB-INF/tlds/mytags" prefix="mytag" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Product Plan</title>
        <!-- Bootstrap CSS -->
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
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
        <div class="container form-container">
            <h3 class="text-center mb-4">Product Plan</h3>
            <form action="detail" method="POST">
                <h2 class="text-center">Workshop: 
                    ${requestScope.plan.id}
                    <input type="hidden" name="planId" value="${requestScope.plan.id}">
                    <input type="hidden" name="startDate" value="${requestScope.plan.startTime}">
                    <input type="hidden" name="endDate" value="${requestScope.plan.endTime}">
                </h2>
                <c:if test="${not empty requestScope.error}">
                    <div class="alert alert-danger error-message">${requestScope.error}</div>
                </c:if>
                <c:forEach items="${requestScope.dateList}" var="date">
                    <div class="table-container">
                        <table class="table table-bordered">

                            <thead>
                                <tr>
                                    <th scope="col" colspan="4"><mytag:ToVietnameseDate value="${date}" /></th>
                                </tr>
                                <tr>
                                    <th scope="col">Products</th>
                                    <th scope="col">K1</th>
                                    <th scope="col">K2</th>
                                    <th scope="col">K3</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestScope.plan.pc}" var="pr">
                                    <tr>
                                        <td>${pr.p.name}</td>
                                        <td>
                                            <input type="number" name="quantity_${pr.p.id}_${date}_K1" class="form-control" placeholder="Quantity K1">
                                        </td>
                                        <td>
                                            <input type="number" name="quantity_${pr.p.id}_${date}_K2" class="form-control" placeholder="Quantity K2">
                                        </td>
                                        <td>
                                            <input type="number" name="quantity_${pr.p.id}_${date}_K3" class="form-control" placeholder="Quantity K3">
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>

                        </table>
                    </div>
                </c:forEach>
                <div class="text-center mt-4">
                    <input type="submit" value="Submit Quantities" class="btn btn-primary"/>
                </div>
            </form>
        </div>

        <!-- Bootstrap JS, Popper.js, and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>

