<%-- 
    Document   : attendent
    Created on : Oct 23, 2024, 5:01:50 PM
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
        <title>Attendance</title>
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
        <a href="javascript:history.back()" class="btn btn-secondary back-button">Back</a>
        <div class="container form-container">
            <h3 class="text-center mb-4">Attendance</h3>
            <form action="attendent" method="POST">
                <h2 class="text-center">Attendance in detail personal plan (Schedule): 
                    ${requestScope.schedule.id}
                    <input type="hidden" name="wsid" value="${param.wsid != null ? param.wsid : requestScope.wsid}">
                    <input type="hidden" name="scheId" value="${requestScope.schedule.id}">
                </h2>

                <c:if test="${not empty requestScope.error}">
                    <div class="alert alert-danger error-message">${requestScope.error}</div>
                </c:if>

                <div class="table-container">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Employee ID</th>
                                <th>Full Name</th>
                                <th>Product ID</th>
                                <th>Product Name</th>
                                <th>Ordered Quantity</th>
                                <th>Actual Quantity</th>
                                <th>Alpha</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="wl" items="${requestScope.workList}">
                                <c:if test="${wl.id == param.wsid}">
                                <input type="hidden" name="scid" value="${wl.id}"/>
                                <tr>
                                    <c:forEach items="${requestScope.emps}" var="emp">
                                        <c:if test="${wl.e.id == emp.id}">
                                            <td>${emp.id}</td>
                                            <td>${emp.name}</td>
                                        </c:if>
                                    </c:forEach>
                                    <c:forEach items="${requestScope.products}" var="pr">
                                        <c:if test="${requestScope.schedule.cam.p.id == pr.id}">
                                            <td>${pr.id}</td>
                                            <td>${pr.name}</td>
                                        </c:if>
                                    </c:forEach>
                                    <td>${wl.quantity}</td>
                                    <td>
                                        <input type="number" name="actualQuantity" class="form-control" placeholder="Actual Quantity" 
                                               oninput="updateAlpha(this, ${wl.quantity}, this.parentElement.nextElementSibling, this.nextElementSibling)">
                                        <input type="hidden" name="alpha2" value="">
                                    </td>
                                    <td></td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="text-center mt-4">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </form>
        </div>

        <!-- Bootstrap JS, Popper.js, and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>

