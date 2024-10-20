<%-- 
    Document   : detail
    Created on : Oct 16, 2024, 5:36:42 PM
    Author     : Ad
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .product-table {
                display: none; /* Mặc định ẩn bảng sản phẩm */
            }
        </style>
        <script>
            // Hàm để ẩn/hiện bảng sản phẩm khi nhấn vào shift
            function toggleProductTable(shiftId) {
                const productTable = document.getElementById(shiftId);
                if (productTable.style.display === "none") {
                    productTable.style.display = "table"; // Hiện bảng nếu đang ẩn
                } else {
                    productTable.style.display = "none"; // Ẩn bảng nếu đang hiện
                }
            }
        </script>
    </head>
    <body>
        <form action="detail" method="POST">
            <table border="1px">
                <tr>
                    <th>Workshop</th>
                    <th>Date</th>
                    <th>Shift</th>
                    <th>Product & Quantity</th>
                    <th>Required Quantity</th>
                </tr>
                <c:forEach items="${requestScope.dateList}" var="date" varStatus="loop">
                    <tr>
                        <td>${requestScope.plan.id}<input type="hidden" name="planId" value="${requestScope.plan.id}">
                            <input type="hidden" name="startDate" value="${requestScope.plan.startTime}">
                            <input type="hidden" name="endDate" value="${requestScope.plan.endTime}"></td>
                        <td>${date}</td>

                        <td>
                            <table border="1px">
                                <tr>
                                    <!-- Khi nhấn vào shift, gọi hàm để ẩn/hiện bảng sản phẩm -->
                                    <td><button type="button" onclick="toggleProductTable('product-table-${loop.index}_1')">K1</button></td>
                                </tr>
                                <tr>
                                    <td><button type="button" onclick="toggleProductTable('product-table-${loop.index}_2')">K2</button></td>
                                </tr>
                                <tr>
                                    <td><button type="button" onclick="toggleProductTable('product-table-${loop.index}_3')">K3</button></td>
                                </tr>
                            </table>
                        </td>

                        <td>
                            <table id="product-table-${loop.index}_1" class="product-table" border="1px">
                                <c:forEach items="${requestScope.products}" var="p">
                                    <tr>
                                        <td>${p.name}</td>
                                        <td><input type="text" name="quantity${loop.index}_1" placeholder="Enter quantity" /></td>
                                    </tr>
                                </c:forEach>
                            </table>

                            <table id="product-table-${loop.index}_2" class="product-table" border="1px">
                                <c:forEach items="${requestScope.products}" var="p">
                                    <tr>
                                        <td>${p.name}</td>
                                        <td><input type="text" name="quantity${loop.index}_2" placeholder="Enter quantity" /></td>
                                    </tr>
                                </c:forEach>
                            </table>

                            <table id="product-table-${loop.index}_3" class="product-table" border="1px">
                                <c:forEach items="${requestScope.products}" var="p">
                                    <tr>
                                        <td>${p.name}</td>
                                        <td><input type="text" name="quantity${loop.index}_3" placeholder="Enter quantity" /></td>
                                    </tr>
                                </c:forEach>
                            </table>

                        </td>
                        <c:if test="${loop.first}">
                            <td rowspan="4" align="right">
                                <table border="1px">
                                    <c:forEach items="${requestScope.products}" var="pr">
                                        <tr>
                                            <td>${pr.name}</td>
                                            <td>
                                                <c:forEach items="${requestScope.plan.pc}" var="pl">
                                                    <c:if test="${pl.p.id eq pr.id}">
                                                        ${pl.quantity}
                                                    </c:if>
                                                </c:forEach>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>

            </table>
            <input type="submit" name="Save" value="Save Plan" />
        </form>
    </body>
</html>
