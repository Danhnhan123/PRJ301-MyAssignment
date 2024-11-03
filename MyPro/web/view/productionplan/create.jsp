<%-- 
    Document   : create
    Created on : Oct 16, 2024, 3:51:41 PM
    Author     : Ad
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Create Production Plan</title>
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
            <h3 class="text-center mb-4">Create Production Plan</h3>
            <form action="create" method="POST">
                <div class="form-group">
                    <label for="from">From:</label>
                    <input type="date" id="from" name="from" class="form-control"/>
                </div>
                <div class="form-group">
                    <label for="to">To:</label>
                    <input type="date" id="to" name="to" class="form-control"/>
                </div>
                <div class="form-group">
                    <label for="did">Workshop:</label>
                    <select id="did" name="did" class="form-control">
                        <c:forEach items="${requestScope.depts}" var="d">
                            <option value="${d.id}">${d.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <c:if test="${not empty requestScope.error}">
                    <div class="alert alert-danger error-message">${requestScope.error}</div>
                </c:if>

                <div class="table-container">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col">Product</th>
                                <th scope="col">Quantity</th>
                                <th scope="col">Effort</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.products}" var="p">
                                <tr>
                                    <td>${p.name}<input type="hidden" name="pid" value="${p.id}"/></td>
                                    <td><input type="text" name="quantity${p.id}" class="form-control"/></td>
                                    <td><input type="text" name="effort${p.id}" class="form-control"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="text-center mt-4">
                    <input type="submit" name="Save" class="btn btn-primary" value="Save"/>
                </div>
            </form>
        </div>

        <!-- Bootstrap JS, Popper.js, and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
