<%-- 
    Document   : salary
    Created on : Oct 27, 2024, 8:59:37 PM
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
            function deletePlan(id)
            {
                var result = confirm("Are you sure?");
                if (result)
                {
                    document.getElementById("formDelete" + id).submit();
                }
            }

        </script>
    </head>
    <body>
        <h2>Production Plan</h2><h3><a href="../Home.html">HOME</a></h3>
        <h3>General:</h3><br/>
        ${requestScope.error}
        <table>
            <tr>
                <th>Plan Id</th>
                <th>Start time</th>
                <th>End time</th>
                <th>Department</th>
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
                </tr>
            </c:forEach>
        </table>

        <c:if test="${not empty param.id}">
            <h3>Detail Plan for Plan: ${param.id}</h3>
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
        </c:if><br/>

        <c:if test="${not empty param.id}">
            <h3>Detail Plan for days with Plan: ${param.id}</h3><br/>
            ${requestScope.error1}
            <table>
                <tr>
                    <th>Date</th>
                    <th>Shift</th>
                    <th>Product</th>
                    <th>Quantity</th>
                </tr>
                <c:forEach items="${requestScope.plan.pc}" var="pcl">
                    <c:forEach items="${requestScope.schedules}" var="sche">
                        <c:if test="${sche.cam.id == pcl.id}">

                            <tr>
                                <td><a href="list?id=${param.id}&scheId=${sche.id}">${sche.date}</a></td>
                                <td>${sche.k}</td>
                                <c:forEach items="${requestScope.products}" var="pr">
                                    <c:if test="${pcl.p.id == pr.id}">
                                        <td>${pr.name}</td>
                                    </c:if>
                                </c:forEach>
                                <td>${sche.quantity}</td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </table>
        </c:if>

        <c:if test="${not empty param.scheId}">
            <h3>Plan for day:</h3><br/>
            <table>
                <tr>
                    <th>Worker Id</th>
                    <th>Worker Name</th>
                    <th>Order Quantity</th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach items="${requestScope.workList}" var="ws">
                    <c:if test="${ws.sc.id == param.scheId}">
                        <tr>
                            <c:forEach items="${requestScope.emps}" var="e">
                                <c:if test="${ws.e.id==e.id}">
                                    <td>${e.id}</td>
                                    <td>${e.name}</td>
                                </c:if>
                            </c:forEach>
                            <td>${ws.quantity}</td>
                            <td>
                                <a href="list?id=${param.id}&scheId=${param.scheId}&wsid=${ws.id}">More</a>
                            </td>
                            <td><a href="update?Wsid=${ws.id}&scheId3=${param.scheId}">Edit</a>
                                <input type="button" value="Delete" onclick="deletePlan(${ws.id})" />
                                <form action="delete" method="POST" id="formDelete${ws.id}">
                                    <input type="hidden" name="wsId" value="${ws.id}"/>
                                </form>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
            <form action="create" method="GET">
                <input type="hidden" name="scheId" value="${param.scheId}" />
                <input type="submit" value="Add new plan for day"/>
            </form><br/> 
        </c:if>

        <c:if test="${not empty param.wsid}">
            <h3>Work in day:</h3>
            <table>
                <tr>
                    <th>Employee ID</th>
                    <th>Full Name</th>
                    <th>Product ID</th>
                    <th>Product Name</th>
                    <th>Ordered Quantity</th>
                    <th>Actual Quantity</th>
                    <th>Alpha</th>
                    <th></th>
                </tr>
                <c:forEach var="wl" items="${requestScope.workList}">
                    <c:forEach items="${requestScope.attends}" var="at">
                        <c:if test="${at.ws.id==wl.id}">
                            <c:if test="${wl.sc.id==requestScope.schedule.id}">
                                <input type="hidden" name="scid" value="${wl.id}"/>
                                <tr>
                                    <c:forEach items="${requestScope.emps}" var="emp">
                                        <c:if test="${wl.e.id==emp.id}">
                                            <td>${emp.id}</td>
                                            <td>${emp.name}</td>
                                        </c:if>
                                    </c:forEach >
                                    <c:forEach items="${requestScope.products}" var="pr">
                                        <c:if test="${requestScope.schedule.cam.p.id==pr.id}">
                                            <td>${pr.name}</td>
                                        </c:if>
                                    </c:forEach>
                                    <td>${requestScope.schedule.cam.p.id}</td>
                                    <td>${wl.quantity}</td>
                                    <td>
                                        ${at.quantity}
                                    </td>
                                    <td>${at.alpha}</td>
                                    <td><a href="attendent/update?id=${param.id}&scheId=${param.scheId}&wsid=${param.wsid}&atenid=${at.id}">Edit</a>
                                        <input type="button" value="Delete" onclick="deletePlan(${at.id})" />
                                        <form action="attendent/delete" method="POST" id="formDelete${at.id}">
                                            <input type="hidden" name="atId" value="${at.id}"/>
                                        </form>
                                    </td>
                                </tr>
                            </c:if>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </table>
            <form action="attendent" method="GET">
                <input type="hidden" name="scheId1" value="${param.scheId}" />
                <input type="submit" value="Add new work in day"/>
            </form><br/>
        </c:if>
    </body>
</html>
